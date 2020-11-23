package logic;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class BooleanRuleBase implements RuleBase {
    String name;
    Hashtable variableList = new Hashtable();
    //ArrayList<RuleVariable> variableList;
    ArrayList<Clause> clauseVarList = new ArrayList<>();
    ArrayList<Rule> ruleList = new ArrayList<>();
    //  I DONT KNOW WHAT THESE FOR
    ArrayList conclusionVarList;
    Rule ruleptr;
    Clause clauseptr;
    Stack<Clause> goalClauseStack;
    Hashtable effectors;
    Hashtable sensors;
    ArrayList factList;

    public BooleanRuleBase (String name) {
        this.name = name;
    }

    public void addVariable(RuleVariable rv) {
        variableList.put(rv.name, rv);
    }

    public void setVariableValue(String name,String value){
        RuleVariable tmp = (RuleVariable) variableList.get(name);
        tmp.setValue(value);
    }

    public void forwardChain() {
        ArrayList<Rule> conflictRuleSet;
        conflictRuleSet = match(true);

        while (conflictRuleSet.size() > 0) {
            Rule selected = selectRule(conflictRuleSet);
            selected.fire();
            conflictRuleSet = match(false);
        }
    }

    public ArrayList<Rule> match(Boolean test) {
        ArrayList<Rule> matchList = new ArrayList<>();

        for (Rule testRule : ruleList) {
            if (test) {
                testRule.check();
            }
            if (testRule.truth == null) {
                continue;
            }
            if (testRule.truth && !testRule.fired) {
                matchList.add(testRule);
            }
        }
        //displayConflictSet(matchList);
        return matchList;
    }

    public Rule selectRule(ArrayList<Rule> ruleSet) {
        Integer numClauses;
        Rule bestRule = ruleSet.get(0);
        Rule nextRule;
        Integer max = bestRule.numAntecedents();

        for (int i = 1; i < ruleSet.size(); i++) {
            nextRule = ruleSet.get(i);
            if ((numClauses = nextRule.numAntecedents()) > max) {
                max = numClauses;
                bestRule = nextRule;
            }
        }
        return bestRule;
    }

    public void reset() {
        for (Rule rule : ruleList) {
            rule.fired = false;
            rule.truth = null;
        }
        for (Clause clause : clauseVarList) {
            clause.truth = null;
        }
    }



    public void trace(String text) {}

    public void backwardChain(String goalVarName) {}
    public ArrayList getGoalVariables() {return new ArrayList();}

}











