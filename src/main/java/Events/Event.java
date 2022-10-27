package Events;

import Game.GameManager;
import Player.Player;

public abstract class Event {

    private String name = "Event";
    private String type = name;
    private Player target = null;

    /**
     * Executes instructions specific to the created event.
     * @param gameManager the GameManager of the current game.
     */
    public void execute(GameManager gameManager) {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }
}
