# Module - fxui
This is the module used to genereate a fxml view of the mmt application. Since we only have one ui, we could have changed the name to ui. However, for future possibilities to create different user interfaces, such as a web application, we have kept the name as fxui to show that this is a fxml user interface.

## Resources
Int his module we have the folder resoruces. This folder contains all of the fxml files used in the project. 
- DisplayMovie: a template of the way we want to display a movie. For each movie in the movielist, a new version of this fxml file will be generated and displayed in the main view.
- EditMovie: When you want to create a new movie, or edit a movie, this fxml file will be shown to the user. This file contains input fields that the user can use to create movies
- MyMovieTracker: The main fxml file that is used to display all of the other files to the user. This is the main ui and what we call the mmt app.
- Rating: If you want to give a movie a rating, this display will be shown to the user.
- Statistic: If you want to see statistics for the movies in the movielist this view will be displayed. You can change between MyMovieTracker and Statistic by using the buttons in the display.

## Controllers
All of the fxml files listed above have controllers with the same name followed by controller. We also have an app class that is used as a main class and will open the mmt app to the user.