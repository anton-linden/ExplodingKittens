import Client.Client;
import Game.GameManager;

public class Main {

    public static GameManager gameManager = new GameManager();

    /**
     * Inits the application.
     * There has to always be one real player!
     * @param params Valid parameters are: Start a server: int amountOfPlayers (2-5), int numberOfBots (1-4) :: Start a client: String ipAddress
     */
    public static void main(String[] params) {
        if(params.length == 2) {
            try {
                gameManager.initGame(Integer.valueOf(params[0]).intValue(), Integer.valueOf(params[1]).intValue());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
            gameManager.game();
        } else if(params.length == 1) {
            Client c = new Client(params[0]);
        } else {
            System.out.println("Server syntax: java ExplodingKittens numPlayers numBots");
            System.out.println("Client syntax: IP");
        }
    }

}
