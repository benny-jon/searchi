# Searchi: Android Paging Library Demo App

A simple demo app to test out the new Paging Library released by Google as part of the Architectural Components. 
The app consist of a simple List of infinite amount images displayed in 3 columns, which are loaded base on a search query to the Flickr API.

NOTE: Before running the app, please remember to add your Flick API KEY to the file `FlickrApi` inside the method `getFlickApiKey`. To get a Flickr API Key follow the instructions of [this link](https://www.flickr.com/services/apps/create/).
```kotlin
/**
 * Returns the API KEY used for authentication with the Flickr API.
 */
fun getFlickApiKey() : String {
    return "PASTE_YOUR_FLICKR_API_KEY_HERE"
}

```

## Implementation Details

* Written 100% in Kotlin.
* Uses Dagger for dependency injection.
* Uses Retrofit for networking with RxJava and Gson.
* Uses ViewModel.
* Uses LiveData of a PagedList.
* DataFactory and DataSource of type PageKeyedDataSource.

## Architecture

//DIAGRAM COMING SOON

## License

   Copyright 2018 Benny Jon

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   