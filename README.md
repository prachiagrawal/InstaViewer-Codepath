InstaViewer
============

An app which streams popular photos on Instagram at any given time

Time spent: In total, ~12 hours. Most time was spent on getting the image view tto my liking.

Completed user stories:

 * [x] User can scroll through current popular photos from Instagram
 * [x] For each photo displayed, user can see the following details: Graphic, Caption, Username
 * [x] Relative timestamp of when the photo was posted, likes count, user profile image
 * [x] Pull-to-refresh for popular stream with SwipeRefreshLayout
 * [x] Heart, comment, and location bubbles and information
 * [x] Show latest two comments for each photo and also show the count of total comments just like on Instagram
 * [x] Display each photo with the same style and proportions as the real Instagram

Walkthrough of all user stories:

![Video Walkthrough](InstaViewer.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

Used Instagram's APIs to get the data, Picasso (http://square.github.io/picasso/) to download and display the images and Android Async Http Client for all Async calls to the Instagram APIs (http://loopj.com/android-async-http/).


