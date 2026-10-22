package firstAgent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentReceveur extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();

                if (msg != null) {
                    System.out.println(getLocalName() + " a reçu le message : " + msg.getContent());

                    // Suite à la réception d’un message, il peut également être créé en utilisant la méthode createReply()[cite: 1]
                    ACLMessage reply = msg.createReply();

                    // Remplir le contenu et déclarer le performatif
                    reply.setPerformative(ACLMessage.AGREE);
                    reply.setContent("Bien reçu, merci !");

                    // Les autres champs sont instanciés automatiquement à partir du message reçu
                    send(reply);
                    System.out.println(getLocalName() + " : Réponse envoyée !");
                } else {
                    block(); // Met l'agent en pause s'il n'y a pas de message
                }
            }
        });
    }
}