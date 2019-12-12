package biz.braham.rssreader.models;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for holding and listing all feeds
 */
public class FeedList<E> extends ArrayList<Feed> {

    public static final String FEEDS_LOCATION = "feeds.xml";

    /**
     * Reads the feeds.xml file and convert it to this collection
     * If the feeds.xml file does not exist, an empty feed is returned
     *
     * @return feeds found in xml file or empty feed
     */
    public static FeedList initFromDiskFile() {
        FeedList feeds = new FeedList();
        File feedFile = new File(FEEDS_LOCATION);

        if (feedFile.exists()) {
            try {
                Document document = new SAXBuilder().build(feedFile);
                List<Element> items = document.getRootElement().getChildren();
                items.forEach(node -> {
                    try {
                        feeds.add(new Feed(node.getChildText("name"), new URL(node.getChildText("url"))));
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
    public void writeToDisk() throws IOException {
        Element feedsRoot = new Element("feeds");

        this.forEach((feed) -> {
            Element staff = new Element("feed");
            staff.addContent(new Element("name").setText(feed.getName()));
            staff.addContent(new Element("url").setText(feed.getUrl().toString()));
            feedsRoot.addContent(staff);
        });

        //store generated xml file to disk
        new XMLOutputter().output(new Document(feedsRoot), new FileWriter(FEEDS_LOCATION));
    }
}
