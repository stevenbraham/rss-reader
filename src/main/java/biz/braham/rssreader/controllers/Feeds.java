package biz.braham.rssreader.controllers;

import biz.braham.rssreader.exceptions.FeedNotFoundException;
import biz.braham.rssreader.models.Feed;
import biz.braham.rssreader.repositories.FeedsRepository;
import biz.braham.rssreader.services.RssReaderService;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@ShellComponent
public class Feeds {

    @Autowired
    FeedsRepository feedsRepository;

    @Autowired
    RssReaderService rssReaderService;

    @ShellMethod("List all available feeds")
    public void listFeeds() {
        feedsRepository.getAll().forEach(feed -> {
            System.out.println("[" + feed.getId() + "]\t" + feed.getName());
        });
    }

    @ShellMethod("Read the news from a feed")
    public void readFeed(int feedId) {
        try {
            Feed feed = feedsRepository.getFeedById(feedId);
            rssReaderService.readFeed(feed).forEach((newsItem) -> {
                //print and format news items
                System.out.println(WordUtils.wrap(newsItem.getTitle().toUpperCase(), 77));
                System.out.println(newsItem.getPubDate());
                System.out.println(WordUtils.wrap(newsItem.getDescription(), 77));
                System.out.println("-----------------------------------------------------------------------------");
            });
        } catch (FeedNotFoundException e) {
            System.out.println("Feed #" + feedId + " does not exist");
        } catch (Exception e) {
            System.out.println("Error reading feed");
        }
    }

    @ShellMethod("Store a new feed")
    public void addFeed() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Feed name: ");
            String feedName = input.nextLine();
            System.out.print("Feed url: ");
            URL feedUrl = new URL(input.nextLine());

            System.out.println("Name:\t" + feedName);
            System.out.println("Url:\t" + feedUrl);
            System.out.print("Is this correct? [y/n] ");
            if (input.nextLine().equals("y")) {
                Feed newFeed = feedsRepository.store(feedName, feedUrl);
                System.out.println("Added new feed with id " + newFeed.getId());
            } else {
                System.out.println("Adding feed cancelled");
            }


        } catch (MalformedURLException e) {
            System.out.println("Please enter a correctly formatted url");
        } catch (Exception e) {
            System.out.println("There was an unexpected error processing your request");
        }

    }

    @ShellMethod("Edit a feed with a given id")
    public void editFeed(int feedId) {
        try {
            Feed feed = feedsRepository.getFeedById(feedId);
            Scanner input = new Scanner(System.in);
            System.out.println("Type a new name for this feed:");
            System.out.println("[Current value: " + feed.getName() + "]");
            String feedName = input.nextLine();
            System.out.println("Type a new url for this feed:");
            System.out.println("[Current value: " + feed.getUrl() + "]");
            String feedUrl = input.nextLine();

            if (!feedName.equals("") && !feedName.equals(feed.getName())) {
                feed.setName(feedName);
            }

            if (!feedUrl.equals("") && !feedUrl.equals(feed.getUrl().toString())) {
                feed.setUrl(new URL(feedUrl));
            }
            feedsRepository.update(feed);
            System.out.println("Item updated");

        } catch (FeedNotFoundException e) {
            System.out.println("Feed #" + feedId + " does not exist");
        } catch (Exception e) {
            System.out.println("There was an unexpected error processing your request");
        }
    }

    @ShellMethod("Delete a feed with a given id")
    public void deleteFeed(int feedId) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Are you sure you want to delete this feed? [y/n] ");
            if (input.nextLine().equals("y")) {
                feedsRepository.delete(feedId);
                System.out.println("Feed #" + feedId + " has been deleted");
            }

        } catch (FeedNotFoundException e) {
            System.out.println("Feed #" + feedId + " does not exist");
        } catch (Exception e) {
            System.out.println("There was an unexpected error processing your request");
        }
    }
}
