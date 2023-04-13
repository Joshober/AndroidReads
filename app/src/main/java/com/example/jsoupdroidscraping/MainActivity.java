package com.example.jsoupdroidscraping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
private TextToSpeech tts;
private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout parentLayout = findViewById(R.id.parent_layout);
        parentLayout.setVisibility(View.VISIBLE);
        LinearLayout buttonlayout = findViewById(R.id.HomeButtons);
        buttonlayout.setWeightSum(4);
        parentLayout.setOrientation(LinearLayout.VERTICAL);

        tts = new TextToSpeech(this, this);



        Handler handler3 = new Handler(Looper.getMainLooper());
        Handler handler2 = new Handler(Looper.getMainLooper());
        Handler handler = new Handler(Looper.getMainLooper());
        ArrayList<String> url = new ArrayList<>();
        url.add("https://www.reddit.com/r/AmItheAsshole/new");
        url.add("https://www.reddit.com/r/AmItheAsshole");
        url.add("https://www.reddit.com/r/shortscarystories/");
        url.add("https://www.reddit.com/r/shortstories/");
        url.add("https://www.reddit.com/r/amithejerkpodcast/");
       for (String s : url) {
           Thread CreateItems = new Thread(() -> {
               try {

                   JsoupScraper jsoupScraper = new JsoupScraper();
                   List<RedditStory> stories = jsoupScraper.extractProducts(s);
                   int[] playlistPhoto = {R.drawable.ic_action_added, R.drawable.ic_action_add};
                   int[] upArrowPhoto = {R.drawable.ic_action_up, R.drawable.ic_action_up_empty};
                   int[] DownArrowPhoto = {R.drawable.ic_action_down, R.drawable.ic_action_down_empty};

                   for (RedditStory story : stories) {
                       Stored dbHelper = Stored.getInstance(this);

                       int[] ImageTracker = {0, 0, 0};
                       final TextView votes = CreateText(story.getUpvote(),10);
                       votes.setPadding(10,40,10,10);
                       votes.setLayoutParams(new LinearLayout.LayoutParams(80, ViewGroup.LayoutParams.MATCH_PARENT));
                       votes.setGravity(View.TEXT_ALIGNMENT_CENTER);
                       final ImageView addTOPlaylist = CreateImage(R.drawable.ic_action_add);
                       addTOPlaylist.setOnClickListener(v -> {
                           if (ImageTracker[0] == 1) {
                               addTOPlaylist.setImageResource(playlistPhoto[1]);
                               ImageTracker[0] = 0;                               dbHelper.deleteData(story.getTitle(),2);
                               dbHelper.deleteData(story.getTitle(),1);

                           } else {
                               addTOPlaylist.setImageResource(playlistPhoto[0]);
                               ImageTracker[0] = 1;
                               dbHelper.addData(story.getTitle(), story.getStory(),1);

                           }

// Insert data into the database

                           //Add To playlist
                       });

                       final ImageView Redditsub = CreateImage(R.drawable.ic_reddit);

                       Redditsub.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.redditorange));
                       //Picasso.get().load(story.getImageurl()).into(Redditsub);

                       final ImageView UpArrow = CreateImage(R.drawable.ic_action_up_empty);
                       UpArrow.setOnClickListener(v -> {
                           if (ImageTracker[1] == 0) {
                               UpArrow.setImageResource(upArrowPhoto[1]);
                               ImageTracker[1] = 1;
                               dbHelper.addData(story.getTitle(), story.getStory(),2);

                           } else {
                               UpArrow.setImageResource(upArrowPhoto[0]);
                               ImageTracker[1] = 0;
                               dbHelper.deleteData(story.getTitle(),2);
                           }

                       });
//                       final ImageView DownArrow = CreateImage(R.drawable.ic_action_down_empty);
//                       DownArrow.setOnClickListener(v -> {
//                           if (ImageTracker[2] == 1) {
//                               DownArrow.setImageResource(DownArrowPhoto[1]);
//                               ImageTracker[2] = 0;
//                           } else {
//                               DownArrow.setImageResource(DownArrowPhoto[0]);
//                               ImageTracker[2] = 1;
//                           }
//                       });


                       final TextView subName = CreateText(story.getSub(),10);

                       final TextView comments = CreateText(story.getComment(),10);
                       comments.setPadding(10,10,50,10);

                       comments.setOnClickListener(v -> {
                           try {
                               speak(story.getStory());
                           } catch (IOException e) {
                               throw new RuntimeException(e);
                           }
                       });
                       final TextView storyButton = CreateText(story.getTitle(),16);
                       storyButton.setSingleLine(false);
                       storyButton.setOnClickListener(v -> {
                           try {
                               speak(story.getStory());
                           } catch (IOException e) {
                               throw new RuntimeException(e);
                           }
                       });
                       LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                               LinearLayout.LayoutParams.WRAP_CONTENT,
                               LinearLayout.LayoutParams.WRAP_CONTENT
                       );

                       layoutParams.setMargins(0, 0, 0, 0);

                       LinearLayout horizontalLayout = new LinearLayout(MainActivity.this);
                       horizontalLayout.setLayoutParams(layoutParams);
                       horizontalLayout.setVisibility(View.VISIBLE);
                       layoutParams.gravity = Gravity.START;

                       LinearLayout commentLine = new LinearLayout(MainActivity.this);
                       commentLine.setLayoutParams(layoutParams);
                       commentLine.setVisibility(View.VISIBLE);
                       commentLine.setPadding(10,0,10,60);
                       layoutParams.gravity = Gravity.START;
                       parentLayout.setWeightSum(3);
                       storyButton.setLayoutParams(layoutParams);
                       comments.setX(storyButton.getX());
                       comments.setPadding(10,20,10,10);

                       commentLine.setGravity(Gravity.CENTER);

                       Space space = new Space(MainActivity.this);
                       LinearLayout.LayoutParams test = new LinearLayout.LayoutParams(
                               (180),
                               LinearLayout.LayoutParams.MATCH_PARENT
                       );
                       space.setLayoutParams(test);


                       handler2.post(() -> {
                           Log.d("Created", String.valueOf(parentLayout.getParent()));
                           horizontalLayout.addView(addTOPlaylist);
                           horizontalLayout.addView(UpArrow);
                           //horizontalLayout.addView(votes);
                           //horizontalLayout.addView(DownArrow);
                           horizontalLayout.addView(storyButton);
                           commentLine.addView(space);
                           commentLine.addView(Redditsub);
                           commentLine.addView(subName);
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
           CreateItems.start();
       }


        final ImageView Playlist = CreateImage(R.drawable.ic_action_playlist);
        Playlist.setOnClickListener(v -> {
            Intent intent = new Intent(this, Playlist.class);
            startActivity(intent);
        });
        final ImageView HomeButton = CreateImage(R.drawable.ic_action_home);
        HomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        });
        final ImageView Search = CreateImage(R.drawable.ic_action_find);
        Playlist.setPadding(0,20,50,10);
        HomeButton.setPadding(150,20,200,10);

        Search.setPadding(40,20,220,10);

        handler3.post(() -> {
            parentLayout.setVisibility(View.VISIBLE);
            buttonlayout.addView(HomeButton);
            buttonlayout.addView(Search);
            buttonlayout.addView(Playlist);

        });




    }



    private TextView CreateText(String text,int textsize) {
        TextView textView = new TextView(MainActivity.this);
        textView.setText(text);
        textView.setTextSize(textsize);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setBackgroundColor(Color.TRANSPARENT);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(10,20,10,10);
        return textView;
    }
    private ImageView CreateImage(int imageResource) {
        ImageView imageView = new ImageView(MainActivity.this);
        imageView.setImageResource(imageResource);
        imageView.setBackgroundColor(Color.TRANSPARENT);
        imageView.layout(100, 10, 100, 10);
        imageView.setPadding(10,30,10,10);
        return imageView;
    }



    private void speak(String story) throws IOException {

        if (tts != null) {
            tts.speak(story, TextToSpeech.QUEUE_FLUSH, null, null);
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
//    private void playTTS(String text) {
//        // Stop any previous playback
//        tts.setLanguage(Locale.US);
//
//        // Set up media player
//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mediaPlayer.setOnCompletionListener(mp -> {
//            // Release media player when playback is completed
//            mp.release();
//        });
//
//        // Save TTS output to temporary audio file
//
//
//        tts.synthesizeToFile(text, null, tempAudioFile, "tts");
//
//        // Play the temporary audio file with media player
//        try {
//            mediaPlayer.setDataSource(tempAudioFile.getPath());
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported");
            } else {
                // TTS engine initialized successfully
            }
        } else {
            Log.e("TTS", "Initialization failed");
        }
    }
    }
