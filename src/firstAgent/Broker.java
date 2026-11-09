package firstAgent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.HashMap;
import java.util.Map;
import jade.core.AID;
import java.util.ArrayList;



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

                    if (msg.getPerformative() == ACLMessage.REQUEST) {
                        positionsClients.put(nomExpediteur, msg.getContent());
                        System.out.println("Client " + nomExpediteur + " enregistré en " + msg.getContent());
                    }

                    else if (msg.getPerformative() == ACLMessage.INFORM) {
                        positionsTaxis.put(nomExpediteur, msg.getContent());
                        System.out.println(nomExpediteur + " enregistré en " + msg.getContent());
                    }

                    attribuerCourses();


                    System.out.println("Taxis connus : " + positionsTaxis);
                    System.out.println("Clients en attente : " + positionsClients);


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

    private void attribuerCourses() {
        // s'il n'y a pas de client ou pas de taxi, on ne peut rien faire
        if (positionsClients.isEmpty() || positionsTaxis.isEmpty()) {
            return;
        }

        // 1. on prend un client en attente
        for (String client : positionsClients.keySet()) {
            String[] infos = positionsClients.get(client).split(",");  // "7,2" → ["7", "2"]
            int posClient = Integer.parseInt(infos[0]);
            String destination = infos[1];

            // 2. on cherche le taxi le plus proche
            String meilleurTaxi = "";
            int meilleureDistance = 10;   // plus grand que toute distance possible

            for (String taxi : positionsTaxis.keySet()) {
                int posTaxi = Integer.parseInt(positionsTaxis.get(taxi));
                int distance = Math.abs(posTaxi - posClient);

                if (distance < meilleureDistance) {
                    meilleureDistance = distance;
                    meilleurTaxi = taxi;
                }
            }

            // 3. on prévient le client
            ACLMessage msgClient = new ACLMessage(ACLMessage.INFORM);
            msgClient.addReceiver(new AID(client, AID.ISLOCALNAME));
            msgClient.setContent(meilleurTaxi);
            send(msgClient);

            // 4. on prévient le taxi
            ACLMessage msgTaxi = new ACLMessage(ACLMessage.REQUEST);
            msgTaxi.addReceiver(new AID(meilleurTaxi, AID.ISLOCALNAME));
            msgTaxi.setContent(client + "," + posClient + "," + destination);
            send(msgTaxi);

            System.out.println("Broker : " + meilleurTaxi + " attribué à " + client);

            // 5. le taxi est occupé et le client est servi : on les retire des listes
            positionsTaxis.remove(meilleurTaxi);
            positionsClients.remove(client);

            return;   // une course à la fois, on s'arrête là
        }
    }


}
