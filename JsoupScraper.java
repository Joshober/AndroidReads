package com.example.jsoupdroidscraping;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsoupScraper {
    private static final String Story_Title_Class = "_eYtD2XCVieq6emjKBH3m";
    private static final String Story_Border_Class = "_1poyrkZ7g36PawDueRza-J _11R7M_VOgKO1RJyRSRErT3 ";
    private static final String Story_Text_Class = "_1qeIAgB0cPwnLhDF9XSiJM";
    private static final String Story_Author_Class = "a._2tbHP6ZydRpjI44J3syuqC";
    private static final String Story_Comments_class = "FHCV02u6Cp2zYL0fhQPsO";
    private static final String Story_upvotes_class = "_1rZYMD_4xY3gRcSS3p8ODO _25IkBM0rRUqWX5ZojEMAFQ";
    public List<RedditStory> extractProducts() {
        List<RedditStory> RedditStories = new ArrayList<>();

        Document doc;
        try {
            doc = Jsoup.connect("https://www.reddit.com/r/AmItheAsshole").get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements storyBorders = doc.select("div.Post");
        //Elements storyBorders = doc.getElementsByClass("_1poyrkZ7g36PawDueRza-J _11R7M_VOgKO1RJyRSRErT3");


        for (Element storyBorder : storyBorders) {
            RedditStory story = new RedditStory();

            // Get title
            Element titleElement = storyBorder.getElementsByClass("_eYtD2XCVieq6emjKBH3m").first();
            if (titleElement != null) {
                story.setTitle(titleElement.text());
            }

            // Get story
            story.setStory(storyBorder.getElementsByClass(Story_Text_Class).text());


            // Get author
            Elements comment = storyBorder.getElementsByClass(Story_Comments_class);
            if (comment != null) {
                story.setComment(comment.text());
            }
            Elements upvotes = storyBorder.getElementsByClass(Story_upvotes_class);
            if (upvotes != null) {

                story.setUpvote(upvotes.text());

            }
            if (story.getStory()!="")
                RedditStories.add(story);
        }

        return RedditStories;    }
}
