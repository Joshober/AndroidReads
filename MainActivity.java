package com.example.jsoupdroidscraping;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout parentLayout = findViewById(R.id.parent_layout);
        parentLayout.setOrientation(LinearLayout.VERTICAL);

        tts = new TextToSpeech(getApplicationContext(), this);




        Handler handler2 = new Handler(Looper.getMainLooper());
        Handler handler = new Handler(Looper.getMainLooper());
        Thread makeJsoup = new Thread(() -> {
            try {
                JsoupScraper jsoupScraper = new JsoupScraper();
                List<RedditStory> stories = jsoupScraper.extractProducts();
                int[] playlistPhoto = {R.drawable.ic_action_added,R.drawable.ic_action_add};
                int[] upArrowPhoto = {R.drawable.ic_action_up,R.drawable.ic_action_up_empty};
                int[] DownArrowPhoto = {R.drawable.ic_action_down,R.drawable.ic_action_down_empty};

                for (RedditStory story : stories) {
                    int[] ImageTracker = {0,0,0};
                    final TextView votes = new TextView(MainActivity.this);
                    final ImageView addTOPlaylist = new ImageView(MainActivity.this);
                    addTOPlaylist.setImageResource(R.drawable.ic_action_add);
                    addTOPlaylist.setBackgroundColor(Color.TRANSPARENT);
                    addTOPlaylist.layout(100,10,100,10);

                    addTOPlaylist.setOnClickListener(v -> {
                        if (ImageTracker[0] == 1){
                            addTOPlaylist.setImageResource(playlistPhoto[1]);
                            ImageTracker[0] = 0;
                        }
                        else{
                            addTOPlaylist.setImageResource(playlistPhoto[0]);
                            ImageTracker[0] = 1;
                        }
                        //Add To playlist
                    });
                    final ImageView UpArrow = new ImageView(MainActivity.this);
                    UpArrow.setImageResource(R.drawable.ic_action_up_empty);
                    UpArrow.setBackgroundColor(Color.TRANSPARENT);
                    UpArrow.layout(100,10,100,10);
                    UpArrow.setOnClickListener(v -> {
                        if (ImageTracker[1] == 1){
                            UpArrow.setImageResource(upArrowPhoto[1]);
                            ImageTracker[1] = 0;
                        }
                        else{
                            UpArrow.setImageResource(upArrowPhoto[0]);
                            ImageTracker[1] = 1;
                        }
                    });
                    final ImageView DownArrow = new ImageView(MainActivity.this);
                    DownArrow.setImageResource(R.drawable.ic_action_down_empty);
                    DownArrow.setBackgroundColor(Color.TRANSPARENT);
                    DownArrow.layout(100,100,100,100);
                    DownArrow.setOnClickListener(v -> {
                        if (ImageTracker[2] == 1){
                            DownArrow.setImageResource(DownArrowPhoto[1]);
                            ImageTracker[2] = 0;
                        }
                        else{
                            DownArrow.setImageResource(DownArrowPhoto[0]);
                            ImageTracker[2] = 1;
                        }
                    });
                    //UpArrow.setScaleY(5);



                    votes.setText(story.getUpvote());
                    votes.setTextSize(10);
                    votes.setSingleLine(true);
                    votes.setEllipsize(TextUtils.TruncateAt.END);
                    votes.setBackgroundColor(Color.TRANSPARENT);
                    votes.setTextColor(Color.WHITE);


                    final TextView comments = new TextView(MainActivity.this);
                    comments.setText(story.getComment());
                    comments.setTextSize(5);
                    comments.setSingleLine(true);
                    comments.setEllipsize(TextUtils.TruncateAt.END);
                    comments.setBackgroundColor(Color.TRANSPARENT);
                    comments.setTextColor(Color.WHITE);
                    comments.setOnClickListener(v -> {
                        try {
                            speak(story.getStory());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    final TextView storyButton = new TextView(MainActivity.this);
                    storyButton.setText(story.getTitle());
                    storyButton.setSingleLine(true);
                    storyButton.setEllipsize(TextUtils.TruncateAt.END);
                    storyButton.setBackgroundColor(Color.TRANSPARENT);
                    storyButton.setTextColor(Color.WHITE);
                    storyButton.setOnClickListener(v -> {
                        try {
                            speak(story.getStory());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    storyButton.setTextSize(10);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(0,0,0,0);
                    LinearLayout horizontalLayout = new LinearLayout(MainActivity.this);
                    horizontalLayout.setLayoutParams(layoutParams);
                    horizontalLayout.setVisibility(View.VISIBLE);
                    layoutParams.gravity = Gravity.START;
                    LinearLayout commentLine = new LinearLayout(MainActivity.this);
                    commentLine.setLayoutParams(layoutParams);
                    commentLine.setVisibility(View.VISIBLE);
                    layoutParams.gravity = Gravity.START;
                    parentLayout.setWeightSum(3);
                    storyButton.setLayoutParams(layoutParams);
                    comments.setX(storyButton.getX());
                    commentLine.setGravity(Gravity.CENTER);

                    Space space = new Space(MainActivity.this);
                    LinearLayout.LayoutParams test = new LinearLayout.LayoutParams(
                            (250),
                            LinearLayout.LayoutParams.MATCH_PARENT                                      // height in pixels
                    );
                    space.setLayoutParams(test);



                    handler2.post(() -> {
                        Log.d("Created", String.valueOf(parentLayout.getParent()));
                        horizontalLayout.addView(addTOPlaylist);
                        horizontalLayout.addView(UpArrow);
                        horizontalLayout.addView(votes);
                        horizontalLayout.addView(DownArrow);
                        horizontalLayout.addView(storyButton);
                        commentLine.addView(space);
                        commentLine.addView(comments);

                       //horizontalLayout.addView(comments);

                    });

                    handler.post(() -> {

                        parentLayout.addView(horizontalLayout);
                        parentLayout.addView(commentLine);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


        makeJsoup.start();
    }
  
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Set the language for the TextToSpeech engine
            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("MainActivity", "Language is not supported");
            }
        } else {
            Log.e("MainActivity", "Initialization failed");
        }

    }
    private void speak(String story) throws IOException {
        new MediaPlayer();
        MediaPlayer mediaPlayer;

        if (tts != null) {
            tts.speak(story, TextToSpeech.QUEUE_FLUSH, null, null);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource("speech.wav");
            mediaPlayer.prepare();
            mediaPlayer.start();
        } else {
            Log.e("MainActivity", "TextToSpeech engine not initialized");
        }
    }
    @Override
    protected void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
}