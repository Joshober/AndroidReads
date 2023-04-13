package com.example.jsoupdroidscraping;

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

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Stories extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private ArrayList<RedditStory> Urls;
    //private MediaPlayer mediaPlayer;
    private ArrayList<String> url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        Bundle extras = getIntent().getExtras();
        String site = null;
        String photoUrl = null;
        if (extras != null) {
            site = extras.getString("key");
            photoUrl = extras.getString("Image");
        }
        LinearLayout parentLayout = findViewById(R.id.parent_layout);
        parentLayout.setVisibility(View.VISIBLE);
        parentLayout.setOrientation(LinearLayout.VERTICAL);


        Handler handler2 = new Handler(Looper.getMainLooper());
        Handler handler = new Handler(Looper.getMainLooper());
        String finalSite = site;
        String finalPhotoUrl = photoUrl;

        Callable<ArrayList<String>> newsCallable = () -> {
            NewsScraper n = new NewsScraper(finalSite);
            url = n.getUrl();
            return url;
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<ArrayList<String>> future = executor.submit(newsCallable);




        parentLayout.setVisibility(View.VISIBLE);
        LinearLayout buttonlayout = findViewById(R.id.HomeButtons);
        buttonlayout.setWeightSum(4);
        parentLayout.setOrientation(LinearLayout.VERTICAL);

        tts = new TextToSpeech(this, this);



        Handler handler3 = new Handler(Looper.getMainLooper());

handler.post(() -> {



    try {
            ArrayList<String> urls = future.get(); // retrieve the result from the FutureTask
            // use the URLs here
        JsoupScraper jsoupScraper = new JsoupScraper();

        for (String s : urls) {
                // do something with each URL

                    Thread CreateItems = new Thread(() -> {
                        try {
                            Stored dbHelper = Stored.getInstance(this);

                            RedditStory story = jsoupScraper.grabSources(s);
                            int[] playlistPhoto = {R.drawable.ic_action_added, R.drawable.ic_action_add};
                            int[] upArrowPhoto = {R.drawable.ic_action_up, R.drawable.ic_action_up_empty};
                            int[] DownArrowPhoto = {R.drawable.ic_action_down, R.drawable.ic_action_down_empty};

                                int[] ImageTracker = {0, 0, 0};
                                final TextView votes = CreateText(story.getUpvote(),10);
                                votes.setPadding(10,40,10,10);
                                votes.setLayoutParams(new LinearLayout.LayoutParams(80, ViewGroup.LayoutParams.MATCH_PARENT));
                                votes.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                final ImageView addTOPlaylist = CreateImage(R.drawable.ic_action_add);
                                addTOPlaylist.setOnClickListener(v -> {
                                    if (ImageTracker[0] == 1) {
                                        addTOPlaylist.setImageResource(playlistPhoto[1]);
                                        ImageTracker[0] = 0;
                                        dbHelper.deleteData(story.getTitle(),1);



                                    } else {
                                        addTOPlaylist.setImageResource(playlistPhoto[0]);
                                        ImageTracker[0] = 1;
                                        dbHelper.addData(story.getTitle(), story.getStory(),1);

                                    }
                                    //Add To playlist
                                });

                                final ImageView Redditsub = CreateImage(R.drawable.ic_reddit);

                            //Redditsub.setBackgroundColor(ContextCompat.getColor(Stories.this,R.color.redditorange));
                                //Picasso.get().load(story.getImageurl()).into(Redditsub);

                            final ImageView UpArrow = CreateImage(R.drawable.ic_action_up_empty);
                            UpArrow.setOnClickListener(v -> {
                                if (ImageTracker[1] == 0) {

                                    UpArrow.setImageResource(upArrowPhoto[0]);
                                    ImageTracker[1] = 1;
                                    dbHelper.addData(story.getTitle(), story.getStory(),1);

                                } else {
                                    UpArrow.setImageResource(upArrowPhoto[1]);
                                    ImageTracker[1] = 0;
                                    dbHelper.deleteData(story.getTitle(),1);


                                }
                                });



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

                                LinearLayout horizontalLayout = new LinearLayout(Stories.this);
                                horizontalLayout.setLayoutParams(layoutParams);
                                horizontalLayout.setVisibility(View.VISIBLE);
                                layoutParams.gravity = Gravity.START;

                                LinearLayout commentLine = new LinearLayout(Stories.this);
                                commentLine.setLayoutParams(layoutParams);
                                commentLine.setVisibility(View.VISIBLE);
                                commentLine.setPadding(10,0,10,60);
                                layoutParams.gravity = Gravity.START;
                                parentLayout.setWeightSum(3);
                                storyButton.setLayoutParams(layoutParams);
                                comments.setX(storyButton.getX());
                                comments.setPadding(10,20,10,10);

                                commentLine.setGravity(Gravity.CENTER);

                                Space space = new Space(Stories.this);
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
                                    //commentLine.addView(Reddit-sub);
                                    //commentLine.addView(subName);
                                    //commentLine.addView(comments);

                                    //horizontalLayout.addView(comments);

                                });

                                handler3.post(() -> {

                                    parentLayout.addView(horizontalLayout);
                                    parentLayout.addView(commentLine);
                                });


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });
                    CreateItems.start();
                }


        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
});

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
        TextView textView = new TextView(Stories.this);
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
        ImageView imageView = new ImageView(Stories.this);
        imageView.setImageResource(imageResource);
        imageView.setBackgroundColor(Color.TRANSPARENT);
        imageView.layout(100, 10, 100, 10);
        imageView.setPadding(10,30,10,10);
        return imageView;
    }



    private void speak(String story) throws IOException {

        if (tts != null) {
            tts.speak("test", TextToSpeech.QUEUE_FLUSH, null, null);

            for (int i =4; i+100<story.length();i+=100){
                tts.speak((story.substring(i,i+100)), TextToSpeech.QUEUE_ADD, null, null);

            }
            tts.speak((story.substring((int)(story.length()/100)*100)), TextToSpeech.QUEUE_ADD, null, null);
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
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
//
//        // Initialize a new MediaPlayer instance
//        mediaPlayer = new MediaPlayer();
//
//        // Set the audio stream type
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//        // Set a completion listener to release the MediaPlayer instance when playback is finished
//        mediaPlayer.setOnCompletionListener(mp -> {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        });
//
//        // Use the TTS to generate an audio file and load it to the MediaPlayer instance
//        try {
//            // Use the default language for the TTS output
//            int result = tts.setLanguage(Locale.getDefault());
//            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
//                // Generate the audio file from the TTS output
//                String utteranceId = "tts_utterance_" + System.currentTimeMillis();
//                tts.synthesizeToFile(text, null, new File(getCacheDir(), utteranceId + ".wav"), utteranceId);
//
//                // Load the audio file to the MediaPlayer instance
//                mediaPlayer.setDataSource(new File(getCacheDir(), utteranceId + ".wav").getPath());
//                mediaPlayer.prepare();
//
//                // Start playback
//                mediaPlayer.start();
//            }
//        } catch (IOException e) {
//            Log.e("Stories", "Failed to play TTS", e);
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

