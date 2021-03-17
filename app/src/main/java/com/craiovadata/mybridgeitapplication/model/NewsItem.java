package com.craiovadata.mybridgeitapplication.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;


@Root(name = "item", strict = false)
public class NewsItem {

    public NewsItem() {
    }

    @Path("title")
    @Text(required = false)
    public
    String title;

    @Path("description")
    @Text(required = false)
    public String description;

    @Path("link")
    @Text(required = false)
    public String link;

    // Getters
//    public String getTitle() {
//        return title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getLink() {
//        return link;
//    }


}

