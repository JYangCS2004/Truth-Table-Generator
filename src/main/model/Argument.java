package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


// abstract representation of a Logical Argument
public class Argument {
    private List<LogicExp> premises;
    private LogicExp conclusion;
    private AssignModel model;

    public Argument() {
        premises = new ArrayList<>();
        model = new AssignModel();
    }

    // REQUIRES: exp must be already in the list of arguments
    // MODIFIES: this
    // EFFECTS: deletes an expression from the argument, re-adjusts
    //          the counter
    public void deleteExp(int num) {
        EventLog.getInstance().logEvent(new Event("A premise was removed from the argument."));
        LogicExp exp = premises.get(num - 1);
        premises.remove(num - 1);
        for (String s : exp.getSymbolsUsed()) {
            if (isSymbolNotInOtherExps(s)) {
                model.delete(s);
            }
        }
    }

    // REQUIRES: exp is a valid expression
    // MODIFIES: this
    // EFFECTS: adds a new expression to the argument, re-adjusts
    //          the counter
    public void addExp(LogicExp exp) {
        EventLog.getInstance().logEvent(new Event("A new premise was added."));
        premises.add(exp);

        for (String symbol : exp.getSymbolsUsed()) {
            if (!model.isInSet(symbol)) {
                model.add(symbol);
            }
        }
    }

    // EFFECTS: computes each expression for the current truth values,
    //          and returns the values as a list of integers
    public List<Integer> computeEach() {
        List<Integer> results = new ArrayList<>();
        for (LogicExp e : premises) {
            results.add(e.evaluate(model));
        }

        results.add(conclusion.evaluate(model));
        return results;
    }


    // REQUIRES: exp is a valid expression
    // MODIFIES: this
    // EFFECTS: sets the expression for the conclusion,
    //          adds new symbols to model
    public void setConclusion(LogicExp exp) {
        EventLog.getInstance().logEvent(new Event("Conclusion was changed to " + exp.getExpString() + "."));
        this.conclusion = exp;

        for (String symbol : exp.getSymbolsUsed()) {
            if (!model.isInSet(symbol)) {
                model.add(symbol);
            }
        }
    }


    // EFFECTS: returns the conclusion of the argument
    public LogicExp getConclusion() {
        return conclusion;
    }


    // EFFECTS: returns the model (set of truth values)
    public AssignModel getModel() {
        return model;
    }

    // EFFECTS: returns the premises as a list
    public List<LogicExp> getExps() {
        return premises;
    }

    // EFFECTS: returns true if symbol is not in the other expressions
    private boolean isSymbolNotInOtherExps(String symbol) {
        boolean result = true;
        for (LogicExp e : premises) {
            if (e.getSymbolsUsed().contains(symbol)) {
                result = false;
                break;
            }
        }

        if (conclusion != null) {
            if (result) {
                result = !conclusion.getSymbolsUsed().contains(symbol);
            }
        }

        return result;
    }


    // EFFECTS: returns the set of truth values that make the argument invalid
    public AssignModel returnInvalidModel() {
        model.reset();
        int numOfRows = (int) Math.pow(2, model.numOfSymbols());

        for (int j = 0; j < numOfRows; j++) {
            int first = computeEach().get(0);
            boolean allSame = true;
            for (int i = 0; i < computeEach().size() - 1; i++) {
                if (computeEach().get(i) != first) {
                    allSame = false;
                }
            }

            if (allSame) {
                if (first == 1 && conclusion.evaluate(model) == 0) {
                    return model;
                }
            }

            model.nextValues();
        }

        return null;
    }


    // EFFECTS: returns a JSONObject representing an argument
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("premises", convertExpsToJson());

        if (conclusion != null) {
            json.put("conclusion", conclusion.toJson());
        } else {
            json.put("conclusion", new JSONObject());
        }

        return json;
    }


    // EFFECTS: returns a JSONArray representing the list of premises
    private JSONArray convertExpsToJson() {
        JSONArray jsonArr = new JSONArray();
        for (LogicExp e : premises) {
            jsonArr.put(e.toJson());
        }

        return jsonArr;
    }
}
