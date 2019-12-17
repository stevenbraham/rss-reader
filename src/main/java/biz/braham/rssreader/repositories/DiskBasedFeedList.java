package biz.braham.rssreader.repositories;

import biz.braham.rssreader.exceptions.FeedNotFoundException;
import biz.braham.rssreader.exceptions.StoreFeedException;
import biz.braham.rssreader.models.Feed;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiskBasedFeedList implements FeedsRepository {
    private static final String FEEDS_LOCATION = "feeds.xml";

    private ArrayList<Feed> feeds;

    public DiskBasedFeedList() {
        this.feeds = new ArrayList<Feed>();
    }

    @Override
    public List<Feed> getAll() {
        if (feeds.size() == 0) {
            //read and cache feeds from xml file
            this.feeds = loadFeedsFromDisk();
        }
        return feeds;
    }

    @Override
    public Feed store(String name, URL url) throws StoreFeedException {

        int id = this.getAll().size() + 1;
        Feed newFeed = new Feed(id, name, url);
        if (!this.feeds.add(newFeed)) {
            throw new StoreFeedException();
        }
        try {
            writeToDisk();
        } catch (Exception e) {
            throw new StoreFeedException();
        }
        return newFeed;
    }

    /**
     * Reads the feeds.xml file and convert it to a collection
     * If the feeds.xml file does not exist, an empty feed is returned
     *
     * @return feeds found in xml file or empty feed
     */
    private ArrayList<Feed> loadFeedsFromDisk() {
        ArrayList<Feed> feeds = new ArrayList<Feed>();
        File feedFile = new File(FEEDS_LOCATION);
        if (feedFile.exists()) {
            try {
                Document document = new SAXBuilder().build(feedFile);
                List<Element> items = document.getRootElement().getChildren();
                items.forEach(node -> {
                    try {
                        //read and parse values from xml
                        int feedId = Integer.parseInt(node.getChildText("id").replaceAll("\\s+", ""));
                        String feedName = node.getChildText("name").replaceAll("\\s+", "");
                        URL feedUrl = new URL(node.getChildText("url").replaceAll("\\s+", ""));

                        feeds.add(new Feed(feedId, feedName, feedUrl));
                    } catch (Exception e) {

                    }
                });
            } catch (Exception e) {
                return feeds;
            }
        }
        return feeds;
    }

    /**
     * Takes all items in list and stores them in an xml file on disk
     *
     * @throws IOException
     */
    private void writeToDisk() throws IOException {
        Element feedsRoot = new Element("feeds");
        int feedId = 1;
        for (Feed feed : feeds) {
            Element feedElement = new Element("feed");
            feedElement.addContent(new Element("id").setText(Integer.toString(feedId)));
            feedElement.addContent(new Element("name").setText(feed.getName()));
            feedElement.addContent(new Element("url").setText(feed.getUrl().toString()));
            feedsRoot.addContent(feedElement);
            feedId++;
        }

        //store generated xml file to disk
        new XMLOutputter().output(new Document(feedsRoot), new FileWriter(FEEDS_LOCATION));
    }

    /**
     * Converts a feed id to an actual index in the feed list
     *
     * @param feedId
     * @return
     * @throws FeedNotFoundException
     */
    private int feedIdToIndex(int feedId) throws FeedNotFoundException {

        int index = feedId - 1;
        if (feedId > getAll().size() || index < 0) {
            //out of range
            throw new FeedNotFoundException();
        }
        return index;
    }

    @Override
    public boolean delete(int feedId) throws FeedNotFoundException {
        try {
            //remove feed from internal list, store on disk and update the id list
            getAll().remove(feedIdToIndex(feedId));
            writeToDisk();
            this.feeds = loadFeedsFromDisk();
        } catch (FeedNotFoundException e) {
            throw new FeedNotFoundException();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Feed feed) throws FeedNotFoundException {
        return delete(feed.getId());
    }
}
