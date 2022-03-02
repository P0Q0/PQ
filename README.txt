* README *
Note this project is following a specific requirement that might not require the "latest" practices,
but as a further improvement, the following could be added to "modernize" the application:
-migrate to abstraction with core support for inheritable data
-migrate to some form of architecture with agreed sdk and api
-migrate to design patterns such as an mvc variations mvvm
-migrate to single activity multiple fragments
-migrate to navigation graph
-migrate to not clean
-support dynamic feature deployment to google play
-support backwards compatibility for older sdks
-support dark mode
-support testing
-support ci/cd
-support code quality alerts by lint
-support isolate toolkit for generic api (shared preferences,firebase,permissions,etc.)
-module a1, fab button to clear shared preferences, fab button to invoke dialog and see ui further, foreground background
-module a2, observers to reflect available permissions, improve permissions workflow with dialogs, add external storage uiIF,
add clear permissions ui reset individual permissions, add clear all permissions ui to reset all permissions,
polish file i/o, add additional ui for setting each permission individually, support multiple storage from different vendors,
migrate to scoped storage, migrate to dynamic on single use permissions this would clear on each app destroyed event
-module a3, FIX google sign-out button functionality consider revoke access as well, ui to display status
-module a4, TODO: readme.md,A4
-module a5, further extension to adapter with abstraction to reduce boilerplate for future adapters
-module a6, TODO: readme.md,A6
-module a7, TODO: readme.md,A7
-module a0, TODO: readme.md,A0