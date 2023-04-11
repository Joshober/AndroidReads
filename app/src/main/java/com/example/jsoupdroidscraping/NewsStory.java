package com.example.jsoupdroidscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NewsStory {
    private String url;
    private String Title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    NewsStory(String url,String Title){
        this.url = url;
        this.Title = Title;
    }
    public RedditStory grabStory(String s){
        RedditStory story = new RedditStory();
        try {
            //Get Document object after parsing the html from given url.
            Document document = Jsoup.connect(s).get();
            Elements div = document.select("div");
            //Get links from document object.
            ;
            for (Element a : div) {
                Elements title = a.getElementsByClass("css-s99gbd StoryBodyCompanionColumn");
                //Iterate links and print link attributes.

                    story.setTitle(this.getTitle());
                    story.setStory(title.text());


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    return story;
    }
    }

