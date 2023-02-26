package ui;

import model.*;

import java.util.*;

public class TTGenerator {

    private Argument argument;
    private Scanner input;

    boolean isValid;

    public TTGenerator() {
        argument = new Argument();
        input = new Scanner(System.in);
        runTTGenerator();
        isValid = true;
    }

    // EFFECTS: runs the Truth Table Generator
    public void runTTGenerator() {

        getInputExp();
        System.out.println("Here are your statements so far. If you wish"
                + " to add another statement, enter 'add'. If you want to delete an "
                + "existing statement, enter 'delete'.");

        getPremises();

        System.out.print("Please input the concluding expression:");
        inputConclusion();
        printTruthTable();
        displayValidity();

    }

    // EFFECTS: get user's inputted expression. If the input is invalid, ask for input
    //         again until an appropriate input is received.
    public void getInputExp() {
        System.out.print("Please a enter premise on the next line:");
        while (true) {
            if (input.hasNextLine()) {
                LogicExp userExp = new LogicExp(input.nextLine());

                if (userExp.isValid()) {
                    argument.addExp(userExp);
                    System.out.println("Premises so far:");
                    break;
                }
            }

            System.out.println("Invalid input. Please try again");
        }
    }

    // EFFECTS: prints out a short set of introductions for how to use the solver
    public void displayBriefDocumentation() {

    }

    public void getPremises() {
        String makeChoice = "";
        while (true) {
            try {
                makeChoice = input.nextLine().toLowerCase();
                if (makeChoice.equals("confirm")) {
                    break;
                } else if (makeChoice.equals("add")) {
                    getInputExp();
                } else if (makeChoice.equals("delete")) {
                    if (argument.getExps().isEmpty()) {
                        System.out.println("Can't delete more statements.");
                    } else {
                        askToDelete();
                    }
                } else {
                    System.out.println("Invalid input. Please try again...");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please choose one of these options:"
                        + "'add', 'delete', 'confirm.'");
            }

            printEachExp();
        }

    }

    // EFFECTS: display each expression in the premises on a separate line
    public void printEachExp() {
        int num = 1;
        for (LogicExp exp : argument.getExps()) {
            System.out.println(num + "." + exp.getExpString());
            num++;
        }
    }


    // EFFECTS: prompt user to select a premise to remove, by entering a number
    public void askToDelete() {
        System.out.println("I see you have chosen to remove a statement. "
                + "Select the one to be deleted by entering the corresponding number"
                + " (e.g., to delete 'P=Q' input 1).");

        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                int deleteNum = input.nextInt();

                if (deleteNum <= argument.getExps().size()) {
                    argument.deleteExp(deleteNum);
                    System.out.println("Premises so far:");
                    isValidInput = true;
                } else {
                    System.out.println("Invalid input. Please try again...");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again...");
            }

            if (isValidInput) {
                break;
            }

        }
    }

    // EFFECTS: prompt user to input a conclusion statement
    public void inputConclusion() {
        while (true) {
            if (input.hasNextLine()) {
                LogicExp userExp = new LogicExp(input.nextLine());

                if (userExp.isValid()) {
                    argument.setConclusion(userExp);

                    printEachExp();
                    System.out.println("-------------- \n"
                            + argument.getConclusion().getExpString());
                    break;
                }
            }

            System.out.println("Invalid input. Please try again...");
        }
    }

    // EFFECTS: prompts user to change conclusion
    public void changeConclusion() {
        System.out.println("Would you like to change the conclusion? Enter 'yes' or 'no'."
                + "By entering 'no', our solver will then generate the full truth table for all the expressions.");

        while (true) {
            if (input.hasNextLine()) {
                String changeCon = input.nextLine().toLowerCase();
                if (changeCon.equals("yes")) {
                    System.out.print("Please enter a statement for the conclusion:");
                    inputConclusion();
                } else if (changeCon.equals("no")) {
                    break;
                }
            }
        }

    }



    // EFFECTS: prints out the truth table based on the given premises and conclusion
    public void printTruthTable() {
        List<String> headerRow = new ArrayList<>();
        headerRow.addAll(argument.getModel().getSymbols());

        for (LogicExp e : argument.getExps()) {
            headerRow.add(e.getExpString());
        }

        headerRow.add(argument.getConclusion().getExpString());

        StringBuilder formatBuild = new StringBuilder();

        StringBuilder resultBuild = new StringBuilder();
        for (int n = 0; n < headerRow.size(); n++) {
            formatBuild.append("%-").append(longestExp() + 5).append("s");
        }

        String format = formatBuild.toString();

        resultBuild.append(String.format(format, headerRow.toArray())).append("\n");
        System.out.println(resultBuild);



        for (int i = 0; i < Math.pow(2, argument.getModel().numOfSymbols()); i++) {
            List<Integer> numRow = new ArrayList<>();
            numRow.addAll(argument.getModel().getValues());
            numRow.addAll(argument.computeEach());

            StringBuilder rowString = new StringBuilder();
            rowString.append(String.format(format, numRow.toArray())).append("\n");
            System.out.println(rowString);


            argument.getModel().nextValues();
        }
    }


    // EFFECTS: tells the user if the argument is valid
    public void displayValidity() {
        argument.getModel().resetModel();
        System.out.println("\n \n Validity: ");
        AssignModel possibleModel = argument.returnInvalidModel();
        if (possibleModel != null) {
            System.out.println("The argument is invalid.");

            for (String s : possibleModel.getSymbols()) {
                System.out.print(s + ":" + possibleModel.getValForSymbol(s) + "     ");
            }

        } else {
            System.out.println("The argument is valid.");
        }
    }


    // EFFECTS: returns the length of the longest expression
    private int longestExp() {
        int longestLength = argument.getExps().get(0).getExpString().length();

        for (LogicExp e : argument.getExps()) {
            if (e.getExpString().length() > longestLength) {
                longestLength = e.getExpString().length();
            }
        }

        return longestLength;
    }
}
