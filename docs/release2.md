# Release 2
 For this release we have followed the "arbeidskrav" thorough and worked with every single checkmark to make Release 2 as good as possible. 

 
 ## Modularization and architecture

In release one there was only one module in our project. It consisted of a [JSON](../mmt/core/src/main/java/mmt/json) folder a [core](../mmt/core) folder and a [fxui](../mmt/fxui) folder. For this release we divided it into two modules. One containg the fxui folder and one containing the json and core folders. These folder do have some different dependencies and different functionalities. Therefore, we found it important to devide the folder into two modules.

Normal practice is to divide different functional components in different modules. We could have divided it further on so that we had three modules, JSON, fxui and core. The JSON folder and the core folder share a lot of the same dependencies. So it was decided after a small discussion that the JSON and the core folder not should be split. 
 
  

 ## Quality code
 As a consequence of new features in the project we had to write new and improved tests. The [core tests](../mmt/core/src/test/java/mmt/core) are new and improved, so are the [JSON tests](../mmt/core/src/test/java/mmt/json).
 We have also implemented a [new test](../mmt/fxui/src/test/java/mmt/fxui) wich tests our application when running.

 As said in the [README](/README.md) file we strive to achive 80% code coverage on all modules from jacoco reports. And on this release we have achieved our goal. 

 We have also added spotbugs and checkstyle to our project. These are tools to check that our code upholds a certain coding standard. Spotbugs detects bugs in our code, while checkstyle checks our code up against a given set of rules for whitespace, variabl names, etc.

 ## Documentation
As the project gets bigger and more features are added it is important to keep updating the documentation so that it is alway up to date. Readme files, both [README on root level](/README.md) and [README in mmt folder](mmt/README.md) are updated. The README in the mmt folder contains a new PlantUML diagram of the projects architecture.

Both a class diagram, diagram of the architecture and a sequence diagram is added in the [diagrams](mmt/diagrams) folder.

 ## Working flow
In release 2 we have been a lot better at using issues and branches when necesarry. We divide all our TODO´s into issues, and then assignes the issue to one of the members. We have also started to assign commits and merges to the issue they are attached. 


 ## New classes/functionalities
### Core:
- [Comparators](../mmt/core/src/main/java/mmt/core/Comparators.java)
- [MovieList](../mmt/core/src/main/java/mmt/core/Comparators.java)

### Fxui:
- [DisplayMOvieController](../mmt/fxui/src/main/java/mmt/fxui/DisplayMovieController.java)

- [EditMovieController](../mmt/fxui/src/main/java/mmt/fxui/EditMovieController.java)

- [MyMovieTrackerController](../mmt/fxui/src/main/java/mmt/fxui/MyMovieTrackerController.java)

- [MyMovieTrackerControllerTest](../mmt/fxui/src/test/java/mmt/fxui/MyMovieTrackerControllerTest.java)


### JSON:

- [MovieListSerializer](../mmt/core/src/main/java/mmt/json/MovieListSerializer.java)

- [MovieListDeserializer](../mmt/core/src/main/java/mmt/json/MovieListDeserializer.java)

