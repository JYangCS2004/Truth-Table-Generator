package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssignModelTest {
    AssignModel model;

    @BeforeEach
    void setUp() {
        model = new AssignModel();
        model.add("P");
        model.add("Q");
        model.add("R");
    }

    @Test
    void testConstructor() {
        List<String> symbols = new ArrayList<>();
        symbols.add("P");
        symbols.add("Q");
        symbols.add("R");
        assertEquals(symbols, model.getSymbols());
    }

    @Test
    void deleteTwoSymbols() {
        assertTrue(model.isInSet("P"));
        model.delete("P");
        assertFalse(model.isInSet("P"));
        assertEquals(2, model.numOfSymbols());

        assertTrue(model.isInSet("Q"));
        model.delete("Q");
        assertEquals(1, model.numOfSymbols());
        assertFalse(model.isInSet("Q"));
    }

    @Test
    void addTwoSymbols() {
        model.add("S");
        assertEquals(4, model.numOfSymbols());
        assertTrue(model.isInSet("S"));

        model.add("T");
        assertEquals(5, model.numOfSymbols());
        assertTrue(model.isInSet("T"));
    }

    @Test
    void nextValuesTest() {
        assertEquals("001",model.nextValues());
        assertEquals(1, model.getValForSymbol("R"));
    }

    @Test
    void nextValuesMaxValue() {
        for (int i = 0; i < Math.pow(2, model.numOfSymbols()); i++) {
            model.nextValues();
        }

        assertEquals("111", model.nextValues());
    }

    @Test
    void resetModelTest() {
        List<Integer> ints = new ArrayList<>();
        ints.add(0);
        ints.add(0);
        ints.add(0);

        model.reset();
        assertEquals(ints, model.getValues());

        model.nextValues();
        model.reset();
        assertEquals(ints, model.getValues());
    }

}
