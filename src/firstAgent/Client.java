package firstAgent;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;



public class Client extends Agent {
    private String Cordonnée;
    private String destination;


    protected void setup() {
        Object[] args = this.getArguments();
        if (args != null && args.length >= 2) {
            this.Cordonnée = (String) args[0];
            this.destination = (String) args[1];
            System.out.println(getLocalName() + " : je suis en " + Cordonnée + ", je veux aller en " + destination);



            addBehaviour(new OneShotBehaviour() {
                @Override
                public void action() {
                    ACLMessage inform = new ACLMessage(ACLMessage.REQUEST);

                    inform.setContent(Cordonnée + "," + destination);
                    inform.setProtocol("information");

                    inform.addReceiver(new AID("Broker", AID.ISLOCALNAME));
                    send(inform);

                    System.out.println(getLocalName() + " : Message envoyé !");
                }
            });

                    // attend la réponse du Broker
            addBehaviour(new CyclicBehaviour() {
                @Override
                public void action() {
                    ACLMessage msg = receive();

                    if (msg != null) {
                        if (msg.getPerformative() == ACLMessage.INFORM) {
                            // contenu : "taxi2"
                            System.out.println(getLocalName() + " : mon taxi est " + msg.getContent());
                        }
                    } else {
                        block();
                    }
                }
            });

        }
    }
}