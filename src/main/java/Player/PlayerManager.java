package Player;

import java.util.ArrayList;

public class PlayerManager {

    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Player> specators = new ArrayList<>();

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addSpectator(Player player) {
        specators.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void movePlayerToSpectators(Player player) {
        addSpectator(player);
        removePlayer(player);
    }

    public void removeAllPlayers() {
        players.clear();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getSpecators() {
        return specators;
    }

    public void sendMessageToAllPlayers(String msg) {
        for(Player p : players) {
            p.sendMessage(msg);
        }
    }

    public void sendMessageToAllSpectators(String msg) {
        for(Player p : specators) {
            p.sendMessage(msg);
        }
    }

    public void sendMessageToAllPlayersAndSpectators(String msg) {
        sendMessageToAllPlayers(msg);
        sendMessageToAllSpectators(msg);
    }
}
