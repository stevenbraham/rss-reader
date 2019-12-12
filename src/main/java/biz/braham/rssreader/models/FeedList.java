package biz.braham.rssreader.models;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for holding and listing all feeds
 */
public class FeedList<E> extends ArrayList<Feed> {

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
        new XMLOutputter().output(new Document(feedsRoot), new FileWriter("feeds.xml"));
    }
}
