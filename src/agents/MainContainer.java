package agents;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import ui.Launch;

import java.awt.*;

public class MainContainer {
    public static void main(String[] args) {
        Runtime runtime=Runtime.instance();
        Profile profileImpl=new ProfileImpl();
        Profile profileImpl1=new ProfileImpl();
        ContainerController mainContainer = runtime.createMainContainer(profileImpl);
        ContainerController container = runtime.createAgentContainer(profileImpl1);
        try {
            AgentController ag1=container.createNewAgent("main",agents.MainAgent.class.getName(),null);
            AgentController ag2=container.createNewAgent("cheeseAgent", AgentCats.class.getName(),null);
            AgentController ag3=container.createNewAgent("honeyAgent", AgentCats.class.getName(),null);
            AgentController ag4=container.createNewAgent("oliveOilAgent", AgentCats.class.getName(),null);

            AgentController ag=mainContainer.createNewAgent("rma","jade.tools.rma.rma",null);

            ag.start();
            ag1.start();
            ag2.start();
            ag3.start();
            ag4.start();

        } catch (ControllerException e) {
            e.printStackTrace();
        }
        Launch.main(args);
    }
}
