package mmt.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Assertions;


public class ActorTest {
    private IActor actor;


    @Test
    public void testConstructorNull() {

        //Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            actor = new Actor(null)
        );
    }

    @Test
    public void testConstructorEmptyString() {
        //Arrange
        String emptyString = "";

        //Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> 
            actor = new Actor(emptyString)
        );
    }

    @ParameterizedTest
    //Arrange
    @ValueSource(strings = {"  ", "\t", "\n"})
    public void testConstructorBlankString(String name) {

        //Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> 
            actor = new Actor(name)
        );
    }

    @ParameterizedTest
    @ValueSource(chars = {'0','1','2','3','4','5','6','7','8','9',
    '.','\'','/',':','*','"','<','>','|',',','~','!','@','#','$','%',
    '^','&','(',')','{','}','_',';',' '})
    public void testConstructorInvalidNames(char name) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> 
            actor = new Actor(name+""),"A valid actor name should not contain: " + name
        );
    }
}
