package biz.braham.rssreader.services;

import biz.braham.rssreader.exceptions.ReadFeedException;
import biz.braham.rssreader.models.Feed;
import biz.braham.rssreader.models.NewsItem;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of the RSS reader interface based on the rome reader
 */
@Service
public class RomeBasedReader implements RssReaderService {
    @Override
    public ArrayList<NewsItem> readFeed(Feed feed) throws ReadFeedException {
        return readFeed(feed.getUrl());
    }

    @Override
    public ArrayList<NewsItem> readFeed(URL feedUrl) throws ReadFeedException {
        ArrayList<NewsItem> newsItems = new ArrayList<NewsItem>();
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed rssFeed = input.build(new XmlReader(feedUrl));
            ((List<SyndEntryImpl>) rssFeed.getEntries()).forEach((SyndEntryImpl newsItem) -> {
                String itemTitle = newsItem.getTitle();
                //convert item description to plaintext using jsoup
                String itemDescription = Jsoup.parse(newsItem.getDescription().getValue()).text();
                Date itemDate = newsItem.getPublishedDate();
                newsItems.add(new NewsItem(itemTitle, itemDescription, itemDate));
            });
            return newsItems;
        } catch (Exception e) {
            throw new ReadFeedException();
        }
    }
}
