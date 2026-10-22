package firstAgent;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentEnvoyeur extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
                inform.setContent("contenu");
                inform.setProtocol("information");

                inform.addReceiver(new AID("Receveur", AID.ISLOCALNAME));
                send(inform);

                System.out.println(getLocalName() + " : Message envoyé !");
            }
        });
    }
}
