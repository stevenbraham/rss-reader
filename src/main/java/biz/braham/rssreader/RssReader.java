package biz.braham.rssreader;

import biz.braham.rssreader.models.Feed;
import biz.braham.rssreader.models.FeedList;

import java.util.Scanner;

/**
 * Main menu class
 */
public class RssReader {

    private FeedList<Feed> feeds = new FeedList();

    public RssReader() {
        this.feeds = FeedList.initFromDiskFile();
    }

    public static void main(String[] args) {
        System.out.println("Welcome to RSS Reader");
        RssReader app = new RssReader();
        app.displayMainMenu();
    }

    public void listFeeds() {
        this.feeds.forEach(feed -> {
            System.out.println(feed.getName());
        });
    }

    private void displayMainMenu() {
        System.out.println("Please make a selection:\n");
        System.out.println("1: " + "List feeds");
        System.out.println("2: " + "Exit app");
        options choice = readOption();
        switch (choice) {
            case LIST_FEEDS:
                listFeeds();
                break;
            case EXIT_APP:
                exitApp();
                break;
            default:
                System.out.println(choice + " is not a valid option");
                displayMainMenu();
                break;
        }
    }

    /**
     * Reads a single integer from the user input and converts it to an enum
     *
     * @return detected option, invalid option on error
     */
    private options readOption() {
        try {
            Scanner inputScanner = new Scanner(System.in);
            int choice = inputScanner.nextInt();
            options[] possibleValues = options.values();
            if (choice > 0 && choice < possibleValues.length) {
                return possibleValues[choice];
            }
            return options.INVALID;
        } catch (Exception e) {
            return options.INVALID;
        }

    }

    void exitApp() {
        System.out.println("Goodbye");
        System.exit(0);
    }

    enum options {
        INVALID,
        LIST_FEEDS,
        EXIT_APP
    }

}
