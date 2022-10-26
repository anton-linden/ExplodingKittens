import Cards.Card;
import Cards.CardFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTesting {

    @Test
    void FactoryClassDoesntExist() {
        CardFactory cardFactory = new CardFactory();
        Card c = cardFactory.makeCard("incorrect class name");
        assertEquals(c, null);
    }

    @Test
    void FactoryCreateBasicCard() {
        CardFactory cardFactory = new CardFactory();
        Card c = cardFactory.makeCard("Nope");
        assertEquals(c.getName(), "Nope");
    }

    @Test
    void FactoryCreatingTheAbstractClass() {
        CardFactory cardFactory = new CardFactory();
        Card c = cardFactory.makeCard("Card");
        assertEquals(c, null);
    }

}
