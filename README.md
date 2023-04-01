# Yay or Nay App

🐶🐱 A fun app to vote for your favorite furry friends. 🐶🐱

## Description

Yay or Nay App is an Android app that uses product flavors to provide two different versions: one for cats and one for dogs.</br>
 With each vote, a new picture is loaded from two free APIs: </br>🐱 https://api.thecatapi.com/v1/images/search for cats and </br>🐶 https://dog.ceo/api/breeds/image/random for dogs. </br>
You can vote by clicking the 👍 thumbs up icon (like) or 👎 thumbs down icon (dislike). 

### Main Page
The Main page displays one picture at a time.</br>
You can click the thumbs up icon to like the picture or the thumbs down icon to dislike it.</br>
The app will automatically load a new picture for you to vote on.</br>
Clicking the "Liked" button will take you to the Liked page.

![Main Page](insert_image_url_here)

### Liked Page
The Liked page displays all the pictures that you have liked.</br>
You can click on each picture to copy its image URI. </br>
The page also has a delete function to remove individual pictures, a "Delete All" button to remove all liked pictures, and an "Export All" button to export all the URIs to a file.

![Liked Page](insert_image_url_here)

## SharedPrefManager Library

The app uses a custom library called SharedPrefManager that provides the following capabilities:

1. Singleton pattern for efficient use of shared preferences.
2. Save URI list to shared preferences.
3. Remove all URIs from shared preferences.
4. Get one URI from the URI list.
5. Remove one URI from the URI list.
6. General shared preferences capabilities.

## How to Use

1. Download and install the app on your Android device.
2. Launch the app and start voting on random pictures of cats and dogs.
3. Your vote will be saved and new picture will be loaded.
4. You can also export all the links by clicking the "Export Links" button in the app.

## Credits

The app uses the following free APIs:
- 🐱 https://api.thecatapi.com/v1/images/search for cats
- 🐶 https://dog.ceo/api/breeds/image/random for dogs

