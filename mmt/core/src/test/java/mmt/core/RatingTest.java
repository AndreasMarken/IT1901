package mmt.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RatingTest {
    private Rating rating1;
    private Rating rating2;

    /**
     * The method that runs before all tests are run.
     */
    @BeforeEach
    public void setUp() {
        this.rating1 = new Rating(6);
        this.rating2 = new Rating(5, "Good movie");
    }

    @Test
    @DisplayName("Testing that the fields in the class is non-accesible without using getters.")
    public void testPrivateFields() {
        PrivateFieldTester.checkPrivateFields(Rating.class);
    }

    @Test
    @DisplayName("Testing that the constructor works correctly.")
    public void testConstructor() {
        Assertions.assertEquals(6, rating1.getRating(), "The rating value does not match");
        Assertions.assertEquals("", rating1.getComment(), "The auto-generated comment does not match what it should.");

        Assertions.assertEquals(5, rating2.getRating(), "The rating value does not match the initalized value");
        Assertions.assertEquals(
            "Good movie",
            rating2.getComment(),
            "The comment does not match the comment that was given to the constructor."
        );

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                new Rating(1, null);
            },
            "You are not allowed to set null as a movie comment."
        );

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                new Rating(-1, "Good");
            },
            "You are not allowed to set a negative rating to a movie"
        );
    }

    @Test
    @DisplayName("Test that setting new ratings to a rating-object works as intended.")
    public void testSetValidRating() {
        rating1.setRating(rating2.getRating());
        Assertions.assertEquals(
            rating2.getRating(),
            rating1.getRating(),
            "The rating do not match the rating that it was given"
        );

        rating1.setRating(1);
        Assertions.assertEquals(1, rating1.getRating(), "The rating do not match the rating that it was given");
    }

    @Test
    @DisplayName("Test that setting new ratings to a rating-object works as intended.")
    public void testSetNonValidRating() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                rating1.setRating(-1);
            },
            "The rating is less than 1"
        );

        Assertions.assertEquals(
            6,
            rating1.getRating(),
            "The rating should not change when an illegal argument is given"
        );

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                rating1.setRating(11);
            },
            "The rating is more than 10"
        );

        Assertions.assertEquals(
            6,
            rating1.getRating(),
            "The rating should not change when an illegal argument is given"
        );
    }

    @Test
    @DisplayName("Test that setting a valid comment is possible.")
    public void testSetValidComment() {
        rating1.setComment("Well, it wasnt that good.");
        Assertions.assertEquals(
            "Well, it wasnt that good.",
            rating1.getComment(),
            "The new comment was not assigned when it should have been."
        );

        rating2.setComment("Very good.");
        Assertions.assertEquals(
            "Very good.",
            rating2.getComment(),
            "It should be possible to assign a value to both Rating objects."
        );

        rating1.setComment("");
        Assertions.assertEquals("", rating1.getComment(), "The empty string is a valid argument, it was not accepted.");
    }

    @Test
    @DisplayName("Test that setting null as the argument in a rating object is not allowed.")
    public void testSetNonValidComment() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                rating2.setComment(null);
            },
            "You are not allowed to set null as a movie comment."
        );

        Assertions.assertEquals("Good movie", rating2.getComment(), "The comment of the rating should not be changed.");
    }

    @Test
    @DisplayName("Test toString()")
    public void testToString() {
        Assertions.assertEquals(
            "Rating [comment=" + rating1.getComment() + ", rating=" + rating1.getRating() + "]",
            rating1.toString()
        );
    }
}
