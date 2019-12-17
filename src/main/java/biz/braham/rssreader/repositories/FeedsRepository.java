package biz.braham.rssreader.repositories;

import biz.braham.rssreader.exceptions.StoreFeedException;
import biz.braham.rssreader.models.Feed;

import java.net.URL;
import java.util.List;

public interface FeedsRepository {

    List<Feed> getAll();

    Feed store(String name, URL url) throws StoreFeedException;
}
