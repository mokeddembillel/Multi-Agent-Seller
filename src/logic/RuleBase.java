package logic;


import java.util.ArrayList;

public interface RuleBase {
    //void setDisplay(JtextArea);
    void trace(String text);
    //void displayVariables(JTextArea textArea);
    //void displayRules(JTextArea textArea);
    void reset();
    void backwardChain(String goalVarName);
    void forwardChain();
    ArrayList getGoalVariables();
}
