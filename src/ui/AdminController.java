package ui;
import logic.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;


public class AdminController implements Initializable {
    @FXML private ComboBox<String> category;
    @FXML private ComboBox<String> clauseType;
    @FXML private ComboBox<String> variable;
    @FXML private ComboBox<String> condition;
    @FXML private TextField value;
    @FXML private Label errorMessage;
    @FXML private Button addClause;

    @FXML private TableView<ClauseTableItem> clausestable;
    @FXML private TableColumn<ClauseTableItem, SimpleStringProperty> variableTable;
    @FXML private TableColumn<ClauseTableItem, SimpleStringProperty> conditionTable;
    @FXML private TableColumn<ClauseTableItem, SimpleStringProperty> valueTable;
    @FXML private TableColumn<ClauseTableItem, SimpleStringProperty> consequentTable;
    // Observable list to show all clauses in tableview
    ObservableList<ClauseTableItem> clauses = FXCollections.observableArrayList();

    @FXML private TableView<RuleTableItem> rulestable;
    @FXML private TableColumn<RuleTableItem, SimpleStringProperty> ruleNameColumn;
    @FXML private TableColumn<RuleTableItem, SimpleStringProperty> numberOfAntecedentsColumn;
    @FXML private TableColumn<RuleTableItem, SimpleStringProperty> consequentColumn;
    // Observable list to show all rules in tableview
    ObservableList<RuleTableItem> rules = FXCollections.observableArrayList();

    // Array list to store antecedents
    private ArrayList<ArrayList<String>> antecedents = new ArrayList<>();
    // Array list to store consequent
    private ArrayList<String> consequent = new ArrayList<>();
    // Array list to store the variables that is allowed to add new label
    private ArrayList<String> allowedNewLabel = new ArrayList<>();
    private int maxAnt = 4;
    private int minAnt = 1;
    private Category currentCategory;
//####################################################################################################################//
//####################################################################################################################//

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        variableTable.setCellValueFactory(new PropertyValueFactory<>("variableTable"));
        conditionTable.setCellValueFactory(new PropertyValueFactory<>("conditionTable"));
        valueTable.setCellValueFactory(new PropertyValueFactory<>("valueTable"));
        consequentTable.setCellValueFactory(new PropertyValueFactory<>("consequentTable"));

        ruleNameColumn.setCellValueFactory(new PropertyValueFactory<>("ruleNameColumn"));
        numberOfAntecedentsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfAntecedentsColumn"));
        consequentColumn.setCellValueFactory(new PropertyValueFactory<>("consequentColumn"));

        allowedNewLabel.add("texture");
        allowedNewLabel.add("flavor");
        allowedNewLabel.add("preparationMethod");
        allowedNewLabel.add("milkSource");
        allowedNewLabel.add("country");
        allowedNewLabel.add("cheese");
        allowedNewLabel.add("price");

        allowedNewLabel.add("weight");
        allowedNewLabel.add("length");
        allowedNewLabel.add("speed");
        allowedNewLabel.add("lifeExpectancy");
        allowedNewLabel.add("gregarious");
        allowedNewLabel.add("name");
        allowedNewLabel.add("continent");

        allowedNewLabel.add("flowerSource");
        allowedNewLabel.add("taste");
        allowedNewLabel.add("color");
        allowedNewLabel.add("period");
        allowedNewLabel.add("country");
        allowedNewLabel.add("honey");
        allowedNewLabel.add("price");

        for(String cat : Cats.categories.keySet())
            category.getItems().add(cat);

        clauseType.getItems().add("Antecedent");
        clauseType.getItems().add("Consequent");
        //clauseType.hide();
        //clauseType.show();



        /*for ( Condition cond : Category.conditions.values()) {
            condition.getItems().add(cond.symbol);
        }*/
        condition.getItems().add("=");
        condition.getSelectionModel().select(0);
        condition.setDisable(true);

    }

//####################################################################################################################//

    public void  readRules(String pathToFile)  throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String ruleData[] = line.split(",");
            rules.add(new RuleTableItem(ruleData[0], ruleData[1], ruleData[ruleData.length-3] + ruleData[ruleData.length-2] + ruleData[ruleData.length-1] ));
        }
    }

//####################################################################################################################//

    @FXML
    private void categoryChange(ActionEvent event) {
        currentCategory = Cats.categories.get(category.getValue());
        try {
            rulestable.getItems().clear();
            readRules(currentCategory.categoryRulePath);
            rulestable.setItems(rules);
        } catch (Exception e) {
            System.out.println(e);
        }
         variable.getItems().clear();
        for ( String key : currentCategory.variables.keySet() ) {
            variable.getItems().add(key);
        }
    }

//####################################################################################################################//

    @FXML
    private void variableChange(ActionEvent event) {
        if(variable.getValue() == null) {
            return;
        }
        RuleVariable var = currentCategory.variables.get(variable.getValue());
        ArrayList<String> labels = var.getLabels();
        String message = "Existing Labels: " + labels.get(0);
        for (int i = 1; i < labels.size(); i++) {
            message += "," + labels.get(i);
        }
        /*if (allowedNewLabel.contains(variable.getValue())) {
            message += "), new value is allowed!";
        } else {
            message += "), new value is not allowed!";
        }*/
        errorMessage.setText(message);
    }

//####################################################################################################################//

    @FXML
    private void addClausef(MouseEvent event) {
        errorMessage.setText("");
        String clauseTypeValue = clauseType.getValue();
        String variableValue = variable.getValue();
        String conditionValue = condition.getValue();
        String rhsValue = value.getText();

        if (clauseTypeValue==null || variableValue==null || conditionValue==null || rhsValue==null) {
            errorMessage.setText("fill all the input fields!");
            return;
        }

        RuleVariable var = currentCategory.variables.get(variableValue);
        if(var.getIndex(rhsValue) == -1) {
            if(allowedNewLabel.contains(variableValue)) {
                var.addNewLabel(rhsValue);

                try {
                    ArrayList<String> labelsTMP = new ArrayList<>();
                    BufferedReader reader = new BufferedReader(new FileReader(currentCategory.categoryLabelPath));
                    String line;
                    // get all labels
                    while ((line = reader.readLine()) != null) {
                        labelsTMP.add(line);
                    }
                    // remove everything from  the file
                    PrintWriter writer = new PrintWriter(currentCategory.categoryLabelPath);
                    writer.print("");
                    writer.close();

                    // adding new label
                    for (String label : labelsTMP) {
                        String[] subList = label.split(",");
                        if (subList[0].equals(variableValue)) {
                            StringBuilder sb = new StringBuilder();
                            for (String str : subList) {
                                sb.append(str);
                                sb.append(",");
                            }
                            sb.append(rhsValue);
                            String str = sb.toString();
                            labelsTMP.remove(label);
                            labelsTMP.add(str);
                            break;
                        }
                    }

                    BufferedWriter bwriter = new BufferedWriter(new FileWriter(currentCategory.categoryLabelPath, true));
                    for (int i = 0; i < labelsTMP.size(); i++) {
                        bwriter.write(labelsTMP.get(i));
                        if (i < labelsTMP.size()-1) {
                            bwriter.flush();
                            bwriter.write("\n");
                        }
                    }
                    bwriter.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                String message = errorMessage.getText();
                errorMessage.setText("Use one of the allowed values!");

                return;
            }
        }

        if (clauseTypeValue.equals("Antecedent")) {
            antecedents.add(new ArrayList<>());
            antecedents.get(antecedents.size()-1).add(variableValue);
            antecedents.get(antecedents.size()-1).add(conditionValue);
            antecedents.get(antecedents.size()-1).add(rhsValue);

            clauses.add(new ClauseTableItem(variableValue, conditionValue, rhsValue,"false"));
            clausestable.setItems(clauses);

            variable.getSelectionModel().clearSelection();
            condition.getSelectionModel().clearSelection();

            //TMP
            condition.getSelectionModel().select(0);
            condition.setDisable(true);

            value.clear();

            if (antecedents.size() == maxAnt) {
                clauseType.getSelectionModel().select(1);
                clauseType.setDisable(true);
                //errorMessage.setText("Max number of antecedents");
                if (consequent.size() > 0) {
                    clauseType.setDisable(true);
                    variable.setDisable(true);
                    condition.setDisable(true);
                    value.setDisable(true);
                    addClause.setDisable(true);
                    clauseType.getSelectionModel().clearSelection();
                }
            }
            errorMessage.setText("Clause Added!");
        } else {
            if (antecedents.size() < maxAnt) {
                clauseType.getSelectionModel().select(0);
                clauseType.setDisable(true);
            } else {
                clauseType.setDisable(true);
                variable.setDisable(true);
                condition.setDisable(true);
                value.setDisable(true);
                addClause.setDisable(true);
                clauseType.getSelectionModel().clearSelection();

            }

            consequent.add(variableValue);
            consequent.add(conditionValue);
            consequent.add(rhsValue);
            clauses.add(new ClauseTableItem(variableValue, conditionValue, rhsValue,"true"));
            clausestable.setItems(clauses);

            variable.getSelectionModel().clearSelection();
            condition.getSelectionModel().clearSelection();

            //TMP
            condition.getSelectionModel().select(0);
            condition.setDisable(true);

            value.clear();

        }
    }
//####################################################################################################################//
    @FXML
    private void addRulef(MouseEvent event){
        if (antecedents.size() < minAnt) {
            errorMessage.setText("Add more antecedents");
        } else {
            if (consequent.size() == 0) {
                errorMessage.setText("Add the Consequent");
            } else {
                String randomName = UUID.randomUUID().toString();
                String rule = "";
                rule += randomName + "," + antecedents.size();
                for (ArrayList<String> ant: antecedents) {
                    rule += "," + ant.get(0) + "," + ant.get(1) + "," + ant.get(2);
                }
                rule += "," + consequent.get(0) + "," + consequent.get(1) + "," + consequent.get(2);

                rules.add(new RuleTableItem(randomName, String.valueOf(antecedents.size()), consequent.get(0) + consequent.get(1) + consequent.get(2) ));
                rulestable.setItems(rules);

                errorMessage.setText("Rule Added!");

                antecedents.clear();
                consequent.clear();

                clauseType.getSelectionModel().clearSelection();
                clauseType.setDisable(false);
                variable.setDisable(false);
                condition.setDisable(false);

                //TMP
                condition.getSelectionModel().select(0);
                condition.setDisable(true);

                value.setDisable(false);
                addClause.setDisable(false);

                clauses.clear();
                clausestable.setItems(clauses);


                try {
                    currentCategory.addNewRule(currentCategory.categoryRulePath, rule);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

//####################################################################################################################//
    @FXML
    private void deleteClausef(MouseEvent event) {
        if (clausestable.getSelectionModel().getSelectedIndex() != -1) {
            String variableTMP = clausestable.getSelectionModel().getSelectedItem().variableTable.getValue();
            String conditionTMP = clausestable.getSelectionModel().getSelectedItem().conditionTable.getValue();
            String valueTMP = clausestable.getSelectionModel().getSelectedItem().valueTable.getValue();
            String consequentTMP = clausestable.getSelectionModel().getSelectedItem().consequentTable.getValue();
            for (ClauseTableItem clause : clauses) {
                if (clause.variableTable.getValue().equals(variableTMP) && clause.conditionTable.getValue().equals(conditionTMP) && clause.valueTable.getValue().equals(valueTMP) && clause.consequentTable.getValue().equals(consequentTMP)) {
                    clauses.remove(clause);
                    break;
                }
            }

            if (consequentTMP.equals("true")) {
                consequent.clear();
                if (antecedents.size() == maxAnt) {
                    clauseType.getSelectionModel().select(1);
                    clauseType.setDisable(true);
                    variable.setDisable(false);
                    condition.setDisable(false);

                    //TMP
                    condition.getSelectionModel().select(0);
                    condition.setDisable(true);

                    value.setDisable(false);
                    addClause.setDisable(false);
                } else {
                    clauseType.getSelectionModel().clearSelection();
                    clauseType.setDisable(false);
                }

            } else {
                if (antecedents.size() == maxAnt) {
                    if (consequent.size() > 0 ) {
                        clauseType.getSelectionModel().select(0);
                        clauseType.setDisable(true);
                        variable.setDisable(false);
                        condition.setDisable(false);

                        //TMP
                        condition.getSelectionModel().select(0);
                        condition.setDisable(true);

                        value.setDisable(false);
                        addClause.setDisable(false);
                    } else {
                        clauseType.setDisable(false);
                    }
                }
                for (ArrayList<String> ant : antecedents) {
                    if (ant.get(0).equals(variableTMP) && ant.get(1).equals(conditionTMP) && ant.get(2).equals(valueTMP)) {
                        antecedents.remove(ant);
                        break;
                    }
                }

            }
        }

    }
//####################################################################################################################//
    @FXML
    private void deleteRulef(MouseEvent event) {
        if (rulestable.getSelectionModel().getSelectedIndex() != -1) {
            String ruleNameTMP = rulestable.getSelectionModel().getSelectedItem().ruleNameColumn.getValue();

            for (RuleTableItem rule : rules) {
                if (rule.ruleNameColumn.getValue().equals(ruleNameTMP)) {
                    rules.remove(rule);
                    break;
                }
            }
            try {
                currentCategory.deleteRule(ruleNameTMP);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void manageCategoriesf(MouseEvent mouseEvent) {
    }

//####################################################################################################################//
//####################################################################################################################//

    public class ClauseTableItem {
        private SimpleStringProperty variableTable;
        private SimpleStringProperty conditionTable;
        private SimpleStringProperty valueTable;
        private SimpleStringProperty consequentTable;

        ClauseTableItem(String variableTable, String conditionTable, String valueTable, String consequentTable) {
            this.variableTable = new SimpleStringProperty(variableTable);
            this.conditionTable = new SimpleStringProperty(conditionTable);
            this.valueTable = new SimpleStringProperty(valueTable);
            this.consequentTable = new SimpleStringProperty(consequentTable);
        }

        public String getVariableTable() {
            return variableTable.get();
        }

        public SimpleStringProperty variableTableProperty() {
            return variableTable;
        }

        public void setVariableTable(String variableTable) {
            this.variableTable.set(variableTable);
        }

        public String getConditionTable() {
            return conditionTable.get();
        }

        public SimpleStringProperty conditionTableProperty() {
            return conditionTable;
        }

        public void setConditionTable(String conditionTable) {
            this.conditionTable.set(conditionTable);
        }

        public String getValueTable() {
            return valueTable.get();
        }

        public SimpleStringProperty valueTableProperty() {
            return valueTable;
        }

        public void setValueTable(String valueTable) {
            this.valueTable.set(valueTable);
        }

        public String getConsequentTable() {
            return consequentTable.get();
        }

        public SimpleStringProperty consequentTableProperty() {
            return consequentTable;
        }

        public void setConsequentTable(String consequentTable) {
            this.consequentTable.set(consequentTable);
        }
    }

    public class RuleTableItem {
        private SimpleStringProperty ruleNameColumn;
        private SimpleStringProperty numberOfAntecedentsColumn;
        private SimpleStringProperty consequentColumn;

        RuleTableItem(String ruleNameColumn, String numberOfAntecedentsColumn, String consequentColumn) {
            this.ruleNameColumn = new SimpleStringProperty(ruleNameColumn);
            this.numberOfAntecedentsColumn = new SimpleStringProperty(numberOfAntecedentsColumn);
            this.consequentColumn = new SimpleStringProperty(consequentColumn);


        }

        public String getRuleNameColumn() {
            return ruleNameColumn.get();
        }

        public SimpleStringProperty ruleNameColumnProperty() {
            return ruleNameColumn;
        }

        public void setRuleNameColumn(String ruleNameColumn) {
            this.ruleNameColumn.set(ruleNameColumn);
        }

        public String getNumberOfAntecedentsColumn() {
            return numberOfAntecedentsColumn.get();
        }

        public SimpleStringProperty numberOfAntecedentsColumnProperty() {
            return numberOfAntecedentsColumn;
        }

        public void setNumberOfAntecedentsColumn(String numberOfAntecedentsColumn) {
            this.numberOfAntecedentsColumn.set(numberOfAntecedentsColumn);
        }

        public String getConsequentColumn() {
            return consequentColumn.get();
        }

        public SimpleStringProperty consequentColumnProperty() {
            return consequentColumn;
        }

        public void setConsequentColumn(String consequentColumn) {
            this.consequentColumn.set(consequentColumn);
        }
    }
}

