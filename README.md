# android-writer-notes
The app for writes to keep notes for books and articles.

## App's features:
* create new note (note name, note text, optional tags)
* manage notes (look through notes list, edit note, delete notes)
* manage notes tags (create tag, look through tags list, edit tag, delete tag)
* filter notes by tags (feature is not released yet)
* keep data at local storage

## App's layers:
* domain layer describes domain data entities such as Note, Tag.
* UI layer describe screens (Fragments, ViewModels), custom views, DTO for ui layer, UI utils.
* data layer describes data types for local storage, local storage, data mappers, repositories
* app layer describes DI and other app stuff.

## App uses:
* Single activity approach, Fragments, Jetpack's Navigation
* ViewModel, LiveData
* Room
* RecyclerView
* Hilt

## App screens:

### List of notes
![Screenshot_1636452047](https://user-images.githubusercontent.com/326673/140904950-e029b394-7fad-4c04-b58b-d501ab2b944c.png)

### Delete notes
![Screenshot_1636452061](https://user-images.githubusercontent.com/326673/140905106-29eb29e1-8407-49c5-abd6-67190317d41d.png)

### Edit note
![Screenshot_1636451888](https://user-images.githubusercontent.com/326673/140905491-b362b364-34ae-4431-b1d9-9963e695c914.png)

### Manage tags
![Screenshot_1636451532](https://user-images.githubusercontent.com/326673/140905596-630cbabf-3721-4995-8da8-7c53fc73b5cf.png)

### Filter notes by tags
![Screenshot_1636452550](https://user-images.githubusercontent.com/326673/140905823-fbdd3f0e-b00a-483f-8244-91b326db774c.png)
