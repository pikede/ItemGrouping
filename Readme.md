# ItemGrouping
Displays items retrieved from an API grouped by their lisId.

Display all the items grouped by "listId"
Sorts the results first by "listId" then by "name" when displaying.
Filters out any items where "name" is blank or null.

Displays the result in an easy-to-read list, with the Header to list of items.

minSdk API 35

Improvements and TODOs
Cache items, ids, and names and read from cache
Possible modularization

Tools: Dependency Injection Hilt, Kotlin, Coroutines, Jetpack Compose, MVVM, Retrofit,
Repository pattern, Turbine, Mockk.