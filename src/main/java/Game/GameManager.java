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


    public Deck pile = new Deck();
    public Deck discard = new Deck();
    private PlayerManager playerManager = new PlayerManager();
    private Player currentPlayer = null;
    private Queue<Event> eventQueue = new LinkedList<>();
    private EventFactory eventFactory = new EventFactory();
    private CardFactory cardFactory = new CardFactory();
    public int numberOfTurnsToTake = AMOUNT_OF_TURNS_EACH_ROUND; //attacked?

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

    public void game() {
        while(true) {
            if (getEventQueue().size() > 0)
                getNextEvent().execute(this);
        }
    }


    public void giveStartingCards(ArrayList<Player> playerArrayList) {
        for(Player player : playerArrayList) {
            player.getHand().addCard(getCardFactory().makeCard("Defuse"));
            for(int i=0; i<7; i++) {player.getHand().addCard(pile.drawTopCard());}
        }
    }

    /**
     * Sets a random starting player
     */
    private void setStartPlayer() {
        Random rnd = new Random();
        setCurrentPlayer(playerManager.getPlayers().get(rnd.nextInt(playerManager.getPlayers().size())));
        getEventQueue().add(eventFactory.makeEvent("PlayerTurn"));
    }

    public void queueNextTurn(boolean forceChangePlayer) {

        if (forceChangePlayer) {
            setCurrentPlayer(getNextPlayer());
            getEventQueue().add(eventFactory.makeEvent("PlayerTurn"));
            return;
        }

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
     * Adds all cards to the main deck.
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

    public Event getNextEvent() {
        return getEventQueue().remove();
    }

    public Queue<Event> getEventQueue() {
        return eventQueue;
    }

    /*public void game() {
        int playersLeft = playerManager.getPlayers().size();

        do {

            for(Player p: playerManager.getPlayers()) {
                if(p == currentPlayer)
                    p.sendMessage("It is your turn");
                else
                    p.sendMessage("It is now the turn of player " + currentPlayer.playerID);
            }

            for(int i=0; i<numberOfTurnsToTake; i++) {

                printTurn(currentPlayer);
                String response = currentPlayer.readMessage(false);

                if (response.equals("Pass")) { //Draw a card and end turn
                    Card drawCard = pile.drawTopCard();
                    drawCard.drawEffect(this);
                    currentPlayer.getHand().addCard(drawCard);
                    currentPlayer.sendMessage("You drew: " + drawCard.getName());
                }

                if (response.contains("Two")) {
                    String[] args = response.split(" ");
                    if (!(currentPlayer.getHand().getCardCountFromCardName(args[1]) >= 2)) {
                        currentPlayer.sendMessage("You don't have enough of that card on your hand!");
                        continue;
                    }

                    activateCardAndMoveToDiscardPile(currentPlayer, args[1]);
                    activateCardAndMoveToDiscardPile(currentPlayer, args[1]);
                }

                if (response.contains("Three")) {
                    if (!(currentPlayer.getHand().getCardCountFromCardName(response) >= 3)) {
                        currentPlayer.sendMessage("You don't have enough of that card on your hand!");
                        continue;
                    }
                    activateCardAndMoveToDiscardPile(currentPlayer, response);
                }

                if (response.contains("Attack")) {
                    //Call factory
                    Event event = eventFactory.makeEvent("AttackPlayer");
                    event.execute(this);
                }

                if (response.contains("Favor")) {
                    if (!currentPlayer.getHand().hasCardWithCardName(response)) {
                        currentPlayer.sendMessage("You don't have that card on your hand!");
                        continue;
                    }
                    activateCardAndMoveToDiscardPile(currentPlayer, response);
                }

                if (response.contains("Shuffle")) {
                    if (!currentPlayer.getHand().hasCardWithCardName(response)) {
                        currentPlayer.sendMessage("You don't have that card on your hand!");
                        continue;
                    }
                    activateCardAndMoveToDiscardPile(currentPlayer, response);
                    i++;
                }

                if (response.contains("Skip")) {
                    if (!currentPlayer.getHand().hasCardWithCardName(response)) {
                        currentPlayer.sendMessage("You don't have that card on your hand!");
                        continue;
                    }
                    activateCardAndMoveToDiscardPile(currentPlayer, response);
                    i++;
                }

                if (response.contains("SeeTheFuture")) {
                    if (!currentPlayer.getHand().hasCardWithCardName(response)) {
                        currentPlayer.sendMessage("You don't have that card on your hand!");
                        continue;
                    }
                    activateCardAndMoveToDiscardPile(currentPlayer, response);
                    i++;
                }

                if(i >= (numberOfTurnsToTake-1))
                    numberOfTurnsToTake = AMOUNT_OF_TURNS_EACH_ROUND; //We have served all of our turns, reset it for the next player
            }

            setCurrentPlayer(getNextPlayer());

        } while (playerManager.getPlayers().size() > 1);

        notifyAllPlayersAndSpectatorsOfWinner();
        System.exit(0);
    }*/

/*    public Player getWinner() throws Exception {
        Player winner = null;
        int count = 0;

        for(Player player: getPlayerManager().getPlayers()) {
            if (!player.exploded) {
                count++;
                winner = player;
            }
        }

        if (count > 1)
            throw new Exception("Too many winners! There should only be one!");


        return winner;
    }*/

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

    public Deck getPile() {
        return pile;
    }

    public void setPile(Deck pile) {
        this.pile = pile;
    }

    public Deck getDiscard() {
        return discard;
    }

    public void setDiscard(Deck discard) {
        this.discard = discard;
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

    public void insertCard(String cardName, int amount, Deck deck) {
        for(int i=0; i < amount; i++) { deck.addCard(getCardFactory().makeCard(cardName)); }
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
}
