package biz.braham.rssreader.services;

import biz.braham.rssreader.exceptions.ReadFeedException;
import biz.braham.rssreader.models.Feed;
import biz.braham.rssreader.models.NewsItem;

import java.net.URL;
import java.util.ArrayList;

public interface RssReaderService {
    /**
     * Reads and parses a feed and returns all news items stripped of html
     *
     * @return
     * @throws ReadFeedException
     */
    ArrayList<NewsItem> readFeed(Feed feed) throws ReadFeedException;

    /**
     * Reads and parses a feed and returns all news items stripped of html
     *
     * @param feedUrl
     * @return
     * @throws ReadFeedException
     */
    ArrayList<NewsItem> readFeed(URL feedUrl) throws ReadFeedException;
}
