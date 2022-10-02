# Paging V3 Library Demo App in Kotlin (with Compose and AndroidViews)

A simple demo app to test out the new Paging V3 Library released by Google as part of the Architectural Components. 
The app consist of a simple List of infinite images displayed in 3 columns, which are loaded base on a search query, using the public Flickr API.

## Screenshot

<p align="center">
<img src="https://github.com/benny-jon/searchi/blob/master/demo_app_home.png"/>
</p>

## Running Instrutions

Before running the app, please remember to add your Flick API KEY to the file `FlickrApi` inside the method `getFlickApiKey` ([Link to File](https://github.com/benny-jon/searchi/blob/master/app/src/main/java/com/bennyjon/searchi/network/FlickrApi.kt)). To get a Flickr API Key follow the instructions of [this link](https://www.flickr.com/services/apps/create/).
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
* Uses Jetpack Compose for the Compose version
* Uses ViewBinding for the Android Views version
* Uses [Dagger](https://github.com/google/dagger) for dependency injection.
* Uses [Leak Canary](https://square.github.io/leakcanary/) to detect Memory Leaks.
* Uses [Picasso](https://github.com/square/picasso) for image loading and caching.
* Uses [Coil-kt](https://github.com/coil-kt/coil) for image loading and caching in the Compose Activity.
* Uses [Retrofit](https://github.com/square/retrofit) for networking with [Gson Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson).
* Uses Pagination V3 from the Google's Jetpack Components in coordination with Retrofit's Coroutine support.
* Uses `LiveData` of a `PagingData`.
* Uses `PagingSource` and `PagingDataAdapter`.
* Handles screen rotations by caching the PagingData in the `ViewModel` Coroutine Scope and the search query during `onSaveInstanceState`.

## Architecture

<p align="center">
<img src="https://github.com/benny-jon/searchi/blob/master/Searchi_Architecture.jpeg"/>
</p>

## References

* CodeLab: https://developer.android.com/codelabs/android-paging#0
* Paging V3: https://developer.android.com/topic/libraries/architecture/paging/v3-overview
* Migration to Paging V3 Steps: https://developer.android.com/topic/libraries/architecture/paging/v3-migration

## License

     Copyright 2022 Benny Jon

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
