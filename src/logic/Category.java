package logic;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Category {
    public String categoryName;
    public String categoryRulePath;
    public String categoryLabelPath;
    public ArrayList<String> inputVariables;
    public BooleanRuleBase rb;
    public Map<String, RuleVariable> variables = new HashMap<>();
    public Map<String, Condition> conditions = new HashMap<>();
    public Map<String, Rule> rules = new HashMap<>();

    public Category(String categoryName, ArrayList<String> inputVariables){
        this.categoryName = categoryName;
        categoryRulePath = ".\\src\\logic\\" + categoryName + "Rules.txt";
        categoryLabelPath = ".\\src\\logic\\" + categoryName + "Labels.txt";
        this.inputVariables = inputVariables;
        rb = new BooleanRuleBase(categoryName);
        Map<String, String> labels = new HashMap<>();
        try {
            labels = readLabels(categoryLabelPath);
        } catch (Exception e){
            System.out.println(e);
        }
        readVariables(labels);

        Condition cEquals = new Condition("=");
        conditions.put("=", cEquals);
        /*Condition cNotEquals = new Condition("!=");
        conditions.put("cNotEquals", cNotEquals);
        Condition cLessThan = new Condition("<");
        conditions.put("cLessThan", cLessThan);
        Condition cMoreThan = new Condition(">");
        conditions.put("cMoreThan", cMoreThan);*/

        try {
            //File file = new File(".\\src\\company\\");
            //for(String fileNames : file.list()) System.out.println(fileNames);
            readRules(categoryRulePath);
            //addNewRule(".\\src\\company\\cheeseRules.txt", rule);
        } catch (Exception e){
            System.out.println(e);
        }

    }


    public void  readRules(String pathToFile)  throws IOException {
        rules.clear();
        BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String ruleData[] = line.split(",");
            int antecedentsNumber = Integer.parseInt(ruleData[1]) * 3 + 2;
            ArrayList<Clause> clauseList = new ArrayList<>();
            int i = 2;
            while (i < antecedentsNumber) {
                String lhs = ruleData[i];
                String cond = ruleData[i+1];
                String rhs = ruleData[i+2];
                clauseList.add(new Clause(variables.get(lhs), conditions.get(cond), rhs));
                i += 3;
            }
            String lhs = ruleData[i];
            String cond = ruleData[i+1];
            String rhs = ruleData[i+2];
            rules.put(ruleData[0], new Rule(rb, ruleData[0], clauseList, new Clause(variables.get(lhs), conditions.get(cond), rhs)));
        }
    }

    // used in addNewRule() function to read the new added rule from the rules file
    public void readOneRule(String pathToFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
        String line = "",sCurrentLine;
        while ((sCurrentLine = reader.readLine()) != null) {
            line = sCurrentLine;
        }
        String ruleData[] = line.split(",");

        int antecedentsNumber = Integer.parseInt(ruleData[1]) * 3 + 2;

        ArrayList<Clause> clauseList = new ArrayList<>();
        int i = 2;
        while (i < antecedentsNumber) {
            String lhs = ruleData[i];
            String cond = ruleData[i+1];
            String rhs = ruleData[i+2];
            clauseList.add(new Clause(variables.get(lhs), conditions.get(cond), rhs));
            i += 3;
        }
        String lhs = ruleData[i];
        String cond = ruleData[i+1];
        String rhs = ruleData[i+2];
        rules.put(ruleData[0], new Rule(rb, ruleData[0], clauseList, new Clause(variables.get(lhs), conditions.get(cond), rhs)));
    }

    // used in admin controller to add a rule to the rules file than read it by readOneRule() function
    public void addNewRule(String pathToFIle, String rule)  throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFIle, true));
        if(rules.size() > 0) {
            writer.write("\n");
            writer.flush();
        }
        writer.write(rule);
        writer.close();
        readOneRule(pathToFIle);
    }

    // used in admin controller to delete a rule to the rules file
    public void deleteRule(String ruleName) throws IOException{
        ArrayList<String> rulesTMP = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(categoryRulePath));
        String line;
        while ((line = reader.readLine()) != null) {
            rulesTMP.add(line);
        }

        PrintWriter writer = new PrintWriter(categoryRulePath);
        writer.print("");
        writer.close();

        for (String rule : rulesTMP) {
            String[] subList = rule.split(",");
            if (subList[0].equals(ruleName)) {
                rulesTMP.remove(rule);
                break;
            }
        }

        BufferedWriter bwriter = new BufferedWriter(new FileWriter(categoryRulePath, true));
        for (int i = 0; i < rulesTMP.size(); i++) {
            bwriter.write(rulesTMP.get(i));
            if (i < rulesTMP.size()-1) {
                bwriter.flush();
                bwriter.write("\n");
            }
        }
        bwriter.close();
        readRules(categoryRulePath);
    }

    public Map<String, String> readLabels(String labelsPath) throws IOException {
        Map<String, String> labels = new HashMap();
        BufferedReader reader = new BufferedReader(new FileReader(labelsPath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] labelsTMP = line.split(",");
            StringBuilder listTMP = new StringBuilder();
            for (int i = 1; i < labelsTMP.length; i++) {
                listTMP.append(labelsTMP[i]);
                if (i < labelsTMP.length -1) {
                    listTMP.append(" ");
                }
            }
            String str = listTMP.toString();
            labels.put(labelsTMP[0], str);
        }
        return labels;
    }

    public void readVariables(Map<String, String> labels) {
        for (String var : labels.keySet()) {
            RuleVariable ruleVariable = new RuleVariable(rb, var);
            ruleVariable.setLabels(labels.get(var));
            variables.put(var, ruleVariable);
        }
    }

}
