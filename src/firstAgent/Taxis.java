package firstAgent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Taxis extends Agent {
    private String position;

    @Override
    protected void setup() {

        System.out.println("Taxis created.");
        Object[] args = this.getArguments();
        if (args != null && args.length > 0) {
            this.position = (String) args[0];
            System.out.println("My position is: " + this.position);
        }

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
                inform.setContent(position);
                inform.setProtocol("information");

                inform.addReceiver(new AID("Broker", AID.ISLOCALNAME));
                send(inform);

                System.out.println(getLocalName() + " : Position sent to Broker");
            }
        });
    }
}