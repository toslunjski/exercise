package rsstoolkit.model;

import java.net.URL;
import java.util.List;

public class FeedModel {

    private String title;
    private String link;
    private URL url;
    private List<String> words;

    public FeedModel(String title, String link, URL url, List<String> words) {
        this.title = title;
        this.link = link;
        this.url = url;
        this.words = words;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public URL getUrl() {
        return url;
    }

    public List<String> getWords() {
        return words;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}