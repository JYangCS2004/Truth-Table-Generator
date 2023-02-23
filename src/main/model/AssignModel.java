package model;

import java.util.ArrayList;
import java.util.List;

public class AssignModel {

    private List<String> symbols;
    private List<Integer> values;

    private int numericValue;

    // EFFECTS: creates a new assignment of truth values
    public AssignModel() {
        symbols = new ArrayList<>();
        values = new ArrayList<>();
        this.numericValue = 0;
    }

    // REQUIRES: symbol must already be in the list of symbols
    // MODIFIES: this
    // EFFECTS: deletes the given symbol for the assignment of truth values
    public void delete(String symbol) {
        values.remove(symbols.indexOf(symbol));
        symbols.remove(symbol);
    }

    // REQUIRES: symbol is not a number, not empty
    // MODIFIES: this
    // EFFECTS: adds a new symbol to the assignment of truth values
    // Initializes the symbol to have a truth value of 0.
    public void add(String symbol) {
        symbols.add(symbol);
        values.add(0);
    }

    // REQUIRES: symbol must already be in the list of symbols
    // EFFECTS: returns the value corresponding to given symbol
    public int getValForSymbol(String symbol) {
        return values.get(symbols.indexOf(symbol));
    }

    // MODIFIES: this
    // EFFECTS: generates the next set of truth values, re-assigns the
    // values of each symbol
    public String nextValues() {
        if (numericValue + 1 <= Math.pow(2, numOfSymbols()) - 1) {
            numericValue++;
        }

        String temp = Integer.toBinaryString(numericValue);
        String binCode = String.format("%" + numOfSymbols()
                + "s", temp).replaceAll(" ", "0");

        int c = 0;
        while (c < values.size()) {
            values.set(c, Integer.parseInt(Character.toString(binCode.charAt(c))));
            c++;
        }

        return binCode;
    }

    // EFFECTS: returns the total number of symbols used in argument
    public int numOfSymbols() {
        return symbols.size();
    }

    // EFFECTS: returns true if the given symbol in the list of symbols
    public boolean isInSet(String symbol) {
        return symbols.contains(symbol);
    }

}