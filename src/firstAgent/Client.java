package firstAgent;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;


public class Client extends Agent {
    private String Cordonnée;


    protected void setup() {
        Object[] args = this.getArguments();
        if (args != null && args.length > 0) {
            this.Cordonnée = (String) args[0];
            System.out.println("Course est " + this.Cordonnée);



            addBehaviour(new OneShotBehaviour() {
                @Override
                public void action() {
                    ACLMessage inform = new ACLMessage(ACLMessage.INFORM);

                    inform.setContent(Cordonnée);
                    inform.setProtocol("information");

                    inform.addReceiver(new AID("Broker", AID.ISLOCALNAME));
                    send(inform);

                    System.out.println(getLocalName() + " : Message envoyé !");
                }
            });
        }
    }
}