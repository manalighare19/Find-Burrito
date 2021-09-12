# Find-Burrito
Find-Burrito lets you discover burrito places near you (within 12 miles).

## Screenshots
<p float="left">
  <img src= "https://user-images.githubusercontent.com/43833000/133001564-610f557f-774b-4fb5-8c31-ac33aebf0a24.PNG" width="300" height="567"/>
  <img src= "https://user-images.githubusercontent.com/43833000/133001708-5f59aa95-f6d1-4538-9188-9c470c36f1c1.PNG" width="300" height="567"/>
</p>

<p float="left">
  <img src= "https://user-images.githubusercontent.com/43833000/133002184-7b6cdd72-725a-49e5-b8c9-5ab7d349f82d.PNG" width="300" height="567"/>
  <img src= "https://user-images.githubusercontent.com/43833000/133002186-9097a61d-f69f-42ec-84fc-9d1bd24f6457.PNG" width="300" height="567"/>
</p>

## Storing secrets

1. Create **secrets.properties** file in the root folder of the app.
2. Add all API keys to secrets.properties file like this -

  ```
  YELP_API_KEY = "Secret API key for YELP Api" 
	GOOGLE_MAPS_KEY = "Secret Google maps key"
  ```
  Android studio creates these file in different folder.

3. Make sure to clean and rebuild the code.

## Credits
- [Yelp GraphQL API](https://www.yelp.com/developers/graphql/guides/intro) - Fetch restaurants to display in a list
- [Apollo Android](https://www.apollographql.com/docs/android/tutorial/00-introduction/) - Communicate with GraphQl queries
- [Navigation jetpack library](https://developer.android.com/guide/navigation/navigation-getting-started) - Manage navigation flow of the application
