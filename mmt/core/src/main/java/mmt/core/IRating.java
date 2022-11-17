package mmt.core;

/**
 * Rating interface.
 */
public interface IRating {
    /**
     * Method to set a rating for the specific movie.
     *
     * @param rating The rating to be set in the method.
     * @throws IllegalArgumentException if the rating is not between 1 and 10, an IllegalArgumentException to be thrown.
     */
    public void setRating(int rating) throws IllegalArgumentException;

    /**
     * Method to get the rating linked to this Rating object.
     *
     * @return returns the specific rating from this Rating object.
     */
    public int getRating();

    /**
     * Method to set a comment for the specific movie.
     *
     * @param comment The comment to be set in the method.
     * @throws IllegalArgumentException if the comment is empty, an IllegalArgumentException to be thrown.
     */
    public void setComment(String comment) throws IllegalArgumentException;

    /**
     * Method to get the comment linked to this Rating object.
     *
     * @return returns the specific comment from this Rating object.
     */
    public String getComment();
}
