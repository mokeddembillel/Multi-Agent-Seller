package logic;

import java.util.ArrayList;

public class Rule {
    public BooleanRuleBase rb;
    public String name;
    public ArrayList<Clause> antecedents = new ArrayList<>();
    public Clause consequent;
    public Boolean truth;
    public Boolean fired = false;

    public Rule(BooleanRuleBase rb, String name, Clause lhs, Clause rhs) {
        this.rb = rb;
        this.name = name;
        this.antecedents.add(lhs);
        lhs.addRuleRef(this);
        this.consequent = rhs;
        rhs.addRuleRef(this);
        rhs.setConsequent();
        rb.ruleList.add(this);
        truth = null;
    }

    public Rule(BooleanRuleBase rb, String name, ArrayList<Clause> lhsClauses, Clause rhs) {
        this.rb = rb;
        this.name = name;
        for (Clause lhsClause : lhsClauses) {
            lhsClause.addRuleRef(this);
            antecedents.add(lhsClause);
        }
        consequent = rhs;
        rhs.addRuleRef(this);
        rhs.setConsequent();
        rb.ruleList.add(this);
        truth = null;
    }

    public Integer numAntecedents() {
        return this.antecedents.size();
    }

    public Boolean check() {
        //rb.trace("\nTesting rule " + name);
        for (int i = 0; i < numAntecedents(); i++) {
            if (antecedents.get(i).truth == null) {

                return null;
            }
            if (antecedents.get(i).truth) {
                continue;
            } else {
                truth = false;
                return false;
            }
        }
        truth = true;
        return true;
    }

    public void fire() {
        //rb.trace("\nFiring rule " + name);
        truth = true;
        fired = true;
        if (consequent.lhs == null) {
            //consequent.peform(rb);
        } else {
            consequent.lhs.setValue(consequent.rhs);
            System.out.println("Firing Rule : " + this.name + " , result is => " + consequent.lhs.name + " = " + consequent.rhs);
            checkRules(consequent.lhs.clauseRefs);
        }
    }

    public static void checkRules(ArrayList<Clause> clauseRefs) {
        for (Clause clauseRef : clauseRefs) {
            ArrayList<Rule> tmpRules = clauseRef.ruleRefs;
            for (Rule tmpRule : tmpRules) {
                tmpRule.check();
            }
        }
    }


}
