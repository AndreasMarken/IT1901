package mmt.fxui;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import mmt.core.Actor;
import mmt.core.IActor;
import mmt.core.IMovie;
import mmt.core.Movie;

/**
 * Controller that is used to edit or add a new movie to the MyMovieTracker app.
 * The controller is initalised when the app is opened, but the view is hidden
 * until th user has chosen to edit or add a movie to the movielist.
 */
public class EditMovieController {
    @FXML
    private MyMovieTrackerController myMovieTrackerController;

    @FXML
    private TextField movieTitleField;

    @FXML
    private TextField actorNameField;

    @FXML
    private Spinner<Integer> hours;

    @FXML
    private Spinner<Integer> minutes;

    @FXML
    private DatePicker date;

    @FXML
    private CheckBox watchListCheckBox;

    @FXML
    private Label newEditMovieTab;

    @FXML
    protected Label errorMessage;

    @FXML
    protected ListView<String> actorListView;

    private IMovie movie;

    private Collection<IActor> actors = new ArrayList<>();

    /**
     * The method that is called when the user has completed the editing/adding
     * of a movie and clicks the save button. Saves the updated movie or adds the movie to the movielist, and
     * updates the movielistview.
     * If invalid information is given by the user, an errormessage is shown in the view.
     */
    @FXML
    private void edit() {
        if (isValidTitleName(movieTitleField.getText())) {
            String title = movieTitleField.getText();
            if (title.equals("") || title.equals(null)) {
                errorMessage.setText("The title name is not valid.");
                return;
            }
            //Time time = new Time(hours.getValue(), minutes.getValue(), 0);
            Time time = Time.valueOf(hours.getValue() + ":" + minutes.getValue() + ":00");
            //if (time.getHours() <= 00 && time.getMinutes() <= 00) {
            if (hours.getValue() <= 00 && minutes.getValue() <= 00) {
                errorMessage.setText("The movie must have a duration.");
                return;
            }
            try {
                date.getValue().getYear();
                date.getValue().getMonth();
                date.getValue().getDayOfMonth();
            } catch (NullPointerException e) {
                errorMessage.setText("You must choose a valid date.");
                return;
            }
            //Date releaseDate = new Date(date.getValue().getYear() - 1900, date.getValue().getMonthValue() - 1, date.getValue().getDayOfMonth());
            Date releaseDate = Date.valueOf(
                date.getValue().getYear() +
                "-" +
                date.getValue().getMonthValue() +
                "-" +
                date.getValue().getDayOfMonth()
            );
            boolean watchList = watchListCheckBox.isSelected();

            try {
                if (this.movie == null) {
                    IMovie movie = new Movie(title, time, releaseDate);
                    movie.setOnTakeOfWatchlist(watchList);
                    myMovieTrackerController.addMovie(movie);
                    for (IActor actor : actors) {
                        movie.addActor(actor);
                    }
                    if (movie instanceof Movie) {
                        myMovieTrackerController.dataAccess.addMovie((Movie) movie);
                    }
                } else {
                    editExistingMovie(title, time, releaseDate, watchList);
                    this.movie = null;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            clearInputFields();
            myMovieTrackerController.updateMovieListView();
            myMovieTrackerController.hideEditMovie(false);
        } else {
            errorMessage.setText("The title name is already used");
        }
    }

    /**
     * Updates the existing movie, that the user has chosen, with the input-information
     * in the app.
     *
     * @param title : The new title to be given
     * @param duration : The new duration to be given
     * @param releaseDate : The new releasedate to be given
     * @param watchList : The new watchlist-status to be given
     */
    private void editExistingMovie(String title, Time duration, Date releaseDate, boolean watchList) {
        String oldMovieID = movie.getID();
        movie.setTitle(title);
        movie.setDuration(duration);
        movie.setReleaseDate(releaseDate);
        movie.setOnTakeOfWatchlist(watchList);
        if (movie instanceof Movie) {
            myMovieTrackerController.dataAccess.updateMovie((Movie) movie, oldMovieID);
        }
    }

    /**
     * Cancels the editing/adding of a movie, and hides the view.
     * Clears the inputfields and makes it ready to edit/add a new movie.
     */
    @FXML
    private void cancelEditMovie() {
        this.movie = null;
        clearInputFields();
        myMovieTrackerController.hideEditMovie(false);
    }

    /**
     * Clears the inputfields in the app.
     */
    private void clearInputFields() {
        movieTitleField.clear();
        hours.decrement(hours.getValue() % 24);
        minutes.decrement(minutes.getValue() % 60);
        date.setValue(null);
        watchListCheckBox.setSelected(false);
        actorListView.getItems().clear();
        actors.clear();
    }

    /**
     * The myMovieTrackerController this app is linked, and communicates with.
     *
     * @param myMovieTrackerController the myMovieTrackerController to be set.
     */
    public void setMyMovieTrackerController(MyMovieTrackerController myMovieTrackerController) {
        this.myMovieTrackerController = myMovieTrackerController;
    }

    /**
     * Initializeses the adding/editing of a movie.
     *
     * @param movie If null is given, a new movie is to be created. Else, a movie will be started to edit.
     */
    protected void editMovie(IMovie movie) {
        errorMessage.setText("");
        this.movie = movie;
        if (this.movie != null) {
            newEditMovieTab.setText("Edit movie");
            fillFields();
        } else {
            newEditMovieTab.setText("New Movie:");
            clearInputFields();
        }
    }

    /**
     * Fills the inputfields with the information of the movie that is to be edited.
     */
    private void fillFields() {
        if (this.movie == null) {
            throw new IllegalStateException(
                "You should not have the oppertunity to edit a movie when you havent selected a movie to edit."
            );
        }
        movieTitleField.setText(movie.getTitle());
        //hours.increment(movie.getDuration().getHours());
        hours.increment(Integer.parseInt(movie.getDuration().toString().substring(0, 2)));
        //minutes.increment(movie.getDuration().getMinutes());
        minutes.increment(Integer.parseInt(movie.getDuration().toString().substring(3, 5)));
        date.setValue(movie.getReleaseDate().toLocalDate());
        watchListCheckBox.setSelected(movie.getWatchlist());
        if (movie.getCast() != null) {
            updateActorsListView();
        }
    }

    /**
     * Method to check if a moviename is valid or not.
     *
     * @param title The title to check wheter is valid and free.
     * @return true if the new moviename is valid. fasle if it is not.
     */
    private boolean isValidTitleName(String title) {
        IMovie movie = myMovieTrackerController.getMovieList().getMovie(title);
        if (movie == null || movie == this.movie) {
            return true;
        }
        return false;
    }

    /**
     * Method used to add actors to a movie.
     * Gets the text from the input field, and adds it to the movie.
     */
    @FXML
    private void addActorToMovie() {
        try {
            try {
                if (movie.getCast().stream().anyMatch(a -> a.getName().equals(actorNameField.getText()))) {
                    errorMessage.setText("The actor is already added to the movie");
                    return;
                }
            } catch (NullPointerException e) {
                //No actors? No problem, add this one
            }

            Actor actor = new Actor(actorNameField.getText());

            if (movie != null) {
                try {
                    movie.addActor(actor);
                } catch (IllegalStateException e) {
                    errorMessage.setText("The actor is already added to the movie");
                }
                //TODO trengs denne
                if (!movie.getCast().contains(actor)) {
                    //Do nothing
                }
            } else {
                if (actors.stream().anyMatch(a -> a.getName().equals(actorNameField.getText()))) {
                    errorMessage.setText("The actor is already added to the movie");
                    return;
                }
                actors.add(actor);
            }
            actorListView.getItems().add(actorNameField.getText());
        } catch (IllegalArgumentException e) {
            errorMessage.setText("You must write a name for the actor you want to add.");
        }
        updateActorsListView();
    }

    /**
     * Modify cells in the cast-listview to match the specifications for our app.
     */
    private class ActorListViewCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("X");

        public ActorListViewCell() {
            super();
            button.setStyle("-fx-background-color: white; -fx-border-color: red; -fx-border-radius: 5;");
            hbox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(
                event -> {
                    String actorToBeRemoved = getItem();
                    getListView().getItems().remove(actorToBeRemoved);
                    if (movie == null) {
                        IActor actorObjToBeRemoved = null;
                        for (IActor actor : actors) {
                            if (actor.getName().equals(actorToBeRemoved)) {
                                actorObjToBeRemoved = actor;
                            }
                        }
                        actors.remove(actorObjToBeRemoved);
                    } else {
                        IActor actorObjToBeRemoved = movie
                            .getCast()
                            .stream()
                            .filter(actor -> actor.getName().equals(actorToBeRemoved))
                            .findAny()
                            .orElse(null);
                        movie.removeActor(actorObjToBeRemoved);
                    }
                }
            );
            button.setId("removeActorFromMovie");
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                label.setText(item);
                setGraphic(hbox);
            }
        }
    }

    /**
     * Every time an actor is added to the Movie, the actor list view gets updated.
     */
    private void updateActorsListView() {
        ObservableList<String> observableActorList = FXCollections.observableArrayList();
        try {
            if (movie != null) {
                Collection<IActor> actors = movie.getCast();

                for (IActor actor : actors) {
                    if (!observableActorList.contains(actor.getName())) {
                        observableActorList.add(actor.getName());
                    }
                }
            } else {
                for (IActor actor : this.actors) {
                    if (!observableActorList.contains(actor.getName())) {
                        observableActorList.add(actor.getName());
                    }
                }
            }
        } catch (NullPointerException e) {
            //No actors in this movie
        }
        actorListView.setItems(observableActorList);
        actorListView.setCellFactory(x -> new ActorListViewCell());
        actorNameField.clear();
    }
}
