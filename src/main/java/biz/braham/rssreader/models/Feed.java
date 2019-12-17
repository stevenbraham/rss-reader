package biz.braham.rssreader.models;

import java.net.URL;

public class Feed {
    private int id;
    private String name;
    private URL url;

    public Feed(int id, String name, URL url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}

