package mmt.core;

import java.util.Comparator;

/**
 * Class for sorting IMovie objects.
 */
public class Comparators {

    /**
     * Sorts by highest rating.
     *
     * @return A comparator that sorts by rating. Highest rating comes first.
     */
    public static Comparator<IMovie> sortByHighestRating() {
        return Comparator.comparing(IMovie::getRatingNumber).reversed();
    }

    /**
     * Sorts by title (alfabetical).
     *
     * @return A comparator that sorts by title.
     */
    public static Comparator<IMovie> sortByTitle() {
        return Comparator.comparing(IMovie::getTitle);
    }

    /**
     * Sorts by duration.
     *
     * @return A comparator that sorts by duration. Shortes first.
     */
    public static Comparator<IMovie> sortByDuration() {
        return Comparator.comparing(IMovie::getDuration);
    }
}
