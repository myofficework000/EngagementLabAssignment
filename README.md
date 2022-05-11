# EngagementLabAssignment
This is a sample android application to get movies list🍿using [Disney API]( https://disneyapi.dev/) - which is built to demonstrate use of *Modern Android development* tools. It has been built using kotlin with clean architecture principles and MVVM pattern as well as Architecture Components. 
Dedicated to all Android Developers with ❤️.

## About
The app in itself is a simple movie searrch on the basis Id. Clicking the movie list item it shows the detail of the movie. While this is not an extremely complex app, it isn't a silly Hello World one either, so the hope is that it'll cover regular use cases for a basic application.

## Sailent Features:
- List of movies.
- Search the movies on basis of Id.
- View the list of movies based on the search result.
- On click of movie, detail of movie
- Unit tesing for view models
- UI testing for UI

## 📸 Screenshots

**Please click the image below to enlarge.**

<img src="https://github.com/cheetahmail007/EngagementLabAssignment/blob/master/app/src/Asset/flow_app.gif" height="600" width="300" hspace="40"><img src="https://github.com/cheetahmail007/EngagementLabAssignment/blob/master/app/src/Asset/screen1.png" height="600" width="300" hspace="40">

<img src="https://github.com/cheetahmail007/EngagementLabAssignment/blob/master/app/src/Asset/screen2.png" height="600" width="300" hspace="40"><img src="https://github.com/cheetahmail007/EngagementLabAssignment/blob/master/app/src/Asset/screen3.png" height="600" width="300" hspace="40">

<img src="https://github.com/cheetahmail007/EngagementLabAssignment/blob/master/app/src/Asset/screen4.png" height="600" width="300" hspace="40"><img src="https://github.com/cheetahmail007/EngagementLabAssignment/blob/master/app/src/Asset/testcases.png" height="600" width="300" hspace="40">


## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [GSON](https://github.com/google/gson) - A Java serialization/deserialization library to convert Java Objects into JSON and back.
- [GSON Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) - A Converter which uses Gson for serialization to and from JSON.
- [OkHttp3](https://github.com/square/okhttp) -  For implementing interceptor, logging and mocking web server.
- [PICASSO](https://square.github.io/picasso/) - An image loading and caching library for Android focused on smooth scrolling.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Junit](https://developer.android.com/training/testing/local-tests) -- for unit testing of View model
- [Mockito](https://developer.android.com/training/testing/local-tests)-- for unit testing of view model
- [Espresso](https://developer.android.com/training/testing/espresso) -- for unit testing of UI

# Package Structure
    
    com.example.assignment               # Root Package
    .
    ├── model                            # Model Layer 
    │   ├── remote                            
    |   │   ├── api                      # Retrofit client and Services
    │   ├── responses                    # Data classes 
    │         
    |└── viewmodel
    |      |__ MainViewModel             # For List of movies
    |      |__DetailViewModel            # For Detail of movie
    |
    ├── view                             # Activity/View layer
    │   ├── activities___
    |   |           |____MainActivity    # List Screen View
    |   |           |____DetailActvity   # Detail Screen View
    |   ├── adapter                      # Adapter for RecyclerView
    | 
    |
    └── utils                            # Utility Classes / Kotlin extensions


## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)



### Contact - Let's become friend
- [Github](https://github.com/cheetahmail007)
- [Linkedin](https://www.linkedin.com/in/myofficework/)
- [medium](https://medium.com/@myofficework000)
- [leetcode](https://leetcode.com/myofficework/)

<p>
Don't forget to star ⭐ the repo it motivates me to share more open source
</p>
