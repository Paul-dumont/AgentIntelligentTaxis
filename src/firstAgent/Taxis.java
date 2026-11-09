package firstAgent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;


public class Taxis extends Agent {
    private int position;

    @Override
    protected void setup() {

        System.out.println("Taxis created.");
        Object[] args = this.getArguments();
        if (args != null && args.length > 0) {
            this.position = Integer.parseInt((String) args[0]);
            System.out.println("My position is: " + this.position);
        }

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
                inform.setContent((String.valueOf(position)));
                inform.setProtocol("information");

                inform.addReceiver(new AID("Broker", AID.ISLOCALNAME));
                send(inform);

                System.out.println(getLocalName() + " : Position sent to Broker");
            }
        });

        // écoute les missions envoyées par le Broker
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();

                if (msg != null) {
                    if (msg.getPerformative() == ACLMessage.REQUEST) {
                        // contenu : "client1,7,2"
                        String[] infos = msg.getContent().split(",");
                        String client = infos[0];
                        String depart = infos[1];
                        String arrivee = infos[2];

                        System.out.println(getLocalName() + " : je vais chercher " + client
                                + " en " + depart + " pour l'emmener en " + arrivee);
                    }
                } else {
                    block();
                }
            }
        });

    }
}