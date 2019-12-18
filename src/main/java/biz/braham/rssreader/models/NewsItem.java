package biz.braham.rssreader.models;

import java.util.Date;

public class NewsItem {
    private String title, description;
    private Date pubDate;

    public NewsItem(String title, String description, Date pubDate) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getPubDate() {
        return pubDate;
    }
}
