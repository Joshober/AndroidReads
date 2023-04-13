package com.example.jsoupdroidscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JsoupScraper {
    private static final String Story_Text_Class = "_1qeIAgB0cPwnLhDF9XSiJM";
    private static final String Story_Comments_class = "FHCV02u6Cp2zYL0fhQPsO";
    private static final String Story_upvotes_class = "_1rZYMD_4xY3gRcSS3p8ODO _25IkBM0rRUqWX5ZojEMAFQ";
    public RedditStory grabSources( String url) {
        RedditStory story = new RedditStory();
            ArrayList<String> StoryText = new ArrayList<>();

            Document doc;
            try {

                doc = Jsoup.connect(url).get();
                story.setTitle(doc.title());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements div = doc.select("div");

            StoryText.add(" ");
            for (Element a : div) {
                //Iterate elements and print link attributes.

                    if ((!a.text().startsWith("Send") && !a.text().endsWith("Inc.") && (!a.text().endsWith("Network.")))) {
                        if (a.text().endsWith("!") || a.text().endsWith("?") || a.text().endsWith(".") || a.text().endsWith("\"")) {
                            if (!Objects.equals(StoryText.get(StoryText.size() - 1), a.text())) {
                                if(StoryText.size()<10) {
                                    if(a.text() != "null" ) {
                                        story.setStory(story.getStory() + a.text());
                                    }
                                }
                            }
                        }


                    }


            }
            String listString = String.join(" ", StoryText);
           ;







        return story;
    }

    public List<RedditStory> extractProducts(String url) {
        List<RedditStory> RedditStories = new ArrayList<>();

        Document doc;
        try {
            doc = Jsoup.connect(url).get();
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
            Element Photo = doc.getElementsByClass("Mh_Wl6YioFfBc9O1SQ4Jp u0UgpXN5r-VO6PP9OAViq ").first();
            if (Photo != null) {
                story.setImageurl(Photo.absUrl("src"));
            }
            if (Photo != null) {
                story.setImageurl(Photo.absUrl("src"));
            }
            story.setStory(storyBorder.getElementsByClass(Story_Text_Class).text());
              Element Redditname = doc.getElementsByClass("_2yYPPW47QxD4lFQTKpfpLQ").first();

            if (Redditname != null) {
                story.setSub(Redditname.text());
            }
            // Get author
            Elements comment = storyBorder.getElementsByClass(Story_Comments_class);
            story.setComment(comment.text());
            Elements upvotes = storyBorder.getElementsByClass(Story_upvotes_class);

            story.setUpvote(upvotes.text());

            if (!Objects.equals(story.getStory(), ""))
                RedditStories.add(story);
        }

        return RedditStories;    }
}
