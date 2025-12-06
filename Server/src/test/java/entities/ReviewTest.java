package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ReviewTest {

    private Review review;

    @BeforeEach
    public void setUp() {
        review = new Review();
    }

    @Test
    public void testSetAndGetIDWhenValidIDThenReturnID() {
        int expectedID = 123;
        review.setID(expectedID);
        int actualID = review.getID();
        Assertions.assertEquals(expectedID, actualID);
    }

    @Test
    public void testSetAndGetMovieIDWhenValidMovieIDThenReturnMovieID() {
        int expectedMovieID = 456;
        review.setMovieID(expectedMovieID);
        int actualMovieID = review.getMovieID();
        Assertions.assertEquals(expectedMovieID, actualMovieID);
    }

    @Test
    public void testSetAndGetRatingWhenValidRatingThenReturnRating() {
        int expectedRating = 5;
        review.setStars(expectedRating);
        int actualRating = review.getStars();
        Assertions.assertEquals(expectedRating, actualRating);
    }

    @Test
    public void testSetAndGetCommentWhenValidCommentThenReturnComment() {
        String expectedComment = "Great movie!";
        review.setText(expectedComment);
        String actualComment = review.getText();
        Assertions.assertEquals(expectedComment, actualComment);
    }
}