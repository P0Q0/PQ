* README *

core
: modules, manifest, gradle

module a_0
: TODO: module a_o readme.md

module a_1
: kotlin, activity lifecycle, states, shared preferences, io: memory and disk, lambdas, ui binding, logging

module a_2
: runtime permissions, external storage, internal storage, ui interfacing, sdk aware

module a_3
: google api, synchronous calls, ui listeners, debugging

module a_4
: network data, asynchronous calls, data structures, serialization, lifecycle scope, coroutines, dispatchers,
 tokenization, json, parsers, lazy and val, mutability, bitmaps, extensions, scope functions, mvc design pattern

module a_5
: recycler view strategy: adapter pattern and view holder pattern, material design,

module a_6
: notifications, notify types, grammar: sealed, typealias, object, data, interface, channels, sdk versioning,
 inheritance, encapsulation, abstraction, polymorphism, intents: explicit and implicit, custom api

* Note *
 * The following could be added to "modernize" the application: *
-migrate to abstraction with core support for inheritable data
-migrate to some form of architecture with agreed sdk and api
-migrate to design patterns such as an mvc variations mvvm
-migrate to single activity multiple fragments
-migrate to navigation graph
-migrate to clean
-support dynamic feature deployment to google play
-support backwards compatibility for older sdks
-support dark mode
-support testing
-support ci/cd
-support code quality alerts by lint
-support isolate toolkit for generic api (shared preferences,firebase,permissions,etc.)

* Note *
 * Roadmap for new features by module: *
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
-module a0, TODO: readme.md,A0