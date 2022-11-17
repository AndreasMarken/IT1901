package mmt.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ActorTest {

    @Test
    public void testConstructorNull() {
        //Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Actor(null));
    }

    @Test
    public void testConstructorEmptyString() {
        //Arrange
        String emptyString = "";

        //Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Actor(emptyString));
    }

    @ParameterizedTest
    //Arrange
    @ValueSource(strings = { "  ", "\t", "\n" })
    public void testConstructorBlankString(String name) {
        //Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Actor(name));
    }

    @ParameterizedTest
    @ValueSource(
        chars = {
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            '\\',
            '/',
            ':',
            '*',
            '"',
            '<',
            '>',
            '|',
            '~',
            '!',
            '@',
            '#',
            '$',
            '%',
            '^',
            '&',
            '(',
            ')',
            '{',
            '}',
            '_',
            ';'
        }
    )
    public void testConstructorInvalidNames(char name) {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new Actor(name + ""),
            "A valid actor name should not contain: " + name
        );
    }

    @ParameterizedTest
    @ValueSource(
        strings = {
            "Lorenzo de' Medici",
            "Lupita Nyong'o",
            "Niccolò dell'Arca",
            "Philippe-François Fabre d'Eglantine",
            "Paul-Henri Thiry",
            "Martin Luther King, Jr",
            "Vebjørn"
        }
    )
    public void testConstructorValidNames(String name) {
        Assertions.assertDoesNotThrow(() -> new Actor(name), name + " is a valid name!");
    }
}
