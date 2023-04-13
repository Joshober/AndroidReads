package com.example.jsoupdroidscraping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        LinearLayout linear = findViewById(R.id.parent_layout);
        LinearLayout buttonlayout = findViewById(R.id.HomeButtons);

        LinearLayout Top = findViewById(R.id.Buttons);
        Top.setOrientation(LinearLayout.HORIZONTAL);
        ArrayList<RedditStory> Photos = new ArrayList<>();

        //Photos.add("https://theme.zdassets.com/theme_assets/968999/d8a347b41db1ddee634e2d67d08798c102ef09ac.jpg");
        //Photos.add("https://styles.redditmedia.com/t5_2xhvq/styles/communityIcon_8qil7zgp7oh81.png?width=256&v=enabled&s=a0d16b61a95d6ba1dab2245d3c8e06007807bdc2"')
//        for (NewsStory article : Photos){
//             Thread create = new Thread(() -> {
//                 ImageView photo = CreateTopic(article.getImageurl());
//                 TextView title = CreateText(article.getTitle(),15);
//                 handler1.post(() -> {
////
////                     linear.setVisibility(View.VISIBLE);
////                    linear.addView(photo);
////                    linear.addView(title);
////                 });
//
//
//             });
//
//
//        }
        ArrayList<String> SiteImageurls = new ArrayList<>();
        SiteImageurls.add("https://twt-assets.washtimes.com/img/WLogoNoBack.b80e1949f96a.png");
        SiteImageurls.add("https://media3.s-nbcnews.com/j/newscms/2018_21/2442281/og-nbcnews1200x630_c986de7e1bb6ad2281723b692aa61990.nbcnews-fp-1200-630.png");
        SiteImageurls.add("https://cdn.vox-cdn.com/thumbor/HZ56zrWLxaUkA-UeVqPT1XpbeDg=/0x0:2040x1360/1200x800/filters:focal(857x517:1183x843)/cdn.vox-cdn.com/uploads/chorus_image/image/72043024/vox_logo_4x3.0.jpg");
        SiteImageurls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Fox_News_Channel_logo.svg/1200px-Fox_News_Channel_logo.svg.png");
        SiteImageurls.add("https://www.restartlife.com/wp-content/uploads/logo-npr-white-1.png");
        SiteImageurls.add("https://styles.redditmedia.com/t5_2xhvq/styles/communityIcon_8qil7zgp7oh81.png?width=256&v=enabled&s=a0d16b61a95d6ba1dab2245d3c8e06007807bdc2");
        SiteImageurls.add("https://b.thumbs.redditmedia.com/r2KnsEBWjR1NN250YcjxHJyb_7DSuBxP5juTjDgotBw.png");
        SiteImageurls.add("https://styles.redditmedia.com/t5_67rd85/styles/communityIcon_xtw5wmoikct81.png?width=256&v=enabled&s=986ed41d3836738116a2fa7e9965e4fe55370699");
        SiteImageurls.add("https://styles.redditmedia.com/t5_ryjkl/styles/communityIcon_bnqwk61eez021.jpg?width=256&format=pjpg&v=enabled&s=eed792c73e51f83c91a013383381eefe3252055c");
        SiteImageurls.add("https://b.thumbs.redditmedia.com/c0DzkGpqaijNQvaLMqPAypqTv90jsrqGbqGRCppdC2E.png");
        SiteImageurls.add("https://styles.redditmedia.com/t5_2tqq8/styles/communityIcon_ac6lbwyqkzma1.png?width=256&v=enabled&s=a01f6efc0f3874f19c8d6ce71cb2c544d15c0f6d");
        SiteImageurls.add("https://styles.redditmedia.com/t5_2qh3l/styles/communityIcon_fmygcobc22z81.png?width=256&v=enabled&s=d02a244d581933b1ece73a637c104a6d7f1c4649");
        String[] SiteList ={"https://www.washingtonpost.com/","https://www.nbcnews.com/","https://www.vox.com/","https://www.foxnews.com/","https://www.npr.org/","https://www.reddit.com/r/AmItheAsshole/","https://www.reddit.com/r/shortstories/","https://www.reddit.com/r/AmITheJerkCommentary/","https://www.reddit.com/r/NuclearRevenge/","https://www.reddit.com/r/ProRevenge/","https://www.reddit.com/r/HorrorStory/"};

        ArrayList<String> Sitenames = new ArrayList<>();
        Sitenames.add("Washington Post");
        Sitenames.add("NBC News");
        Sitenames.add("Vox");
        Sitenames.add("Fox News");
        Sitenames.add("NPR");
        Sitenames.add("AITA");
        Sitenames.add("Short Stories");
        Sitenames.add("AmITheJerk?");
        Sitenames.add("Nuclear Revenge");
        Sitenames.add("Pro Revenge?");
        Sitenames.add("Horror Stories?");

        Handler hander1 = new Handler();
        TextView featured = CreateText("Featured Sites ",20);
    Handler handler = new Handler();
    handler.post(() -> linear.addView(featured));



        for (int x= 0; (x<9);x=x+2){
            LinearLayout horizontal = new LinearLayout(Home.this);
            LinearLayout story2 = new LinearLayout(Home.this);
            LinearLayout Story = new LinearLayout(Home.this);

            Story.setOrientation(LinearLayout.VERTICAL);
            story2.setOrientation(LinearLayout.VERTICAL);
            horizontal.setOrientation(LinearLayout.HORIZONTAL);
            ImageView article = CreateTopic(SiteImageurls.get(x));
            ImageView article2 = CreateTopic(SiteImageurls.get(x+1));
            TextView textView = CreateText(Sitenames.get(x),18);
            TextView textView2 = CreateText(Sitenames.get(x+1),18);
            textView2.setGravity(View.TEXT_ALIGNMENT_CENTER);
            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            Space space = new Space(Home.this);
            Space space2 = new Space(Home.this);
            LinearLayout.LayoutParams test = new LinearLayout.LayoutParams(
                    (200),
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            int finalX = x;
            story2.setOnClickListener(v -> {
                Intent intent = new Intent(this, Stories.class);
                intent.putExtra("key", SiteList[finalX+1]);
                intent.putExtra("Image", SiteImageurls.get(finalX+1));

                startActivity(intent);
            });
            int finalX1 = x;
            Story.setOnClickListener(v -> {
                Intent intent = new Intent(this, Stories.class);
                intent.putExtra("key", SiteList[finalX1]);
                intent.putExtra("Image", SiteImageurls.get(finalX1));
                startActivity(intent);
            });
            space.setLayoutParams(test);
            space2.setLayoutParams(test);
            space2.setPadding(100,0,100,0);
            hander1.post(() -> {
                Story.addView(article);
                Story.addView(textView);
                horizontal.addView(Story);
                horizontal.addView(space);
                story2.addView(article2);
                story2.addView(textView2);
                horizontal.addView(story2);
                linear.setVisibility(View.VISIBLE);
                linear.addView(horizontal);

            });
        }
                 TextView theposttitle = CreateText("The Washinton Post",15);
                 TextView NBCTItle = CreateText("NBC",15);


        final ImageView Playlist = CreateImage(R.drawable.ic_action_playlist);
        Playlist.setOnClickListener(v -> {
            Intent intent = new Intent(this, Playlist.class);
            startActivity(intent);
        });

        final ImageView HomeButton = CreateImage(R.drawable.ic_action_home);

        final ImageView Search = CreateImage(R.drawable.ic_action_find);
        Search.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        Playlist.setPadding(0,20,50,10);
        HomeButton.setPadding(150,20,200,10);






        Search.setPadding(40,20,220,10);
        linear.setVisibility(View.VISIBLE);
        buttonlayout.setWeightSum(4);
        linear.setOrientation(LinearLayout.VERTICAL);
        Handler handler3 = new Handler();
        handler3.post(() -> {
            buttonlayout.addView(HomeButton);
            buttonlayout.addView(Search);
            buttonlayout.addView(Playlist);

        });





    }
    private TextView CreateText(String text, int textsize) {
        TextView textView = new TextView(Home.this);
        textView.setText(text);
        textView.setTextSize(textsize);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setBackgroundColor(Color.TRANSPARENT);
        textView.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(450, ViewGroup.LayoutParams.MATCH_PARENT);

        textView.setLayoutParams(layoutParams);
        textView.setPadding(50,20,0,10);
        return textView;
    }
    private ImageView CreateImage(int imageResource) {
        ImageView imageView = new ImageView(Home.this);
        imageView.setImageResource(imageResource);
        imageView.setBackgroundColor(Color.TRANSPARENT);
        imageView.layout(100, 10, 100, 10);
        imageView.setPadding(80,30,40,10);
        return imageView;
    }
    private ImageView CreateTopic(String path) {
        ImageView imageView = new ImageView(Home.this);
        //imageView.setImageResource(imageResource);
        Picasso.get().load(path).resize(300, 300).into(imageView);

        imageView.setBackgroundColor(Color.TRANSPARENT);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300,300);
        imageView.setLayoutParams(layoutParams);
        imageView.setPadding(40,30,20,10);
        return imageView;
    }
}
