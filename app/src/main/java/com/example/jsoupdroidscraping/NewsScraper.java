package com.example.jsoupdroidscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

/**
 * This class is used get links from HTML using Jsoup.
 * @author w3spoint
 */
public class NewsScraper {
    public ArrayList<String> getUrl() {
        return News;
    }

    public void setNews(ArrayList<String> seturl) {
        News = seturl;
    }

    private ArrayList<String> News = new ArrayList<>();

    public NewsScraper(String url){
        Document document;
        try {
            //Get Document object after parsing the html from given url.
            document = Jsoup.connect(url).get();
            Elements div = document.select("div");
            //Get links from document object.
            ;
            String newsStory2 = " ";

            News.add(newsStory2);
            for (Element a : div) {

                Elements elements = a.select("a[href]");
                //Iterate elements and print link attributes.
                for (Element links : elements) {
                    if (links != null) {
                        if (links.text().length() > 40 && (links.attr("href")).startsWith("h")) {
                            if (!links.attr("href").endsWith("ere")){
                            if ((News.get(News.size() - 1)).equals(links.attr("href")) == false) {
                                if (News.size() < 10) {
                                    News.add(links.attr("href"));
                                    System.out.println("Added");
                                }


                            }
                            }

                        }
                    }
                }
            }
            News.remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}