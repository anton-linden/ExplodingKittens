import Cards.Card;
import Cards.CardFactory;
import Deck.Deck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTesting {

    //TODO: Check amount of cards in pile
    //TODO: Check starting hand is 7

    @Test
    void addACard() {
        Deck deck = new Deck();
        CardFactory cardFactory = new CardFactory();
        deck.addCard(cardFactory.makeCard("ExplodingKitten"));
        assertEquals(1, deck.getSize());
    }

    @Test
    void removeACard() {
        Deck deck = new Deck();
        CardFactory cardFactory = new CardFactory();

        Card card = cardFactory.makeCard("ExplodingKitten");

        deck.addCard(card);
        deck.addCard(cardFactory.makeCard("Nope"));

        deck.removeCard(card);

        assertEquals(1, deck.getSize());
    }

    @Test
    void clearAllCards() {
        Deck deck = new Deck();
        CardFactory cardFactory = new CardFactory();

        deck.addCard(cardFactory.makeCard("ExplodingKitten"));
        deck.addCard(cardFactory.makeCard("Nope"));

        deck.clearAll();

        assertEquals(0, deck.getSize());
    }

    @Test
    void drawTopCard() {
        Deck deck = new Deck();
        CardFactory cardFactory = new CardFactory();

        Card card = cardFactory.makeCard("ExplodingKitten");
        deck.addCard(card); //By keeping the specific card as a variable we can see that it's actually that card and not one identical.

        for (int i = 0; i < 10; i++) {
            deck.addCard(cardFactory.makeCard("ExplodingKitten"));
        }

        assertEquals(card, deck.drawTopCard());
    }

    @Test
    void drawCardAtIndex() {
        Deck deck = new Deck();
        CardFactory cardFactory = new CardFactory();

        for (int i = 0; i < 5; i++) {
            deck.addCard(cardFactory.makeCard("ExplodingKitten"));
        }

        Card card = cardFactory.makeCard("ExplodingKitten");
        deck.addCard(card); //By keeping the specific card as a variable we can see that it's actually that card and not one identical.

        for (int i = 0; i < 5; i++) {
            deck.addCard(cardFactory.makeCard("ExplodingKitten"));
        }

        assertEquals(card, deck.drawCardAtIndex(5));
    }

    @Test
    void containsCard() {
        Deck deck = new Deck();
        CardFactory cardFactory = new CardFactory();

        Card card = cardFactory.makeCard("ExplodingKitten");
        deck.addCard(card); //By keeping the specific card as a variable we can see that it's actually that card and not one identical.

        for (int i = 0; i < 10; i++) {
            deck.addCard(cardFactory.makeCard("ExplodingKitten"));
        }

        assertEquals(true, deck.containsCard(card));
    }

    @Test
    void addDeckToDeck() {
        Deck mainDeck = new Deck();
        CardFactory cardFactory = new CardFactory();

        int length = 10;

        for (int i = 0; i < length / 2; i++) {
            mainDeck.addCard(cardFactory.makeCard("ExplodingKitten"));
        }

        Deck deckToAdd = new Deck();

        for (int i = 0; i < length / 2; i++) {
            deckToAdd.addCard(cardFactory.makeCard("Nope"));
        }

        mainDeck.addDeck(deckToAdd);

        assertEquals(10, mainDeck.getSize());

    }

    @Test
    void sortDeck() {
        Deck deck = new Deck();
        CardFactory cardFactory = new CardFactory();

        Card card = cardFactory.makeCard("Attack");
        deck.addCard(cardFactory.makeCard("Defuse"));
        deck.addCard(cardFactory.makeCard("CatterMelon"));
        deck.addCard(card);

        deck.sortDeck();

        assertEquals(card, deck.drawTopCard());
    }

    @Test
    void getCardFromCardName() {
        Deck deck = new Deck();
        CardFactory cardFactory = new CardFactory();

        Card card = cardFactory.makeCard("Attack");
        deck.addCard(cardFactory.makeCard("Defuse"));
        deck.addCard(cardFactory.makeCard("CatterMelon"));
        deck.addCard(card);

        assertEquals(card, deck.getCardFromCardName("Attack"));
    }

    @Test
    void getCardFromCardNameInvalidName() {
        Deck deck = new Deck();
        CardFactory cardFactory = new CardFactory();

        Card card = cardFactory.makeCard("Attack");
        deck.addCard(cardFactory.makeCard("Defuse"));
        deck.addCard(cardFactory.makeCard("CatterMelon"));
        deck.addCard(card);

        assertEquals(null, deck.getCardFromCardName("not a valid card name"));
    }

}
