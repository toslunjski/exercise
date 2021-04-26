package rsstoolkit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rsstoolkit.service.*;

@RestController
public class AppController {

  private final FeedService feedService;
  private final TopicService topicService;

  public AppController(FeedService feedService, TopicService topicService) {
    this.feedService = feedService;
    this.topicService = topicService;
  }

  /**
   * @param feeds
   * @return
   */

  @RequestMapping("/analyse/new")
  @ResponseBody
  ResponseEntity<String> getFeeds(@RequestParam("rss-feed") List<String> feeds) throws Exception {
    if (feeds.size() < 1) {
      return ResponseEntity.badRequest().body("Please enter feeds.");
    }
    return feedService.analyseFeeds(feeds);
  }

  /**
   * @param id
   * @return list of words
   */

  @GetMapping("/frequency/{id}")
  @ResponseBody
  public ResponseEntity<List<Map<String, Object>>> getTopics(@PathVariable Integer id) {
    return topicService.findAnalysisById(id);
  }
}