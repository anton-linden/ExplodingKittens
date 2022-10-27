import Deck.Deck;
import Game.GameManager;
import Player.Player;
import Player.PlayerManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTesting {


    @Test
    void getNextPlayer() {
        GameManager gameManager = new GameManager();
        PlayerManager playerManager = new PlayerManager();

        for(int i=0; i < 4; i++) {
            playerManager.addPlayer(new Player(i+1, true, null, null, null)); //add a bot
        }

        gameManager.setPlayerManager(playerManager);

        Player firstPlayer = playerManager.getPlayers().get(0);
        gameManager.setCurrentPlayer(firstPlayer);

        Player player = gameManager.getNextPlayer();
        gameManager.setCurrentPlayer(player);

        if (gameManager.getCurrentPlayer() == firstPlayer) { fail("Still the same player!!"); }

        assertSame(player, gameManager.getCurrentPlayer());
    }


    //Requirement 1
    @Test
    void tooFewPlayers() {
        GameManager gameManager = new GameManager();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameManager.initGame(1, 0);
        });
    }

    //Requirement 1
    @Test
    void tooManyPlayers() {
        GameManager gameManager = new GameManager();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameManager.initGame(6, 0);
        });
    }

    //Requirement 1
    @Test
    void tooManyBots() {
        GameManager gameManager = new GameManager();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameManager.initGame(2, 5);
        });
    }

    //Requirement 1
    @Test
    void onlyBots() {
        GameManager gameManager = new GameManager();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameManager.initGame(0, 3);
        });
    }

    //Requirement 2
    @Test
    void allPlayersHaveADefuseCard() {
        GameManager gameManager = new GameManager();

        gameManager.addCards();

        for (int i = 0; i < 5; i++) {   //Add five players
            gameManager.getPlayerManager().addPlayer(new Player(i, false, null, null, null));
        }

        gameManager.giveStartingCards(gameManager.getPlayerManager().getPlayers());

        Boolean flag = false;

        for (Player player : gameManager.getPlayerManager().getPlayers()) {
            if (!player.getHand().hasCardWithCardName("Defuse")) { flag = true; }
        }

        assertTrue(!flag);
    }

    //Requirement 3
    @Test
    void testBaseGameDeck() {
        GameManager gameManager = new GameManager();
        gameManager.addCards();

        Boolean flag = false;
        Deck deck = gameManager.getPile();

        flag = ((deck.getCardCountFromCardName("Attack") == 4) ? flag : true);
        flag = ((deck.getCardCountFromCardName("Favor") == 4) ? flag : true);
        flag = ((deck.getCardCountFromCardName("Nope") == 5) ? flag : true);
        flag = ((deck.getCardCountFromCardName("Shuffle") == 4) ? flag : true);
        flag = ((deck.getCardCountFromCardName("Skip") == 4) ? flag : true);
        flag = ((deck.getCardCountFromCardName("SeeTheFuture") == 5) ? flag : true);
        flag = ((deck.getCardCountFromCardName("HairyPotatoCat") == 4) ? flag : true);
        flag = ((deck.getCardCountFromCardName("CatterMelon") == 4) ? flag : true);
        flag = ((deck.getCardCountFromCardName("RainbowRalphingCat") == 4) ? flag : true);
        flag = ((deck.getCardCountFromCardName("TacoCat") == 4) ? flag : true);
        flag = ((deck.getCardCountFromCardName("OverweightBikiniCat") == 4) ? flag : true);

        flag = (deck.getSize() == 46) ? flag : true;

        assertFalse(flag);
    }

    //Requirement 5
    @Test
    void allPlayersStartWithSevenCards() {
        Boolean flag = false;

        for (int players = 2; players < 6; players++) {
            GameManager gameManager = new GameManager();

            gameManager.addCards();

            for (int i = 0; i < players; i++) {
                gameManager.getPlayerManager().addPlayer(new Player(i, false, null, null, null));
            }

            gameManager.insertCard("ExplodingKitten", gameManager.getPlayerManager().getPlayers().size()-1, gameManager.getPile());
            gameManager.giveStartingCards(gameManager.getPlayerManager().getPlayers());

            for (Player player : gameManager.getPlayerManager().getPlayers()) {
                flag = (player.getHand().getSize() == 8) ? flag : true;
            }
        }

        assertFalse(flag);
    }

    //Requirement 6
    @Test
    void testAmountOfExplodingKittens() {

        Boolean flag = false;

        for (int players = 2; players < 6; players++) {
            GameManager gameManager = new GameManager();

            for (int i = 0; i < players; i++) {
                gameManager.getPlayerManager().addPlayer(new Player(i, false, null, null, null));
            }

            gameManager.insertCard("ExplodingKitten", gameManager.getPlayerManager().getPlayers().size()-1, gameManager.getPile());

            if (gameManager.getPile().getCardCountFromCardName("ExplodingKitten") != gameManager.getPlayerManager().getPlayers().size() - 1) {
                flag = true;
            }
        }

        assertFalse(flag);
    }
}
