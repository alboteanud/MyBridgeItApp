package com.craiovadata.mybridgeitapplication.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class NewsItems {

    @Element
    private Channel channel;

    public List<NewsItem> getNews() {
        return channel.getList();
    }

    @Root(strict = false)
    private static class Channel {

        public List<NewsItem> getList() {
            return list;
        }

        @ElementList(inline = true)
        private List<NewsItem> list;

    }



}
