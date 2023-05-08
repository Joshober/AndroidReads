#  Android Reads
### How it Works-
Android reads is an Android application that web scrapes news sites and social media sites to convert their stories into audio the user can listen to.
For most news sites the application goes out and collects a list of URLs from their homepage. The application then uses that list of URLs to scrape each page for a story and title. Finally, the application creates a button that the user can tap on that will read the story to the user using whatever voice engine is installed on their device. Each social media site has its layout, but currently, for Reddit, the application goes to the subreddit and collects all the titles, comment counts, and stories on the page.
Once the application has the stories it provides two buttons that allow the user to save the article to a MySQL database where they can view and listen to their stories even after the application has been restarted
## Future Improvements:
-Using a Database to preload all the stories- By creating a database that collects and stores all the current stories we can remove much of the lag created by the application having to visit multiple sites to collect their stories. The app would simply need to access the online database to collect all the current stories.  
  
-Creating Support for More Social Media Sites- Currently, the only Social Media site supported is Reddit  
  
-Cleaning the Filter- The filter for all stories needs to be reinforced to allow for cleaner audio playback  
  
# Page Preview:
[AppScreens (2).webm](https://user-images.githubusercontent.com/110935796/236846366-4d0b3166-4807-496f-b4fb-0757cc9fb75b.webm)
# App Demo
https://github.com/Joshober/AndroidReads/blob/6a2b624d74c8bacd3ad7c61e897631fb56d66bb5/Demo.webm  
Due to githubs limmited file size the app demo requires you to download the video 
