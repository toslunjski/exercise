package rsstoolkit.service;

import java.net.URL;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import rsstoolkit.model.FeedModel;
import rsstoolkit.model.TopicModel;
import rsstoolkit.repository.TopicRepository;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Service
public class FeedService {

    @Autowired
    private TopicRepository topicRepository;

    public FeedService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public ResponseEntity<String> analyseFeeds(List<String> feeds) throws Exception {

        List<FeedModel> feedList = new ArrayList<>();
        feedList = createFeedList(feeds);

        ArrayList<TopicModel> topicsList = new ArrayList<TopicModel>();
        ;

        Random rand = new Random();

        Integer id = rand.nextInt(1000);

        topicsList = createTopicsList(feedList, id);

        saveTopicsList(topicsList);

        return ResponseEntity.ok("RSS feed analysis saved under id: " + id);

    }

    private List<FeedModel> createFeedList(List<String> feeds) throws Exception {

        List<FeedModel> feedList = new ArrayList<>();

        for (String feed : feeds) {
            try {
                // Getting RSS feed entries with rome tool
                URL feedUrl = new URL(feed);
                SyndFeedInput syndFeedInput = new SyndFeedInput();
                SyndFeed syndFeed = syndFeedInput.build(new XmlReader(feedUrl));
                List<SyndEntry> syndEntries = syndFeed.getEntries();

                for (SyndEntry entry : syndEntries) {

                    // Parsing nouns from title with Stanford POS tagger
                    List<String> words = new ArrayList<String>();

                    MaxentTagger maxentTagger = new MaxentTagger("src/lib/english-left3words-distsim.tagger");
                    String tag = maxentTagger
                            .tagString(entry.getTitle().replace(" - " + entry.getSource().getTitle(), ""));
                    String[] eachTag = tag.split("\\s+");
                    for (int i = 0; i < eachTag.length; i++) {
                        // extracting singular or mass nouns, tag value NN
                        if (eachTag[i].endsWith("_NN")) {
                            words.add(eachTag[i].replace("_NN", ""));
                        } else if (eachTag[i].endsWith("_NNP")) {
                            words.add(eachTag[i].replace("_NNP", ""));
                        }
                    }
                    FeedModel feedEntry = new FeedModel(entry.getTitle(), entry.getLink(), feedUrl, words);
                    feedList.add(feedEntry);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return feedList;
    }

    private ArrayList<TopicModel> createTopicsList(List<FeedModel> feedList, Integer id) {

        ArrayList<TopicModel> topicsList = new ArrayList<TopicModel>();

        for (FeedModel feed : feedList) {
            for (String word : feed.getWords()) {
                topicsList.add(new TopicModel(id, word, feed.getTitle(), feed.getLink()));
            }
        }
        return topicsList;
    }

    private void saveTopicsList(ArrayList<TopicModel> topicsList) {
        for (TopicModel topic : topicsList) {
            topicRepository.save(topic);
        }
    }
}
