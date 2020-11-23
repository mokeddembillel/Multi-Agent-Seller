package logic;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class PizzaMain {
    public static BooleanRuleBase rb;
    public static Map<String, RuleVariable> variables = new HashMap<>();
    public static Map<String, Condition> conditions = new HashMap<>();
    public static Map<String, Rule> rules = new HashMap<>();

    private PizzaMain(){}

    static {
        rb = new BooleanRuleBase("cheese");
        Map<String, String> labels = new HashMap();
        try {
            // Load labels
            String filePath = new File("").getAbsolutePath();
            BufferedReader reader = new BufferedReader(new FileReader(filePath + ".\\src\\logic\\pizzaLabels.txt"));
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
        } catch (Exception e){
            System.out.println(e);
        }

        RuleVariable texture = new RuleVariable(rb, "texture");
        texture.setLabels(labels.get("texture"));
        variables.put("texture", texture);

        RuleVariable flavor = new RuleVariable(rb, "flavor");
        flavor.setLabels(labels.get("flavor"));
        variables.put("flavor", flavor);

        RuleVariable preparationMethod = new RuleVariable(rb, "preparationMethod");
        preparationMethod.setLabels(labels.get("preparationMethod"));
        variables.put("preparationMethod", preparationMethod);

        RuleVariable milkSource = new RuleVariable(rb, "milkSource");
        milkSource.setLabels(labels.get("milkSource"));
        variables.put("milkSource", milkSource);

        RuleVariable country = new RuleVariable(rb, "country");
        country.setLabels(labels.get("country"));
        country.setPromptText("how much is it ?");
        variables.put("country", country);

        RuleVariable cheese = new RuleVariable(rb, "cheese");
        cheese.setLabels(labels.get("cheese"));
        cheese.setPromptText("what is its type ?");
        variables.put("cheese", cheese);

        RuleVariable price = new RuleVariable(rb, "price");
        price.setLabels(labels.get("price"));
        price.setPromptText("how much is it ?");
        variables.put("price", price);

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
            //System.out.println("hi pspspspsps");
            readRules(".\\src\\company\\pizzaRules.txt");
            //addNewRule(".\\src\\company\\cheeseRules.txt", rule);
        } catch (Exception e){
            System.out.println(e);
        }

    }

    public static void  readRules(String pathToFile)  throws IOException {
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

    public static void readOneRule() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(".\\src\\logic\\pizzaRules.txt"));
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

    public static void addNewRule(String pathToFIle, String rule)  throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFIle, true));
        if(rules.size() > 0) {
            writer.write("\n");
            writer.flush();
        }
        writer.write(rule);
        writer.close();
        readOneRule();
    }










}
