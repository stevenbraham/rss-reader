package biz.braham.rssreader.repositories;

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

        feeds.forEach((feed) -> {
            Element feedElement = new Element("feed");
            feedElement.addContent(new Element("name").setText(feed.getName()));
            feedElement.addContent(new Element("url").setText(feed.getUrl().toString()));
            feedsRoot.addContent(feedElement);
        });

        //store generated xml file to disk
        new XMLOutputter().output(new Document(feedsRoot), new FileWriter(FEEDS_LOCATION));
    }
}
