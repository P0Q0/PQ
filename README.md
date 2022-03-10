
### README
*** this is an Android application called PQ ***

##### *** module a_0 layers ***
```md
data
 -io
  -cache
   -define
   -manager
    -loader
    -accelerator
  -disk
   -local
   -remote
  -network
   -local
   -remote
 -model
  -definition
 -tool
domain
 -usecase
 -config
 -controller
 -core
  -initializer
  -constant
  -di
   -injector
 -pref
 -service
 -settings
ui
 -adapter
 -notification
 -state
 -view
util
```

##### *** module a_0 ***
    : single activity, navigation graph,
    : dependency injection, TODO: mockito, espresso, junit, roboelectric
    : retrofit, moshi, live data, flows, parallelism, concurrency
    : mvvm design pattern, architecture components
    : TODO: work manager, room, and firebase

##### *** module a_1 ***
    : kotlin, activity lifecycle, states, shared preferences, io: memory and disk, lambdas, ui binding, logging

##### *** module a_2 ***
    : runtime permissions, external storage, internal storage, ui interfacing, sdk aware

##### *** module a_3 ***
    : google api, synchronous calls, ui listeners, debugging

##### *** module a_4 ***
    : network data, asynchronous calls, data structures, serialization, lifecycle scope, coroutines, dispatchers,
     tokenization, json, parsers, lazy and val, mutability, bitmaps, extensions, scope functions, mvc design pattern

##### *** module a_5 ***
    : recycler view strategy: adapter pattern and view holder pattern, material design,

##### *** module a_6 ***
    : notifications, notify types, grammar: sealed, typealias, object, data, interface, channels, sdk versioning,
     inheritance, encapsulation, abstraction, polymorphism, intents: explicit and implicit, custom api

##### *** module a_7 ***
    : TODO:  module a_7, cameraX

##### *** module a_8 ***
    : TODO:  module a_8, fragments

##### *** module a_9 ***
    : debugging tools: logcat & adb, for, forensics and analysis: logs, bugs, and crashes

##### *** module_app ***
    :  manifest, gradle, dynamic feature modules, resources: layouts, values, and themes

##### *** Note about modernization ***
###### **** The following could be added to "modernize" the application: ****
    -migrate to abstraction with core support for inheritable data
    -migrate to clean domain use cases
    -support dynamic feature deployment to google play
    -support backwards compatibility for older sdks
    -support dark mode
    -support testing
    -support ci/cd
    -support code quality alerts by lint
    -support memory managers
    -support test cases
    -support state holders for unified data flow
    -support isolated toolkit for generic api

##### *** Note about roadmap ***
###### **** Roadmap for new features by module: ****
    -module a1, fab button to clear shared preferences, fab button to invoke dialog and see ui further, foreground background
    -module a2, observers to reflect available permissions, improve permissions workflow with dialogs, add external storage uiIF,
    add clear permissions ui reset individual permissions, add clear all permissions ui to reset all permissions,
    polish file i/o, add additional ui for setting each permission individually, support multiple storage from different vendors,
    migrate to scoped storage, migrate to dynamic on single use permissions this would clear on each app destroyed event
    -module a3, FIX google sign-out button functionality consider revoke access as well, ui to display status
    -module a4, improve with viewmodel api and livedata which will constrain and keep the network flow tight
    -module a5, further extension to adapter with abstraction to reduce boilerplate for future adapters
    -module a6, support for generic expansion, worthwhile to investigate compile and runtime polymorphic type expectations,
    finish dev on different types of notifications including wearables, support for legacy sdks
    -module a7: TODO: readme.md,A7 cameraX
    -module a8: TODO: readme.md,A8 fragments
    -module a9, dev for each type of vital, grid view for section title and section items
    -module a0, memory managers, settings, themes, state holders for achieveing unified data flow, 
    database data access object definitions with repository pattern
    -module app, recycler with grid displaying items that'll invoke unique features

##### *** Note about libraries for modules a1..a9 ***
    Annotations
    AppCompat
    Constraint
    Coroutines
    GSON
    Material
    Kotlin
    OkHttp
    Picasso
    Google Play
    Recycler
###### **** Libraries that were used for module a0 on top of the libraries from above ^: ****
    Moshi
    Retrofit
    Fragment
    Navigation
    Lifecycle
    ViewModel
    Flows
    LiveData
    Junit
    Espresso
    Roboelectric
    Mockito

##### *** Note about SDK: ***
    'compileSdk'        : 31,
    'minSdk'            : 30,
    'targetSdk'         : 31,

##### *** module a_9 debug supplemental ***

[METADATA](https://github.com/P0Q0/PQ/tree/main/metadata "See Metadata") where data about data exists, usually text files and binaries

[RECORDINGS](https://github.com/P0Q0/PQ/tree/main/media/screenrecordings "See Recordings") where a video walkthrough exists, usually gifs 

##### *** media ***
### module a_9, debug anr using adb, rootcause: deadlock
<p>
<img align="left" src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/pq_p9_anr_thread_deadlock.gif" width="750" height="350">
</p>
