package mmt.core;

/**
 * The class that creates reviews from users.
 * Implements the interface IRating.
 */
public class Rating implements IRating {
    private int rating;
    private String comment;

    /**
     * Generates a Rating object with only the rating given as a parameter.
     * Automaticly sets comment to be the empty string.
     *
     * @param rating Rating to set
     */
    public Rating(int rating) {
        super();
        setRating(rating);
        this.comment = "";
    }

    /**
     * Generates a Rating object using rating and comment as parameters.
     *
     * @param rating the rating to be given to the movie
     * @param comment the comment to be given to the movie
     */
    public Rating(int rating, String comment) {
        this(rating);
        setComment(comment);
    }

    @Override
    public void setRating(int rating) throws IllegalArgumentException {
        if (!isValidRating(rating)) {
            throw new IllegalArgumentException("Your input rating is not valid.");
        }
        this.rating = rating;
    }

    @Override
    public int getRating() {
        return this.rating;
    }

    @Override
    public void setComment(String comment) throws IllegalArgumentException {
        if (!isValidComment(comment)) {
            throw new IllegalArgumentException("Your input comment cannot be empty.");
        }
        this.comment = comment;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    /**
     * Used to check whether a rating is valid or not. A rating is valid if it is between 1 and 10.
     *
     * @param rating the movie rating that is to be validated.
     * @return true if the rating is valid, false if the rating is not valid.
     */
    private boolean isValidRating(int rating) {
        return (rating >= 1) && (rating <= 10);
    }

    /**
     * Used to check wheater a comment is valid or not. A comment is valid if the argument is not null.
     *
     * @param comment the movie comment that is to be validated
     * @return true if the comment is valid, false if the comment is not valid.
     */
    private boolean isValidComment(String comment) {
        return comment != null;
    }

    @Override
    public String toString() {
        return "Rating [comment=" + comment + ", rating=" + rating + "]";
    }
}
