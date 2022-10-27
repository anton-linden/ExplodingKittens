package Deck;

import Cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Deck {

    private ArrayList<Card> deck = new ArrayList<>();

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

    /**
     * Gets a list of cards that has a specific frequency.
     * @param min_frequency minimum occurrence
     * @param max_frequency maximum occurrence
     * @return A list of card objects.
     */
    public ArrayList<Card> getCardsWithFrequency(int min_frequency, int max_frequency) {
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<String> checkedCards = new ArrayList<>();

        for (Card card : getDeck()) {
            if (!checkedCards.contains(card.getName())) {
                checkedCards.add(card.getName());

                if (getCardCountFromCardName(card.getName()) >= min_frequency && getCardCountFromCardName(card.getName()) < max_frequency)
                    cards.add(card);
            }
        }
        return cards;
    }

    /**
     * Checks if the given card name exists in the deck.
     * @param cardName name of card to check for.
     * @return true/false if it exists or not.
     */
    public boolean hasCardWithCardName(String cardName) {
        for (Card card : deck) {
            if (card.getName().equals(cardName)) { return true; }
        } return false;
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

    /**
     * Draws the card at a specific index. (Drawing a card will remove it from deck)
     * @param index specific index of where to draw the card from
     * @return the drawn card
     */
    public Card drawCardAtIndex(int index) {
        if (deck.size() < 1) { return null; }
        return deck.remove(index);
    }

    /**
     * Adds a specific card object at a specific index.
     * @param card object of a card instance.
     * @param index specific index to insert the card at. If too small or too big, it gets inserted to the top of the deck.
     */
    public void addCardAtIndex(Card card, int index) {
        if (index > deck.size() || index < 0) { index = 0; }
        deck.add(index, card);
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    public Boolean containsCard(Card card) {
        return deck.contains(card);
    }

    public void removeCard(Card card) {
        deck.remove(card);
    }

    public Card drawTopCard() {
        return drawCardAtIndex(0);
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public int getSize() {
        return deck.size();
    }

    public void clearAll() {
        deck = new ArrayList<Card>();
    }

    public Card drawRandomCard() {
        return drawCardAtIndex(new Random().nextInt(getDeck().size()));
    }
}
