package Events;

import Game.GameManager;

public abstract class Event {

    private String name = "Event";

    public void execute(GameManager gameManager) {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
