package firstAgent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.HashMap;
import java.util.Map;


public class Broker extends  Agent{
    private Map<String, String> positionsClients = new HashMap<>();
    private Map<String, String> positionsTaxis = new HashMap<>();

    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();

                if (msg != null) {
                    String nomExpediteur = msg.getSender().getLocalName();
                    System.out.println(getLocalName() + " a reçu un message de " + nomExpediteur + " : " + msg.getContent());

//                    if nomExpediteur == "Taxis":
//                        PositionTaxis.append(msg.getContent())
//                    if nomExpediteur == "Client":
//                        PositionTaxis.append(msg.getContent())
//
//                    for i in enumerate(PositionClients)
//                        for j in enumerate(PositionClients)
//                            if PositionTaxis[i] == Positionclients[j]
//                                 PositionTaxis[i] = X
//                                 PositionClients[i] = X
//                                 Send message to taxis and clients

                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.AGREE);
                    reply.setContent("Bien reçu, merci !");

                    send(reply);
                    System.out.println(getLocalName() + " : Réponse envoyée !");
                } else {
                    block();
                }
            }
        });
    }
}
