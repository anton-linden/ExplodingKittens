package Deck;

import Cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Deck {

    public ArrayList<Card> deck = new ArrayList<>();

    public void addCard(Card card) {
        deck.add(card);
    }

    public Boolean containsCard(Card card) {
        return deck.contains(card);
    }

    /**
     * Get a card from its card name.
     * @param cardName name of a card.
     * @return returns null or the first found valid card.
     */
    public Card getCardFromCardName(String cardName) {
        for (Card card : deck) {
            if (card.getName().equals(cardName)) { return card; }
        } return null;
    }

    /**
     * Gets the amount of the same card in the deck.
     * @param cardName the name of the card to get the count of.
     * @return the amount of cards with the name of the input.
     */
    public int getCardCountFromCardName(String cardName) {
        int count = 0;

        for (Card card : deck) {
            if (card.getName().equals(cardName)) { count++; }
        }

        return count;
    }

    public Boolean hasCardWithCardName(String cardName) {
        for (Card card : deck) {
            if (card.getName().equals(cardName)) { return true; }
        } return false;
    }

    public Card drawTopCard() {
        return drawCardAtIndex(0);
    }
    public Card drawCardAtIndex(int index) {
        if (deck.size() < 1) { return null; }
        return deck.remove(index);
    }

    public void removeCard(Card card) {
        deck.remove(card);
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public int getSize() {
        return deck.size();
    }

    /**
     * Method to sorting an object arraylist by name.
     * Source: https://www.codebyamir.com/blog/sort-list-of-objects-by-field-java
     */
    public void sortDeck() {
        Collections.sort(deck, new Comparator<Card>() {
            @Override
            public int compare(Card u1, Card u2) {
                return u1.getName().compareTo(u2.getName());
            }
        });
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * Adds all cards in one deck to another deck.
     * @param _deck is a Deck to be added into the deck.
     */
    public void addDeck(Deck _deck) {
        int length = _deck.getSize();
        for (int i = 0; i < length; i++) {
            deck.add(_deck.drawTopCard());
        }
    }

    public void clearAll() {
        deck = new ArrayList<Card>();
    }

}
