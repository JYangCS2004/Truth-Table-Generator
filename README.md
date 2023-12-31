# My Personal Project: Truth Table Calculator

### What will the application do?
In CPSC 121, we spent a good chunk of the semester learning about boolean algebra, or
logical expressions. Specifically, we were often required to show whether if 
and logical argument is of a valid form. We used two different methods to achieve this. 
We showed that an argument is valid by applying equivalency laws and rules of inference.
However, we can also use a truth table to show validity. The idea is to draw a truth table listing all possible combinations
of truth values from each logical statement in the argument.
An example of this is shown below:

![img.png](img.png)

For this term's 210 project, the task is to create a program that can generate such truth tables.
Moreover, the program should have the ability to determine the validity of the argument.
An argument can be shown to be invalid if there is a row in the truth table 
where the **premises are true and the conclusion is false**.
We also can use it to test logical equivalence between two different logical statements using
the truth table. **The program should be able to take in logical statements as strings (e.g. P ∨ Q.), and interpret
and evaluate based on given truth values.**
### Who will use it?
This program will be particularly useful for students taking CPSC 121 and PHIL 220. Drawing a truth table is an automatic, but
can be a time-consuming process, considering logical statements can get arbitrarily long. A complicated truth table can be cumbersome
to analyze. It would be nice to have a program to automate the process.

### Why is this project of interest to you?
We looked into many types of interesting algorithms in CPSC 110. I feel like this would be a nice extension to what we learned
in DrRacket. Additionally, I'm also currently re-learning this material in PHIL 220. This project could reinforce what I 
have learned in class, and as an extension to what I already know.

## User Stories
  - As a user, I want to be able to add an arbitrary number of premises to the argument.
  - As a user, I want to be shown the complete truth table that displays all the expressions inputted.
  - As a user, I want to be told the specific set of truth values that proves that an argument is invalid.
  - As a user, I want to be able to delete a premise from the set of logical statements.
  - As a user, I want to have the option to save the current statements in the argument any time during the application.
  - As a user, I want to have the option to load the set of statements that was last saved at any time during the program.

## Instructions for Grader
- You can generate the first required action relating to adding premises to an Argument by inputting in the text field beside "Add Premise:" and then pressing 'Enter' on the keyboard to add it to the argument.
The newly added premise will appear in the JList on the right.
- You can remove a premise from the Argument by selecting any premise in the JList, and then clicking the 'Delete' button.
- You can locate the visual component by pressing the 'Save' button, or by pressing 'Enter' from the 'Add Premise' textbox on an invalid input.
- You can save the state of my application by clicking 'Save' button.
- You can reload the state of my application by clicking 'Load' button. 

Additional Notes:
- To input a conclusion, input text into the textbox beside 'Add Conclusion', and press 'Enter'. A popup menu will appear showing an image icon.
- Instructions for input: The only allowed characters are: * \(xor\), = (bi-condition), | (or),
& (and), ~ (negation), ( (left bracket), ) (right bracket), > (conditional), and the 26 letters in the alphabet.

# Phase 4: Task 2
Sample of Logged Events:

Wed Apr 12 22:02:26 PDT 2023

A new premise was added.

Wed Apr 12 22:02:31 PDT 2023

A new premise was added.

Wed Apr 12 22:02:41 PDT 2023

Conclusion was changed to q.

Wed Apr 12 22:02:47 PDT 2023

Conclusion was changed to ~p.

Wed Apr 12 22:02:55 PDT 2023

A premise was removed from the argument.

Wed Apr 12 22:03:01 PDT 2023

A premise was removed from the argument.

# Phase 4: Task 3
If I had more time to work on this project, I would definitely modify the functionality of the AssignModel class. Currently, the class that 
represents the set of truth tables needs the client to manually update the set of truth tables. Additionally, the client also needs to reset the truth values.
I could refactor it so that all the user needs to do is loop through the set of truth values, and not worry about the underlying structure of the class.
To do this, I could implement the Iterator Pattern. The class would contain the list of all the truth values, and the client just need to loop through them without manually updating to the next set of values.
This could significantly improve my code in various ways. Right now, many places in the code involving looping through the truth values.
So this will be improved by implementing the pattern. 

Additionally, I want to GUI to show the type of the user-inputted statement (eg. tautology, contradiction, and contingency).
To do this, each logic expression would need its own set of truth values. Hence, using the iterator pattern on the AssignModel class would make this more efficient.

