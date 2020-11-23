package logic;

        import com.sun.org.apache.xpath.internal.operations.Bool;

        import java.util.ArrayList;

public class Clause {

    public ArrayList<Rule> ruleRefs = new ArrayList<>();
    public RuleVariable lhs;
    public String rhs;
    public Condition cond;
    public Boolean consequent;
    public Boolean truth;

    public Clause(RuleVariable lhs, Condition cond,String rhs) {
        this.lhs = lhs;
        this.cond = cond;
        this.rhs = rhs;
        lhs.addClauseRef(this);
        truth = null;
        consequent = false;
    }

    public Clause() {}

    public String toString() {
        return lhs.name + cond.toString() + rhs + " ";
    }

    public void addRuleRef(Rule ref) {
        ruleRefs.add(ref);
    }

    public Boolean check() {
        if(consequent == true) {
            return truth = null;
        }
        if(lhs.value.equals("null")) {
            return truth = null;
        } else {
            Double lhsNumericValue = null;
            Double rhsNumericValue = null;
            Boolean bothNumeric = true;

            try {
                lhsNumericValue = Double.valueOf(lhs.value);
                rhsNumericValue = Double.valueOf(rhs);
            } catch(Exception e) {
                bothNumeric = false;
            }

            switch (cond.index) {
                case 1:
                    if (bothNumeric) {
                        truth = lhsNumericValue.compareTo(rhsNumericValue) == 0;
                    } else {
                        truth = lhs.value.equalsIgnoreCase(rhs);
                    }
                    break;
                case 2:
                    if (bothNumeric) {

                        truth = lhsNumericValue.compareTo(rhsNumericValue) > 0;
                    } else {
                        truth = lhs.value.compareTo(rhs) > 0;
                    }
                    break;
                case 3:
                    if (bothNumeric) {

                        truth = lhsNumericValue.compareTo(rhsNumericValue) < 0;
                    } else {
                        truth = lhs.value.compareTo(rhs) < 0;
                    }
                    break;
                case 4:
                    if (bothNumeric) {

                        truth = lhsNumericValue.compareTo(rhsNumericValue) != 0;
                    } else {
                        truth = lhs.value.compareTo(rhs) != 0;
                    }
                    break;
            }
        }
        return truth;
    }

    public void setConsequent () {
        consequent = true;
    }

    public Rule getRule() {
        if (consequent) {
            return ruleRefs.get(0);
        } else {
            return null;
        }
    }
}



















