package model;


// An Operator object
public class Operator {

    char operator;
    int rank;

    // REQUIRES: op must be a legal operator
    // EFFECTS: constructs a new Operator object
    public Operator(char op) {
        this.operator = op;
        if (op == '~') {
            this.rank = 1;
        } else {
            this.rank = 0;
        }
    }

    // REQUIRES: x is either 1 or 0, y is either 1 or 0
    // EFFECTS: evaluates the expression x (operator) y
    public int evaluate(int x, int y) {
        switch (operator) {
            case '~':
                if (y == 1) {
                    return 0;
                } else {
                    return 1;
                }
            case '&':
                return Math.min(x, y);
            case '|':
                return Math.max(x, y);
            case '>':
                return evaluateConditional(x, y);
            case '*':
                return evaluateExOr(x, y);
            default:
                return evaluateBiconditional(x, y);
        }
    }


    // EFFECTS: evaluates the negation operator
    public int evaluateNegator(int x) {
        return evaluate(0, x);
    }

    // REQUIRES: test must be a valid operator
    // EFFECTS: returns true if this operator has a higher rank than the given one
    public boolean isHigherRank(char test) {
        Operator testOp = new Operator(test);
        return getRank() > testOp.getRank();
    }


    // EFFECTS: returns the rank of the operator
    public int getRank() {
        return rank;
    }

    // EFFECTS: returns the operator character
    public char getOperator() {
        return operator;
    }

    // EFFECTS: evaluates the conditional
    private int evaluateConditional(int x, int y) {
        if (x == 1 && y == 0) {
            return 0;
        }

        return 1;
    }

    // EFFECTS: evaluates the biconditional
    private int evaluateBiconditional(int x, int y) {
        if (x == y) {
            return 1;
        }

        return 0;
    }

    // EFFECTS: evaluates XOR
    private int evaluateExOr(int x, int y) {
        if (x == y) {
            return 0;
        }

        return 1;
    }
}
