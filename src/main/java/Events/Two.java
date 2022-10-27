package Events;

import Cards.Card;
import Game.GameManager;
import Player.Player;

import java.util.Random;

public class Two extends CardEvent {

    Two() {
        setName("Two");
    }

    @Override
    public void execute(GameManager gameManager) {
        if (!isValidateCardFrequency(gameManager))
            return;

        removeCards(gameManager);
        drawRandoAndMoveCard(gameManager);
        gameManager.queueNextTurn(false, false);
    }

    private void drawRandoAndMoveCard(GameManager gameManager) {
        Card card = getTarget().getHand().drawRandomCard();
        getTarget().sendMessage("Player " + gameManager.getCurrentPlayer().playerID + " played " + getName()  + " and took a " + card.getName() + " card from your hand!");
        gameManager.getCurrentPlayer().getHand().addCard(card);
        gameManager.getCurrentPlayer().sendMessage("You received a " + card.getName() + " card from player " + getTarget().playerID);
    }

    private boolean isValidateCardFrequency(GameManager gameManager) {
        return (gameManager.getCurrentPlayer().getHand().getCardCountFromCardName(getType()) >= 2);
    }

    private void removeCards(GameManager gameManager) {
        for (int i = 0; i < 2; i++) {
            String type = getType();
            Card c = gameManager.getCurrentPlayer().getHand().getCardFromCardName(type);
            gameManager.getCurrentPlayer().getHand().removeCard(c);
        }
    }
}
