[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2238/gr2238/-/tree/master/)
# My Movie Tracker

My movie tracker is a project for the course IT1901. 

The application currently has one main function: 
- Add movies to the database.

## Building and running the project
The project is built using maven, and therefore maven must be used, to run the project.

If you are in the root folder **gr2238**, you will have to change directory to **mmt** before continuing. Perform:

```
cd mmt
```

You should now be in the correct folder.
### Build the project
To build the project, perform:

```
mvn clean install
```
This will clean the maven project, and build it from scratch.

### Running the project
To run the javafx part of the project, perform:

```
mvn javafx:run
```

This should open a new window with the application. From here you can add movies to the database.

### Testing the project
To perform all of the test, you can use:

```
mvn test
```

This will run all of the test, and give you a feedback on how many that succeded.

After you have run the test, there will open a new folder in the target folder. The folders name is site/jacoco. In this folder, there will be an index.html. This can be opened in a web browser, to look at the test coverage.

### Building the project in GitPod
To build the project in gitpod, simply press the button at the top of this file. From here, a code enviroment will open. To run the project, use the same commands stated above.

## Finished product
When the project is finished, the plan is that a user can add search up movies in the database, give them ratings and add them to their watchlist. You will also be able to sort movies, based on ratings and categories. Currently, uploading movies is the only part of the project that is working. 

### Main page
![Image was not shown](images/MainPage.png)
As you can see on the picture above, the user can add a movie to the database by clicking the "+" sign. From there, the user will be guided to a new screen, see the picture below, where you the user will be able to give the movie a name, as well as duration and release date.

![Image was not shown](images/AddMovie.png)
When the "Add movie" button is pressed, the new movie will be added to the main page. Where the user can choose to add more movies to the database, or save them to a file.