package com.example.jsoupdroidscraping;

import static android.view.View.GONE;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
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

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class Playlist extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        LinearLayout parentLayout = findViewById(R.id.parent_layout);
        parentLayout.setVisibility(View.VISIBLE);
        LinearLayout buttonlayout = findViewById(R.id.HomeButtons);
        buttonlayout.setWeightSum(4);
        parentLayout.setOrientation(LinearLayout.VERTICAL);

        tts = new TextToSpeech(this, this);


        Handler handler3 = new Handler(Looper.getMainLooper());
        Handler handler2 = new Handler(Looper.getMainLooper());
        Stored dbHelper = Stored.getInstance(this);
        Cursor cursor = dbHelper.getAllDataByListType(2);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            // Do something with the data




                    final TextView storyButton = CreateText(name,16);
                    storyButton.setSingleLine(false);
                    storyButton.setOnClickListener(v -> {
                        try {
                            speak(description);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    layoutParams.setMargins(0, 0, 0, 0);

                    LinearLayout horizontalLayout = new LinearLayout(Playlist.this);
                    horizontalLayout.setLayoutParams(layoutParams);
                    horizontalLayout.setVisibility(View.VISIBLE);
                    layoutParams.gravity = Gravity.START;



            final ImageView AddPlaylist = CreateImage(R.drawable.ic_action_added);
            AddPlaylist.setOnClickListener(v -> {

                AddPlaylist.setVisibility(GONE);
                storyButton.setVisibility(GONE);

                dbHelper.deleteData(name,2);


            });

                    Space space = new Space(Playlist.this);
                    LinearLayout.LayoutParams test = new LinearLayout.LayoutParams(
                            (180),
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );
                    space.setLayoutParams(test);


                    handler2.post(() -> {
                        Log.d("Created", String.valueOf(parentLayout.getParent()));
                        horizontalLayout.addView(AddPlaylist);
                        horizontalLayout.addView(storyButton);


                    });

                    handler3.post(() -> {

                        parentLayout.addView(horizontalLayout);





            });
}

        cursor.close();
        Cursor cursor2 = dbHelper.getAllDataByListType(1);
        while (cursor2.moveToNext()) {
            String name = cursor2.getString(cursor.getColumnIndexOrThrow("name"));
            String description = cursor2.getString(cursor.getColumnIndexOrThrow("description"));
            // Do something with the data




            final TextView storyButton = CreateText(name,16);
            storyButton.setSingleLine(false);
            storyButton.setOnClickListener(v -> {
                try {
                    speak(description);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            layoutParams.setMargins(0, 0, 0, 0);

            LinearLayout horizontalLayout = new LinearLayout(Playlist.this);
            horizontalLayout.setLayoutParams(layoutParams);
            horizontalLayout.setVisibility(View.VISIBLE);
            layoutParams.gravity = Gravity.START;


            final ImageView UpArrow = CreateImage(R.drawable.ic_action_up);


            UpArrow.setOnClickListener(v -> {

                UpArrow.setVisibility(GONE);
                storyButton.setVisibility(GONE);
                dbHelper.deleteData(name,1
                );


            });



            handler2.post(() -> {
                Log.d("Created", String.valueOf(parentLayout.getParent()));
                horizontalLayout.addView(UpArrow);
                horizontalLayout.addView(storyButton);


            });

            handler3.post(() -> {

                parentLayout.addView(horizontalLayout);





            });
        }

        cursor2.close();
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
        TextView textView = new TextView(Playlist.this);
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
        ImageView imageView = new ImageView(Playlist.this);
        imageView.setImageResource(imageResource);
        imageView.setBackgroundColor(Color.TRANSPARENT);
        imageView.layout(100, 10, 100, 10);
        imageView.setPadding(10,30,10,10);
        return imageView;
    }



    private void speak(String story) throws IOException {
        // Initialize MediaPlayer
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // Set a listener to release the MediaPlayer resources when playback is completed
        mediaPlayer.setOnCompletionListener(mp -> {
            mediaPlayer.release();
            mediaPlayer = null;
        });

        // Set the data source for MediaPlayer to read from a text-to-speech stream
        mediaPlayer.setDataSource(story);

        // Prepare and start MediaPlayer
        mediaPlayer.prepare();
        mediaPlayer.start();
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
//            mediaPlayer = null;
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
//            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("Page 3", "Failed to set language for TTS");
//                return;
//            }
//
//            // Generate the audio file from the TTS output
//            String utteranceId = "tts_utterance_" + System.currentTimeMillis();
//            File cacheDir = getCacheDir();
//            File wavFile = new File(cacheDir, utteranceId + ".wav");
//            int synthesizeResult = tts.synthesizeToFile(text, null, wavFile, utteranceId);
//            if (synthesizeResult == TextToSpeech.ERROR) {
//                Log.e("Page 3", "Failed to synthesize TTS to file");
//                return;
//            }
//
//            // Load the audio file to the MediaPlayer instance
//            mediaPlayer.setDataSource(wavFile.getPath());
//            mediaPlayer.prepare();
//
//            // Start playback
//            mediaPlayer.start();
//        } catch (IOException e) {
//            Log.e("Page 3", "Failed to play TTS", e);
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