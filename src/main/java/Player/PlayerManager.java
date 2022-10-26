package Player;

import java.util.ArrayList;

public class PlayerManager {

    public ArrayList<Player> players = new ArrayList<>();

    public PlayerManager() {
        System.out.println("Constructor PlayerManager()");
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void removeAllPlayers() {
        players.clear();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void sendMessageToAllPlayers(String msg) {
        for(Player p : players) {
            p.sendMessage(msg);
        }
    }
}
