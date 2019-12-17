package biz.braham.rssreader.repositories;

import biz.braham.rssreader.exceptions.FeedNotFoundException;
import biz.braham.rssreader.exceptions.StoreFeedException;
import biz.braham.rssreader.models.Feed;

import java.net.URL;
import java.util.List;

public interface FeedsRepository {

    List<Feed> getAll();

    Feed store(String name, URL url) throws StoreFeedException;

    boolean delete(int feedId) throws FeedNotFoundException;

    boolean delete(Feed feed) throws FeedNotFoundException;

    Feed getFeedById(int feedId) throws FeedNotFoundException;

    boolean update(Feed feed) throws FeedNotFoundException;

}
