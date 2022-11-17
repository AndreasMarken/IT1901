package mmt.fxui;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
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

    private ArrayList<IActor> temporaryActors = new ArrayList<>();

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
            if (title.equals("")) {
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
            Date releaseDate;

            try {
                releaseDate =
                    Date.valueOf(
                        date.getValue().getYear() +
                        "-" +
                        date.getValue().getMonthValue() +
                        "-" +
                        date.getValue().getDayOfMonth()
                    );
            } catch (Exception e) {
                errorMessage.setText("You must choose a valid date.");
                return;
            }

            boolean watchList = watchListCheckBox.isSelected();

            try {
                if (this.movie == null) {
                    IMovie movie = new Movie(title, time, releaseDate);
                    movie.setOnTakeOfWatchlist(watchList);
                    for (IActor actor : temporaryActors) {
                        movie.addActor(actor);
                    }
                    myMovieTrackerController.addMovie(movie);

                    myMovieTrackerController.dataAccess.addMovie((Movie) movie);
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
        if (movie.getCast() != null) {
            for (IActor actor : movie.getCast()) {
                movie.removeActor(actor);
            }
        }
        for (IActor actor : temporaryActors) {
            movie.addActor(actor);
        }

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
        temporaryActors.clear();
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
        if (movie != null) {
            newEditMovieTab.setText("Edit movie");
            if (movie.getCast() != null) {
                temporaryActors = new ArrayList<>(movie.getCast());
            }
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
        hours.increment(Integer.parseInt(movie.getDuration().toString().substring(0, 2)));
        minutes.increment(Integer.parseInt(movie.getDuration().toString().substring(3, 5)));
        date.setValue(movie.getReleaseDate().toLocalDate());
        watchListCheckBox.setSelected(movie.getWatchlist());
        updateActorsListView();
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
        if (actorNameField.getText().isEmpty()) {
            errorMessage.setText("You must write a name for the actor you want to add.");
        }

        for (IActor actor : temporaryActors) {
            if (actorNameField.getText().equals(actor.getName())) {
                errorMessage.setText("The actor is already added to the movie");
                return;
            }
        }

        Actor actor = new Actor(actorNameField.getText());

        temporaryActors.add(actor);

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
                    String nameOfActorToBeRemoved = getItem();
                    getListView().getItems().remove(nameOfActorToBeRemoved);
                    Actor actorToBeRemoved = null;
                    for (IActor actor : temporaryActors) {
                        if (actor.getName().equals(nameOfActorToBeRemoved)) {
                            actorToBeRemoved = (Actor) actor;
                        }
                    }
                    temporaryActors.remove(actorToBeRemoved);
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

        for (IActor actor : temporaryActors) {
            observableActorList.add(actor.getName());
        }

        actorListView.setItems(observableActorList);
        actorListView.setCellFactory(x -> new ActorListViewCell());
        actorNameField.clear();
    }
}
