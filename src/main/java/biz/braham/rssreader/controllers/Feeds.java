package biz.braham.rssreader.controllers;

import biz.braham.rssreader.repositories.FeedsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class Feeds {

    @Autowired
    FeedsRepository feedsRepository;

    @ShellMethod("List all available feeds")
    public void listFeeds() {
        feedsRepository.getAll().forEach(feed -> {
            System.out.println(feed.getName());
        });
    }
}
