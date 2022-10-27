package Game;

import Cards.Card;
import Cards.CardFactory;
import Deck.Deck;
import Events.Event;
import Events.EventFactory;
import Player.Player;
import Player.PlayerManager;
import Server.Server;
import java.util.*;

public class GameManager {
    private final int MIN_AMOUNT_OF_PLAYERS = 2;
    private final int MAX_AMOUNT_OF_PLAYERS = 5;
    private final int AMOUNT_OF_TURNS_EACH_ROUND = 1;   //The amount of base turns

    private Deck pile = new Deck();
    private Deck discard = new Deck();
    private PlayerManager playerManager = new PlayerManager();
    private Player currentPlayer = null;
    private Queue<Event> eventQueue = new LinkedList<>();
    private EventFactory eventFactory = new EventFactory();
    private CardFactory cardFactory = new CardFactory();
    private int numberOfTurnsToTake = AMOUNT_OF_TURNS_EACH_ROUND;

    /**
     * Validate an amount of players that the game shall be initiated with. Min 2 players, max 5 players.
     * @param numPlayers Amount of players, minimum 1.
     * @param numBots Amount of bots.
     * @return
     */
    public Boolean validateNumbersOfPlayers(int numPlayers, int numBots) {
        if (numPlayers == 0)
            return false;

        int total = numBots + numPlayers;

        if (total >= MIN_AMOUNT_OF_PLAYERS && total <= MAX_AMOUNT_OF_PLAYERS) {
            return true;
        } return false;
    }

    /**
     * Inits the game. Throws IllegalArgumentException if the player count is invalid. There needs to be at least 2 players/bots.
     * @param numPlayers the amount of real players that should play the game, minimum 1.
     * @param numBots the amount of bots that should be added to the game
     */
    public void initGame(int numPlayers, int numBots) throws Exception {

        if (!validateNumbersOfPlayers(numPlayers, numBots))
            throw new IllegalArgumentException("Illegal amount of players/bots");

        try {
            Server server = new Server(playerManager, numPlayers, numBots);

            addCards();
            pile.shuffle();

            giveStartingCards(playerManager.getPlayers());
            insertCard("ExplodingKitten", playerManager.getPlayers().size()-1, pile);

            pile.shuffle();

            setStartPlayer();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Runs the game. Takes a new event from the event queue that is a FIFO-queue and then executes it's instructions.
     */
    public void game() {
        while(true) {
            if (getEventQueue().size() > 0)
                getNextEvent().execute(this);
        }
    }


    /**
     * Gives all players the right amount of starting cards each drawn for the pile and gives defuse cards.
     * @param playerArrayList the players that shall get the cards
     */
    public void giveStartingCards(ArrayList<Player> playerArrayList) {
        for(Player player : playerArrayList) {
            player.getHand().addCard(getCardFactory().makeCard("Defuse"));
            for(int i=0; i<7; i++) {player.getHand().addCard(pile.drawTopCard());}
        }
    }

    /**
     * Sets a random starting player & and queues the first event. (Starting the game)
     */
    private void setStartPlayer() {
        Random rnd = new Random();
        setCurrentPlayer(playerManager.getPlayers().get(rnd.nextInt(playerManager.getPlayers().size())));
        getEventQueue().add(eventFactory.makeEvent("PlayerTurn"));
    }

    /**
     * Queues the next turn.
     * @param forceChangePlayer if a event want's to force the next player do play next, this variable will force a current player variable change.
     * @param reduceTurns determines if the amount of turns shall decrease or stay the same.
     */
    public void queueNextTurn(boolean forceChangePlayer, boolean reduceTurns) {

        if (forceChangePlayer) {
            setCurrentPlayer(getNextPlayer());
            getEventQueue().add(eventFactory.makeEvent("PlayerTurn"));
            return;
        }

        if (reduceTurns)
            setNumberOfTurnsToTake(getNumberOfTurnsToTake()-1);

        if (getNumberOfTurnsToTake() > 0) {
            getEventQueue().add(eventFactory.makeEvent("PlayerTurn"));
            return;
        }

        setNumberOfTurnsToTake(getAMOUNT_OF_TURNS_EACH_ROUND());
        setCurrentPlayer(getNextPlayer());
        getEventQueue().add(eventFactory.makeEvent("PlayerTurn"));
    }

    /**
     * Adds all cards in the basic game mode to the main deck called pile.
     */
    public void addCards() {
        for(int i=0; i<4; i++) {pile.addCard(getCardFactory().makeCard("Attack"));}
        for(int i=0; i<4; i++) {pile.addCard(getCardFactory().makeCard("Favor")); }
        for(int i=0; i<5; i++) {pile.addCard(getCardFactory().makeCard("Nope"));}
        for(int i=0; i<4; i++) {pile.addCard(getCardFactory().makeCard("Shuffle"));}
        for(int i=0; i<4; i++) {pile.addCard(getCardFactory().makeCard("Skip"));}
        for(int i=0; i<5; i++) {pile.addCard(getCardFactory().makeCard("SeeTheFuture"));}
        for(int i=0; i<4; i++) {pile.addCard(getCardFactory().makeCard("HairyPotatoCat"));}
        for(int i=0; i<4; i++) {pile.addCard(getCardFactory().makeCard("CatterMelon"));}
        for(int i=0; i<4; i++) {pile.addCard(getCardFactory().makeCard("RainbowRalphingCat"));}
        for(int i=0; i<4; i++) {pile.addCard(getCardFactory().makeCard("TacoCat"));}
        for(int i=0; i<4; i++) {pile.addCard(getCardFactory().makeCard("OverweightBikiniCat"));}
    }

    /**
     * Gets the next player that's next in line to play.
     * @return the next player
     */
    public Player getNextPlayer() {
        return ((playerManager.getPlayers().indexOf(getCurrentPlayer()) + 1) == playerManager.getPlayers().size())
                ? playerManager.getPlayers().get(0) //Return first
                : playerManager.getPlayers().get(playerManager.getPlayers().indexOf(getCurrentPlayer()) + 1);    //Return next element
    }

    /**
     * Calls execute on a specific player's card. Then removes the card from the player hand and places it in the discard pile.
     * @param player The player that played the card.
     * @param cardName The name of the card that was played by the player.
     */
    public void activateCardAndMoveToDiscardPile(Player player, String cardName) {
        Card card = player.getHand().getCardFromCardName(cardName);

        if (card == null)
            return;

        card.playCard(this);
        removeCardFromPlayerAddToDiscardPile(player, card);
    }

    /**
     * Removes a specific card from a specific player.
     * @param player The player to remove the selected card from.
     * @param card The card to remove from the selected player.
     */
    private void removeCardFromPlayerAddToDiscardPile(Player player, Card card) {
        discard.addCard(card);
        player.getHand().removeCard(card);
    }

    /**
     * Creates and inserts a card from only a name into a deck using the card factory.
     * @param cardName name of the card to insert
     * @param amount amount of cards of the given type to add
     * @param deck the deck that shall have the new cards
     */
    public void insertCard(String cardName, int amount, Deck deck) {
        for(int i=0; i < amount; i++) { deck.addCard(getCardFactory().makeCard(cardName)); }
    }

    public Deck getPile() {
        return pile;
    }

    public Deck getDiscard() {
        return discard;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public int getAMOUNT_OF_TURNS_EACH_ROUND() {
        return AMOUNT_OF_TURNS_EACH_ROUND;
    }

    public EventFactory getEventFactory() {
        return eventFactory;
    }

    public int getNumberOfTurnsToTake() {
        return numberOfTurnsToTake;
    }

    public void setNumberOfTurnsToTake(int numberOfTurnsToTake) {
        this.numberOfTurnsToTake = numberOfTurnsToTake;
    }

    public CardFactory getCardFactory() {
        return cardFactory;
    }

    public Event getNextEvent() {
        return getEventQueue().remove();
    }

    public Queue<Event> getEventQueue() {
        return eventQueue;
    }
}
