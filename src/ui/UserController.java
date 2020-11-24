package ui;

import agents.MainAgent;
import logic.Category;
import logic.Cats;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

    public class UserController implements Initializable {
        @FXML private ComboBox<String> category;

        private Category currentCategory;
        private Map<String, ComboBox<String>> inputList = new HashMap<>();

        @FXML
        private StackPane Mystack;

        @FXML
        private HBox honey;

        @FXML
        private VBox leftBox1;

        @FXML
        private VBox rightBox1;

        @FXML
        private ComboBox<String> labelFlowerSource;

        @FXML
        private ComboBox<String> labelTaste;

        @FXML
        private ComboBox<String> labelColor;

        @FXML
        private ComboBox<String> labelPeriod;

        @FXML
        private HBox oliveOil;

        @FXML
        private VBox leftBox2;

        @FXML
        private VBox rightBox2;

        @FXML
        private ComboBox<String> labelType;

        @FXML
        private ComboBox<String> labelOliveSource;

        @FXML
        private ComboBox<String> labelFlavorOlive;

        @FXML
        private ComboBox<String> labelColorOlive;

        @FXML
        private HBox cheese;

        @FXML
        private VBox leftBox;

        @FXML
        private VBox rightBox;

        @FXML
        private ComboBox<String> labelPreparation;

        @FXML
        private ComboBox<String> labelTexture;

        @FXML
        private ComboBox<String> labelFlavor;

        @FXML
        private ComboBox<String> labelMilk;

        @FXML
        private Button search;

        @FXML
        private Label outPrint1;

        @FXML
        private Label outPrint2;

        @FXML
        private Label outPrint3;

        @FXML
        private Label outPrint4;

        static private MainAgent sender;

        static public void setSender(MainAgent ag){
            sender=ag;
        }

        @FXML
        public void initialize(URL url, ResourceBundle rb) {

            for (String categoryTMP : Cats.categories.keySet()) {
                category.getItems().add(categoryTMP);
            }
            //search.setDisable(true);
            this.Mystack.setDisable(true);
            this.search.setDisable(true);
        }

        @FXML
        private void categoryChange(ActionEvent event) {
            this.search.setDisable(false);
            currentCategory = Cats.categories.get(category.getValue());
            if(category.getValue().equals("cheese")){
                cheese.toFront();
                for(int i=0;i<currentCategory.variables.get(labelPreparation.promptTextProperty().getValue()).labels.size();i++){
                    labelPreparation.getItems().add(currentCategory.variables.get(labelPreparation.promptTextProperty().getValue()).labels.get(i));
                }
                for(int i=0;i<currentCategory.variables.get(labelTexture.promptTextProperty().getValue()).labels.size();i++){
                    labelTexture.getItems().add(currentCategory.variables.get(labelTexture.promptTextProperty().getValue()).labels.get(i));
                }
                for(int i=0;i<currentCategory.variables.get(labelFlavor.promptTextProperty().getValue()).labels.size();i++){
                    labelFlavor.getItems().add(currentCategory.variables.get(labelFlavor.promptTextProperty().getValue()).labels.get(i));
                }
                for(int i=0;i<currentCategory.variables.get(labelMilk.promptTextProperty().getValue()).labels.size();i++){
                    labelMilk.getItems().add(currentCategory.variables.get(labelMilk.promptTextProperty().getValue()).labels.get(i));
                }
            }
            else if(category.getValue().equals("honey")){
                honey.toFront();
                for(int i=0;i<currentCategory.variables.get(labelFlowerSource.promptTextProperty().getValue()).labels.size();i++){
                    labelFlowerSource.getItems().add(currentCategory.variables.get(labelFlowerSource.promptTextProperty().getValue()).labels.get(i));
                }
                for(int i=0;i<currentCategory.variables.get(labelTaste.promptTextProperty().getValue()).labels.size();i++){
                    labelTaste.getItems().add(currentCategory.variables.get(labelTaste.promptTextProperty().getValue()).labels.get(i));
                }
                for(int i=0;i<currentCategory.variables.get(labelColor.promptTextProperty().getValue()).labels.size();i++){
                    labelColor.getItems().add(currentCategory.variables.get(labelColor.promptTextProperty().getValue()).labels.get(i));
                }
                for(int i=0;i<currentCategory.variables.get(labelPeriod.promptTextProperty().getValue()).labels.size();i++){
                    labelPeriod.getItems().add(currentCategory.variables.get(labelPeriod.promptTextProperty().getValue()).labels.get(i));
                }

            }
            else if(category.getValue().equals("oliveOil")){
                oliveOil.toFront();
                for(int i=0;i<currentCategory.variables.get(labelType.promptTextProperty().getValue()).labels.size();i++){
                    labelType.getItems().add(currentCategory.variables.get(labelType.promptTextProperty().getValue()).labels.get(i));
                }
                for(int i=0;i<currentCategory.variables.get(labelOliveSource.promptTextProperty().getValue()).labels.size();i++){
                    labelOliveSource.getItems().add(currentCategory.variables.get(labelOliveSource.promptTextProperty().getValue()).labels.get(i));
                }
                for(int i=0;i<currentCategory.variables.get(labelFlavorOlive.promptTextProperty().getValue()).labels.size();i++){
                    labelFlavorOlive.getItems().add(currentCategory.variables.get(labelFlavorOlive.promptTextProperty().getValue()).labels.get(i));
                }
                for(int i=0;i<currentCategory.variables.get(labelColorOlive.promptTextProperty().getValue()).labels.size();i++){
                    labelColorOlive.getItems().add(currentCategory.variables.get(labelColorOlive.promptTextProperty().getValue()).labels.get(i));
                }

            }
            this.Mystack.setDisable(false);

        }

        @FXML
        private void searchf(MouseEvent event) {
            currentCategory = Cats.categories.get(category.getValue());
            System.out.println(" agent :"+sender.getAID().getLocalName());
            if(category.getValue().equals("cheese")){
                if (labelPreparation.getValue()!=null && labelTexture.getValue()!=null && labelFlavor.getValue()!=null && labelMilk.getValue()!=null) {

                    search.setDisable(true);
                    String data="cheese,"+"preparationMethod,"+labelPreparation.getValue()+","+"texture,"+labelTexture.getValue()+","+"flavor,"+labelFlavor.getValue()+","+"milkSource,"+labelMilk.getValue()+","+"country,null,cheese,null,price,null";
                    sender.setDataIn(data);
                    System.out.println(sender.getDataIn());
                    while (sender.getDataOut().equals("")){
                        System.out.println(sender.getDataIn());
                    }
                    String contain=sender.getDataOut();
                    String[] labelsTMP = contain.split(",");
                    if (labelsTMP[1].equals("null")) {
                        labelPreparation.getSelectionModel().clearSelection();
                        labelTexture.getSelectionModel().clearSelection();
                        labelFlavor.getSelectionModel().clearSelection();
                        labelMilk.getSelectionModel().clearSelection();
                        search.setDisable(false);
                        outPrint1.setText("Product is not available !");
                        outPrint2.setText("");
                        outPrint3.setText("");
                        outPrint4.setText("");
                    } else {
                        labelPreparation.getSelectionModel().clearSelection();
                        labelTexture.getSelectionModel().clearSelection();
                        labelFlavor.getSelectionModel().clearSelection();
                        labelMilk.getSelectionModel().clearSelection();
                        search.setDisable(false);
                        outPrint1.setText("Product Available !");
                        outPrint2.setText(labelsTMP[0]+": " + labelsTMP[1]);
                        outPrint3.setText(labelsTMP[2]+": " + labelsTMP[3]);
                        outPrint4.setText(labelsTMP[4]+": " + labelsTMP[5]);
                    }
                    sender.setDataOut("");
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setHeaderText("Impossible d'efféctuer la recherche");
                    alert.setContentText("Veuillez remplir tous les labels !");
                    alert.showAndWait();

                }

            }
            else if(category.getValue().equals("oliveOil")){
                if (labelType.getValue()!=null && labelOliveSource.getValue()!=null && labelFlavorOlive.getValue()!=null && labelColorOlive.getValue()!=null) {

                    search.setDisable(true);
                    String data="oliveOil,"+"type,"+labelType.getValue()+","+"oliveSource,"+labelOliveSource.getValue()+","+"flavor,"+labelFlavorOlive.getValue()+","+"color,"+labelColorOlive.getValue()+","+"country,null,oliveOil,null,price,null";
                    sender.setDataIn(data);
                    while (sender.getDataOut().equals("")){
                        System.out.println(sender.getDataIn());
                    }
                    String contain=sender.getDataOut();
                    String[] labelsTMP = contain.split(",");
                    if (labelsTMP[1].equals("null")) {
                        labelType.getSelectionModel().clearSelection();
                        labelOliveSource.getSelectionModel().clearSelection();
                        labelFlavorOlive.getSelectionModel().clearSelection();
                        labelColorOlive.getSelectionModel().clearSelection();
                        search.setDisable(false);
                        outPrint1.setText("Product is not available !");
                        outPrint2.setText("");
                        outPrint3.setText("");
                        outPrint4.setText("");
                    } else {
                        labelType.getSelectionModel().clearSelection();
                        labelOliveSource.getSelectionModel().clearSelection();
                        labelFlavorOlive.getSelectionModel().clearSelection();
                        labelColorOlive.getSelectionModel().clearSelection();
                        search.setDisable(false);
                        outPrint1.setText("Product Available !");
                        outPrint2.setText(labelsTMP[0]+": " + labelsTMP[1]);
                        outPrint3.setText(labelsTMP[2]+": " + labelsTMP[3]);
                        outPrint4.setText(labelsTMP[4]+": " + labelsTMP[5]);
                    }
                    sender.setDataOut("");
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setHeaderText("Impossible d'efféctuer la recherche");
                    alert.setContentText("Veuillez remplir tous les labels !");
                    alert.showAndWait();

                }


            }
            else if(category.getValue().equals("honey")){
                if (labelFlowerSource.getValue()!=null && labelTaste.getValue()!=null && labelColor.getValue()!=null && labelPeriod.getValue()!=null) {

                    search.setDisable(true);
                    String data="honey,"+"flowerSource,"+labelFlowerSource.getValue()+","+"taste,"+labelTaste.getValue()+","+"color,"+labelColor.getValue()+","+"period,"+labelPeriod.getValue()+","+"country,null,honey,null,price,null";
                    sender.setDataIn(data);
                    while (sender.getDataOut().equals("")){
                        System.out.println(sender.getDataIn());
                    }
                    String contain=sender.getDataOut();
                    String[] labelsTMP = contain.split(",");
                    if (labelsTMP[1].equals("null")) {
                        labelFlowerSource.getSelectionModel().clearSelection();
                        labelTaste.getSelectionModel().clearSelection();
                        labelColor.getSelectionModel().clearSelection();
                        labelPeriod.getSelectionModel().clearSelection();
                        search.setDisable(false);
                        outPrint1.setText("Product is not available !");
                        outPrint2.setText("");
                        outPrint3.setText("");
                        outPrint4.setText("");
                    } else {
                        labelFlowerSource.getSelectionModel().clearSelection();
                        labelTaste.getSelectionModel().clearSelection();
                        labelColor.getSelectionModel().clearSelection();
                        labelPeriod.getSelectionModel().clearSelection();
                        search.setDisable(false);
                        outPrint1.setText("Product Available !");
                        outPrint2.setText(labelsTMP[0]+": " + labelsTMP[1]);
                        outPrint3.setText(labelsTMP[2]+": " + labelsTMP[3]);
                        outPrint4.setText(labelsTMP[4]+": " + labelsTMP[5]);
                    }
                    sender.setDataOut("");
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setHeaderText("Impossible d'efféctuer la recherche");
                    alert.setContentText("Veuillez remplir tous les labels !");
                    alert.showAndWait();

                }


            }


            }

        /*String preparationMethodValue = preparationMethod.getValue();
        String textureValue = texture.getValue();
        String flavorValue = flavor.getValue();
        String milkSourceValue = milkSource.getValue();

        Category.rb.reset();
        Category.rb.setVariableValue("preparationMethod",preparationMethodValue);
        Category.rb.setVariableValue("texture",textureValue);
        Category.rb.setVariableValue("flavor",flavorValue);
        Category.rb.setVariableValue("milkSource",milkSourceValue);
        Category.rb.setVariableValue("country","null");
        Category.rb.setVariableValue("cheese","null");
        Category.rb.setVariableValue("price","null");
        Category.rb.forwardChain();

        String countryValue = Category.variables.get("country").getValue();
        String cheeseValue = Category.variables.get("cheese").getValue();
        String priceValue = Category.variables.get("price").getValue();

        if (countryValue.equals("null")) {
            preparationMethod.getSelectionModel().clearSelection();
            texture.getSelectionModel().clearSelection();
            flavor.getSelectionModel().clearSelection();
            milkSource.getSelectionModel().clearSelection();
            search.setDisable(true);
            available.setText("Product is not available !");
            //country.
        } else {
            preparationMethod.getSelectionModel().clearSelection();
            texture.getSelectionModel().clearSelection();
            flavor.getSelectionModel().clearSelection();
            milkSource.getSelectionModel().clearSelection();
            search.setDisable(true);
            available.setText("Product Available !");
            cheese.setText("Cheese : " + cheeseValue);
            country.setText("Made in : " + countryValue);
            price.setText("Price : " + priceValue);
        }

    }
    @FXML
    private void pmChange(ActionEvent event) {
        if (preparationMethod.getValue()!=null && texture.getValue()!=null && flavor.getValue()!=null && milkSource.getValue()!=null) {
            search.setDisable(false);
        }
        available.setText("");
        cheese.setText("");
        country.setText("");
        price.setText("");
    }
    @FXML
    private void textureChange(ActionEvent event) {
        if (preparationMethod.getValue()!=null && texture.getValue()!=null && flavor.getValue()!=null && milkSource.getValue()!=null) {
            search.setDisable(false);
        }
        available.setText("");
        cheese.setText("");
        country.setText("");
        price.setText("");
    }
    @FXML
    private void flavorChange(ActionEvent event) {
        if (preparationMethod.getValue()!=null && texture.getValue()!=null && flavor.getValue()!=null && milkSource.getValue()!=null) {
            search.setDisable(false);
        }
        available.setText("");
        cheese.setText("");
        country.setText("");
        price.setText("");
    }
    @FXML
    private void milkSourceChange(ActionEvent event) {
        if (preparationMethod.getValue()!=null && texture.getValue()!=null && flavor.getValue()!=null && milkSource.getValue()!=null) {
            search.setDisable(false);
        }
        available.setText("");
        cheese.setText("");
        country.setText("");
        price.setText("");
    }*/

}
