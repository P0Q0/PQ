
<div dir=rtl>.80.81.</div>
<p align="right">
<img align="center" src="https://github.com/P0Q0/PQ/blob/main/media/screenshots/pq_logo.png" width="75" height="75" />
</p>

#### ``` *** PQ_README ***```

[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)

### Runtime Demonstration

---

<p>
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/features_singled/pq_p0_what_module_mode.gif" width="175" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/features_singled/pq_p0_what_Gg_signin.gif" width="175" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/features_singled/pq_p0_what_net_io.gif" width="175" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/features_singled/pq_p0_what_recycler_view.gif" width="175" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/features_singled/pq_p0_what_profile_view.gif" width="175" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/features_singled/pq_p0_what_noty_on_stopers.gif" width="175" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/features_singled/pq_p0_what_noty_on_destroyers.gif" width="175" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/features_singled/pq_p0_what_dialogs.gif" width="175" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/features_singled/pq_p0_what_runtime_permissions.gif" width="175" height="200" />
</p>

---

### CameraX 

<p>
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/features_singled/pq_p0_what_camerax_photos.gif" width="175" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/features_singled/pq_p0_what_camerax_video.gif" width="175" height="200" />
</p>

---

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
 -messages
 -notification
 -state
 -view
util
```

---

##### *** module a_0 ***
    : single activity, navigation graph, lifecycleStates, backStack, dialogs
    : dependency injection, TODO: mockito, espresso, junit, roboelectric
    : retrofit, moshi, live data, flows, parallelism, concurrency
    : clean layers, mvvm design pattern
    : work manager, service, buses for broadcasting
    : TODO: room, gcp: firebase db and notifications
    : TODO: promises api: async, await, futures, deferrables

---

##### *** module a_1 ***
    : kotlin, activity lifecycle, states, shared preferences, io: memory and disk, lambdas, ui binding, logging

---

##### *** module a_2 ***
    : runtime permissions, external storage, internal storage, ui interfacing, sdk aware

---

##### *** module a_3 ***
    : google api, synchronous calls, ui listeners, debugging

---

##### *** module a_4 ***
    : network data, asynchronous calls, data structures, serialization, lifecycle scope, coroutines, dispatchers,
     tokenization, json, parsers, lazy and val, mutability, bitmaps, extensions, scope functions, mvc design pattern

---

##### *** module a_5 ***
    : recycler view strategy: adapter pattern and view holder pattern, material design,

---

##### *** module a_6 ***
    : notifications, notify types, grammar: sealed, typealias, object, data, interface, channels, sdk versioning,
     inheritance, encapsulation, abstraction, polymorphism, intents: explicit and implicit, custom api

---

##### *** module a_7 ***
    : TODO:  module a_7, cameraX

---

##### *** module a_8 ***
    : TODO:  module a_8, fragments

---

##### *** module a_9 ***
    : debugging tools: logcat & adb, for, forensics and analysis: logs, bugs, and crashes

---

##### *** module_app ***
    :  manifest, gradle, dynamic feature modules, resources: layouts, values, and themes

---

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

---

##### *** Note about roadmap ***
###### **** Roadmap for new features by module: ****
    -module a1, fab button to clear shared preferences, fab button to invoke dialog and see ui further, foreground background
    -module a2, observers to reflect available permissions, improve permissions workflow with dialogs, add external storage uiIF,
    add clear permissions ui reset individual permissions, add clear all permissions ui to reset all permissions,
    polish file i/o, add additional ui for setting each permission individually, support multiple storage from different vendors,
    migrate to scoped storage, migrate to dynamic on single use permissions this would clear on each app destroyed event
    -module a3, [FIX IN A3] but [FIXED IN A0] google sign-out button functionality consider revoke access as well, ui to display status
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

---

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
    WorkManager
    LeakCanary

---

##### *** Note about SDK: ***
    'compileSdk'        : 31,
    'minSdk'            : 30,
    'targetSdk'         : 31,

---

##### *** module a_9 debug supplemental ***

[METADATA](https://github.com/P0Q0/PQ/tree/main/metadata "See Metadata") where data about data exists, usually text files and binaries

[RECORDINGS](https://github.com/P0Q0/PQ/tree/main/media/screenrecordings "See Recordings") where a video walkthrough exists, usually gifs 

---


##### *** media ***


---


### module a_9, debug anr using adb, rootcause: deadlock
<p>
<img align="center" src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/pq_p9_anr_thread_deadlock.gif" width="750" height="350">
</p>


---


### module a_9, AppInspection, scenario: Garbage Collector and Cpu are hunting, Threads are racing to finish before they get hunted
<p>
<img align="center" src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/analysis/pq_p0_appinspection_net_io.gif" width="750" height="350">
</p>


---


### module a_9, Profiler, scenario: Runtime Analysis, Part#1
<p>
<img align="center" src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/analysis/pq_p0_profiler_memory_cpu_part1.gif" width="750" height="350">
</p>


---


### module a_9, Profiler, scenario: Runtime Analysis, Part#2
<p>
<img align="center" src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/analysis/pq_p0_profiler_memory_cpu_part2.gif" width="750" height="350">
</p>



---


<p>
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/modules_singled/pq_a1.gif" width="125" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/modules_singled/pq_a2.gif" width="125" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/modules_singled/pq_a3.gif" width="125" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/modules_singled/pq_a4.gif" width="125" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/modules_singled/pq_a5.gif" width="125" height="200" />
<img src="https://github.com/P0Q0/PQ/blob/main/media/screenrecordings/modules_singled/pq_a6.gif" width="125" height="200" />
</p>


---


### module a_0, application invoker, ui notifications, data flow invoke
<p>
<img align="center" src="https://github.com/P0Q0/PQ/blob/main/media/diagrams/android_ui-notifications_app-invoker.png" width="750" height="350">
</p>


---


### module a_0, application desist, ui notifications, data flow desist
<p>
<img align="center" src="https://github.com/P0Q0/PQ/blob/main/media/diagrams/android_ui-notifications_app-desist.png" width="750" height="350">
</p>


---


##### *** dependencies ***
    1) place file 'credentials.json' at the root, i.e. , ...\PQ\credentials.jason
    2) add to 'local.properties': example: 
        `# local defines stashing google api credentials, omit { and } on the string
        GOOGLE_SERVICES_API_CLIENT_ID = "{token-A}.apps.googleusercontent.com"
        GOOGLE_SERVICES_API_CLIENT_SECRET = "{token-B}"`


---

## Liability 
Please use this project for learning, demonstration purposes, and evalutaion of my skillset.

## License
```xml
The [NAME_OF_LICENSE] License

Copyright (c) 2022 P0Q0 @ https://github.com/P0Q0

[DESCRIPTION_OF_LICENSE]
```

---


###### Point Of Contact: Jose Pablo Rosas
