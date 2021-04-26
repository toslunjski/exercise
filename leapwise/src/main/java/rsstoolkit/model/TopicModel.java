package rsstoolkit.model;

import javax.persistence.*;

@Entity
@Table(name = "TOPIC")
public class TopicModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ANALYSE_ID", nullable = false)
    private Integer analyseId;

    @Column(name = "KEYWORD", length = 64, nullable = false)
    private String keyword;

    @Column(name = "NEWS_HEADER", columnDefinition = "VARCHAR")
    private String newsHeader;

    @Column(name = "NEWS_LINK", columnDefinition = "VARCHAR")
    private String newsLink;

    protected TopicModel() {
    }

    public TopicModel(Integer analyseId, String keyword, String newsHeader, String newsLink) {

        this.analyseId = analyseId;
        this.keyword = keyword;
        this.newsHeader = newsHeader;
        this.newsLink = newsLink;
    }

    public Integer getAnalyseId() {
        return analyseId;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getNewsHeader() {
        return newsHeader;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setAnalyseId(Integer analyseId) {
        this.analyseId = analyseId;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setNewsHeader(String newsHeader) {
        this.newsHeader = newsHeader;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    @Override
    public String toString() {
        return "Topic[" + "analyseId=" + analyseId + ", keyword='" + keyword + '\'' + ", newsHeader='" + newsHeader
                + '\'' + ", newsLink='" + newsLink + '\'' + ']';
    }

    public TopicModel build() throws NullPointerException {
        return new TopicModel(analyseId, keyword, newsHeader, newsLink);
    }
}