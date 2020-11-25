package agents;


import logic.Cats;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import ui.UserController;


public class MainAgent extends Agent {
    private ACLMessage envoye;
    private ACLMessage reçu;
    private String dataIn=null;
    private String dataOut="";

    protected void setup() {
        UserController.setSender(this);
        System.out.println("the agent "+ getLocalName()+" is created");
        ParallelBehaviour parallel =new ParallelBehaviour();
        parallel.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                if(dataIn!=null){
                    System.out.println(dataIn);
                    System.out.println("the agent "+ getLocalName()+" get dataIn from the user");
                    String[] labelsTMP = dataIn.split(",");
                    if (labelsTMP[0].equals("cheese")) {
                        envoye =new ACLMessage(ACLMessage.INFORM);
                        envoye.addReceiver(new AID("cheeseAgent", AID.ISLOCALNAME));
                        envoye.setContent(dataIn);
                        send(envoye);
                    }
                    else if(labelsTMP[0].equals("honey")){
                        envoye =new ACLMessage(ACLMessage.INFORM);
                        envoye.addReceiver(new AID("honeyAgent", AID.ISLOCALNAME));
                        envoye.setContent(dataIn);
                        send(envoye);
                    }
                    else if(labelsTMP[0].equals("oliveOil")){
                        envoye =new ACLMessage(ACLMessage.INFORM);
                        envoye.addReceiver(new AID("oliveOilAgent", AID.ISLOCALNAME));
                        envoye.setContent(dataIn);
                        send(envoye);
                    }
                    dataIn=null;
                } }});
        parallel.addSubBehaviour(new CyclicBehaviour() {
            @Override
        public void action() {
            reçu=new ACLMessage(ACLMessage.INFORM);
            reçu=receive();
            if(reçu!=null){ System.out.println("the agent "+ getLocalName()+" set the dataOut to the user");
            setDataOut(reçu.getContent());
            System.out.println(dataOut);
            }
            else block(); }});
       addBehaviour(parallel);
    }
    public void setDataIn(String data){
        this.dataIn=data;
    }
    public String getDataIn(){
        return dataIn;
    }
    public void setDataOut(String data){
        this.dataOut=data;
    }
    public String getDataOut(){
        return dataOut;
    }
}
