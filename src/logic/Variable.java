package logic;

import java.util.ArrayList;
import java.util.StringTokenizer;

public abstract class Variable {
    protected String name;
    protected String value;
    public ArrayList<String> labels = new ArrayList<>();
    protected int column;

    public Variable() {}

    public Variable(String name) {
        this.name = name;
        value = null;
    }

    public String getName () {
         return name;
    }

    public void setValue (String value) {
        this.value = value;
    }

    public String getValue () {
        return value;
    }

    public void setLabels (String newLabels) {
        StringTokenizer tok = new StringTokenizer(newLabels, " ");
        while (tok.hasMoreTokens()) {
            labels.add(tok.nextToken());
        }
    }

    public String getLabel(int index) {
        return labels.get(index);
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void addNewLabel(String label) {labels.add(label);}

    public String getLabelsAsString () {
        String labelList = "";
        for (String label: labels) {
            labelList += label + " ";
        }
        return labelList;
    }

    public Integer getIndex (String label) {
        Integer index = -1;
        if (label == null) {
            return index;
        }
        for(int i = 0; i < labels.size(); i++) {
            if (label.equals(labels.get(i))) {
                index = i;
                break;
            }
        }
        return index;
    }

    public Boolean categorical() {
        if (labels != null) {
            return true;
        } else {
            return false;
        }
    }

    //################################################################################################################//
    public String toString() {
        return name;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public abstract void computeStatistics(String inValue);
    public abstract  int normalize(String inValue, ArrayList<Float> outArray, int inx);
    public int normalizedSize() {
        return 1;
    }
    public String getDecodedValue(ArrayList<Float> act, int index) {
        return String.valueOf(act.get(index));
    }

}



















