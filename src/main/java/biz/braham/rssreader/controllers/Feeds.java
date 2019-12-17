package biz.braham.rssreader.controllers;

import biz.braham.rssreader.models.Feed;
import biz.braham.rssreader.repositories.FeedsRepository;
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

    @ShellMethod("List all available feeds")
    public void listFeeds() {
        feedsRepository.getAll().forEach(feed -> {
            System.out.println("[" + feed.getId() + "]\t" + feed.getName());
        });
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
            System.out.println("Is this correct? [y/n]");
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
}
