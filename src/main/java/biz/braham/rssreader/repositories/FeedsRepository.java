package biz.braham.rssreader.repositories;

import biz.braham.rssreader.models.Feed;

import java.util.List;

public interface FeedsRepository {

    List<Feed> getAll();
}
