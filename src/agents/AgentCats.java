package agents;
import logic.Category;
import logic.Cats;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentCats extends Agent {
    private String category;
    private ACLMessage envoye;
    private ACLMessage reçu;
    private Category currentCat;
    protected void setup() {
        System.out.println("the agent "+ getLocalName()+" is created");
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                if(getLocalName().equals("cheeseAgent"))category="cheese";
                if(getLocalName().equals("honeyAgent"))category="honey";
                if(getLocalName().equals("oliveOilAgent"))category="oliveOil";
                reçu =new ACLMessage(ACLMessage.INFORM);
                reçu=receive();
                if(reçu!=null){
                    System.out.println("the "+ getLocalName()+" receive message from "+ reçu.getSender());
                    String contain=reçu.getContent();
                    String[] labelsTMP = contain.split(",");

                    currentCat = Cats.categories.get(category);
                    currentCat.rb.reset();
                    currentCat.rb.setVariableValue(labelsTMP[1],labelsTMP[2]);
                    currentCat.rb.setVariableValue(labelsTMP[3],labelsTMP[4]);
                    currentCat.rb.setVariableValue(labelsTMP[5],labelsTMP[6]);
                    currentCat.rb.setVariableValue(labelsTMP[7],labelsTMP[8]);
                    currentCat.rb.setVariableValue(labelsTMP[9],labelsTMP[10]);
                    currentCat.rb.setVariableValue(labelsTMP[11],labelsTMP[12]);
                    currentCat.rb.setVariableValue(labelsTMP[13],labelsTMP[14]);
                    currentCat.rb.forwardChain();

                    String firstValue = currentCat.variables.get(labelsTMP[9]).getValue();
                    String secondValue = currentCat.variables.get(labelsTMP[11]).getValue();
                    String thirdValue = currentCat.variables.get(labelsTMP[13]).getValue();
                    String message=labelsTMP[9]+","+firstValue+","+labelsTMP[11]+","+secondValue+","+labelsTMP[13]+","+thirdValue;
                    envoye =new ACLMessage(ACLMessage.INFORM);
                    envoye.addReceiver(reçu.getSender());
                    envoye.setContent(message);
                    send(envoye);
                }
                else block();
            }
        });


    }
public void setCategory(String name){
        category=name;
}
}
