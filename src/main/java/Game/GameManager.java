package Game;

import Cards.Card;
import Cards.CardFactory;
import Deck.Deck;
import Player.Player;
import Player.PlayerManager;
import Server.Server;

import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private final int MIN_AMOUNT_OF_PLAYERS = 2;
    private final int MAX_AMOUNT_OF_PLAYERS = 5;
    private final int AMOUNT_OF_TURNS_EACH_ROUND = 1;   //The amount of base turns


    public Deck pile = new Deck();
    public Deck discard = new Deck();
    private PlayerManager playerManager = new PlayerManager();
    private Player currentPlayer = null;

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
            insertCard("ExplodingKitten", playerManager.getPlayers().size()+20, pile);  //TODO - Reset

            pile.shuffle();

            setStartPlayer();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void giveStartingCards(ArrayList<Player> playerArrayList) {
        CardFactory cardFactory = new CardFactory();

        for(Player player : playerArrayList) {
            player.getHand().addCard(cardFactory.makeCard("Defuse"));
            for(int i=0; i<7; i++) {player.getHand().addCard(pile.drawTopCard());}
        }
    }

    public void insertCard(String cardName, int amount, Deck deck) {
        CardFactory cardFactory = new CardFactory();
        for(int i=0; i < amount; i++) { deck.addCard(cardFactory.makeCard(cardName)); }
    }

    /**
     * Sets a random starting player
     */
    private void setStartPlayer() {
        Random rnd = new Random();
        setCurrentPlayer(playerManager.getPlayers().get(rnd.nextInt(playerManager.getPlayers().size())));
    }

    /**
     * Adds all cards to the main deck.
     */
    public void addCards() {
        CardFactory cardFactory = new CardFactory();

        for(int i=0; i<4; i++) {pile.addCard(cardFactory.makeCard("Attack"));}
        for(int i=0; i<4; i++) {pile.addCard(cardFactory.makeCard("Favor")); }
        for(int i=0; i<5; i++) {pile.addCard(cardFactory.makeCard("Nope"));}
        for(int i=0; i<4; i++) {pile.addCard(cardFactory.makeCard("Shuffle"));}
        for(int i=0; i<4; i++) {pile.addCard(cardFactory.makeCard("Skip"));}
        for(int i=0; i<5; i++) {pile.addCard(cardFactory.makeCard("SeeTheFuture"));}
        for(int i=0; i<4; i++) {pile.addCard(cardFactory.makeCard("HairyPotatoCat"));}
        for(int i=0; i<4; i++) {pile.addCard(cardFactory.makeCard("CatterMelon"));}
        for(int i=0; i<4; i++) {pile.addCard(cardFactory.makeCard("RainbowRalphingCat"));}
        for(int i=0; i<4; i++) {pile.addCard(cardFactory.makeCard("TacoCat"));}
        for(int i=0; i<4; i++) {pile.addCard(cardFactory.makeCard("OverweightBikiniCat"));}
    }

    public void game() {
        int playersLeft = playerManager.getPlayers().size();

        do {

            for(Player p: playerManager.getPlayers()) {
                if(p == currentPlayer)
                    p.sendMessage("It is your turn");
                else
                    p.sendMessage("It is now the turn of player " + currentPlayer.playerID);
            }

            for(int i=0; i<numberOfTurnsToTake; i++) {

                String response = "";
                printTurn(currentPlayer);

                //while (!response.equalsIgnoreCase("pass")) {
                    response = currentPlayer.readMessage(false);


                    if (response.equals("Pass")) { //Draw a card and end turn
                        Card drawCard = pile.drawTopCard();
                        drawCard.drawEffect(this);
                        currentPlayer.getHand().addCard(drawCard);
                        currentPlayer.sendMessage("You drew: " + drawCard.getName());
                    }

                    if (response.contains("Two")) {
                        if (!currentPlayer.getHand().hasCardWithCardName(response)) {
                            currentPlayer.sendMessage("You don't have that card on your hand!");
                            continue;
                        }
                        activateCardAndMoveToDiscardPile(currentPlayer, response);
                    }

                    if (response.contains("Three")) {
                        if (!currentPlayer.getHand().hasCardWithCardName(response)) {
                            currentPlayer.sendMessage("You don't have that card on your hand!");
                            continue;
                        }
                        activateCardAndMoveToDiscardPile(currentPlayer, response);
                    }

                    if (response.contains("Attack")) {
                        if (!currentPlayer.getHand().hasCardWithCardName(response)) {
                            currentPlayer.sendMessage("You don't have that card on your hand!");
                            continue;
                        }
                        activateCardAndMoveToDiscardPile(currentPlayer, response);
                        numberOfTurnsToTake += AMOUNT_OF_TURNS_EACH_ROUND;
                        break;
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
                    //}
                }

                if(i >= (numberOfTurnsToTake-1))
                    numberOfTurnsToTake = AMOUNT_OF_TURNS_EACH_ROUND; //We have served all of our turns, reset it for the next player
            }

/*            do { //next player that is still in the game
                int nextID=((currentPlayer.playerID+1) < playerManager.getPlayers().size()?(currentPlayer.playerID)+1:0);
                currentPlayer = playerManager.getPlayers().get(nextID);
            } while(currentPlayer.exploded && playersLeft>1);*/

            setCurrentPlayer(getNextPlayer());

        } while (playerManager.getPlayers().size() > 1);

        notifyAllPlayersAndSpectatorsOfWinner();

        System.exit(0);
    }

    public Player getWinner() throws Exception {
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
    }

    public void notifyAllPlayersAndSpectatorsOfWinner() {
        String msg = "";

        try {
            msg = "Player " + getWinner().playerID + " has won the game";
        } catch (Exception e) {
            msg = "No winner was found";
        }

        playerManager.sendMessageToAllPlayersAndSpectators(msg);
    }

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
    private void activateCardAndMoveToDiscardPile(Player player, String cardName) {
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

    private void printTurn(Player player) {
        player.sendMessage("\nYou have " + numberOfTurnsToTake + ((numberOfTurnsToTake>1)?" turns":" turn") + " to take");
        printHand(player, player.getHand());

        printOptions(player);
    }

    private void printOptions(Player out) {
        String yourOptions = "You have the following options:\n";

        ArrayList<String> stringArrayList = new ArrayList<>();

        for(Card card : out.getHand().getDeck()) {

            if (card.canBePlayed() && !stringArrayList.contains(card.getName())) {
                stringArrayList.add(card.getName());
            }

            //TODO: Add count! Missing TWO & THREE! See source code!
        }

        for(String str: stringArrayList) {
            yourOptions += "\t" + str + "\n";
        }

        yourOptions += "\tPass\n";

        out.sendMessage(yourOptions);
    }

    private void printHand(Player out, Deck hand) {
        out.sendMessage("Your hand: ");

        for (int i = 0; i < hand.getSize(); i++) {
            out.sendMessage(hand.getDeck().get(i).getName());
        }
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
}
