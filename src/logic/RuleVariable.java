package logic;

import java.util.ArrayList;

public class RuleVariable extends Variable {
    protected BooleanRuleBase rb;
    protected ArrayList<Clause>  clauseRefs = new ArrayList<>();
    protected String promptText;
    protected String ruleName;

    public RuleVariable(BooleanRuleBase rb, String name) {
        super(name);
        this.rb = rb;
        rb.addVariable(this);
    }

    //Used only in backward chaining algorithm
    public void setValue(String value) {
        this.value = value;
        updateClauses();
    }

    //public String askUser() {
    //}

    public void addClauseRef (Clause ref) {
        this.clauseRefs.add(ref);
    }

    public void updateClauses () {
        for (Clause ref: clauseRefs) {
            ref.check();
        }
    }

    public void setRuleName (String ruleName) {
        this.ruleName = ruleName;
    }

    public void setPromptText (String promptText) {
        this.promptText = promptText;
    }

    public String getPromptText () {
        return promptText;
    }

    public void computeStatistics(String inValue) {}

    public int normalize(String inValue, ArrayList<Float> outArray, int inx) {
        return inx;
    }
}



















