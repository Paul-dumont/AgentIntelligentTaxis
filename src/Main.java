// Point d'entrée : démarre la plateforme JADE et crée les agents.
// Format de chaque agent : nom:package.Classe(arguments), séparés par des ;
public class Main {
    public static void main(String[] args) {
        String agents = "Broker:firstAgent.Broker;"
                + "taxi1:firstAgent.Taxis(1);"
                + "taxi2:firstAgent.Taxis(6);"
                + "client1:firstAgent.Client(7,2)";


        jade.Boot.main(new String[]{ "-agents", agents });
    }
}
