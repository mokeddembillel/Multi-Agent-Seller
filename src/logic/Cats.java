package logic;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class Cats {
    public static Map<String, Category> categories = new HashMap<>();
    private Cats(){}

    static {
        String filePath = new File("").getAbsolutePath();

        try {
            // Load labels
            BufferedReader reader = new BufferedReader(new FileReader(".\\src\\logic\\categories.txt"));
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] category = line.split(",");
                ArrayList<String> inputVariables = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(category, 1, category.length)));
                categories.put(category[0], new Category(category[0], inputVariables));
            }

        } catch (Exception e){
            System.out.println(e);

        }
    }

    public static void createCategory(String categoryName, ArrayList<String> variablesList, ArrayList<String> inputVariables) {
        // Add the new category to the categories file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(".\\src\\logic\\categories.txt", true));
            if (categories.size() > 0) {
                writer.write("\n");
            }
            writer.write(categoryName);
            writer.flush();
            for (String invar : inputVariables) {
                writer.write("," + invar);
            }
            writer.close();
        } catch (Exception e){
            System.out.println(e);
        }
        // Create new Labels file for this category and fill it with all  the variables
        try {
            File labelsFile = new File(".\\src\\logic\\" + categoryName + "Labels.txt");
            if (labelsFile.createNewFile()) {
                System.out.println("File created: " + labelsFile.getName());
            } else {
                System.out.println("File already exists.");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(".\\src\\logic\\" + categoryName + "Labels.txt", true));
            for(int i = 0; i < variablesList.size(); i++) {
                writer.write(variablesList.get(i));
                writer.flush();
                if (i < variablesList.size() - 1) {
                    writer.write("\n");
                    writer.flush();
                }
            }
            writer.close();
        } catch (Exception e){
            System.out.println(e);
        }
        // Create new Rules file for this category
        try {
            File rulesFile = new File(".\\src\\logic\\" + categoryName + "Rules.txt");
            if (rulesFile.createNewFile()) {
                System.out.println("File created: " + rulesFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (Exception e){
            System.out.println(e);
        }
        // Add the new category to categories list
        categories.put(categoryName, new Category(categoryName, inputVariables));
    }

    public static void deleteCategory() {

    }
}
