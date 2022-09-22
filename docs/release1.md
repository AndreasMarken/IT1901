# Release 1:

In release 1, we have created an easy app. The focus has been on structuring the project, add support for Maven and Gitpod and saving to JSON-file. 

## Core
In the [core](../mmt/src/main/java/mmt/core) folder we created these interfaces
- [IMovie.java](../mmt/src/main/java/mmt/core/IMovie.java)
- [IRating.java](../mmt/src/main/java/mmt/core/IRating.java)

and the associated classes

- [Movie.java](../mmt/src/main/java/mmt/core/Movie.java)
- [Rating.java](../mmt/src/main/java/mmt/core/Rating.java)

We also have a class called [Serie.java](../mmt/src/main/java/mmt/core/Serie.java), but this class is not currently used.

## FXUI
Our fxui currently consists of:
- [MmtApp.java](../mmt/src/main/java/mmt/fxui/MmtApp.java)
- [MmtController.java](../mmt/src/main/java/mmt/fxui/MmtController.java)
- [mmt.fxml](../mmt/src/main/resources/mmt/fxui/mmt.fxml)

The app loads the fxml file, and the logic is specified in the controller.

## JSON
The [JSON folder](../mmt/src/main/java/mmt/json) is used when saving the current state of our app.
The JSON set up is currently by serializing the Movie and Rating objects:
- [MovieSerializer.java](../mmt/src/main/java/mmt/json/MovieSerializer.java)
- [RatingSerializer.java](../mmt/src/main/java/mmt/json/RatingSerializer.java)

The deserializing prosess consists of the following classes:
- [MovieDeserializer.java](../mmt/src/main/java/mmt/json/MovieDeserializer.java)
- [RatingDeserializer.java](../mmt/src/main/java/mmt/json/RatingDeserializer.java)

And all these JSON serializers/deserializers are set up using the [MovieModule.java](../mmt/src/main/java/mmt/json/MovieModule.java) which extends the SimpleModule, and allows for serializing and deserializing of java objects.



