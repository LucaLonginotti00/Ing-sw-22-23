package it.polimi.ingsw.model;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the class {@link it.polimi.ingsw.model.Deck}.
 */
class DeckTest {

    private List<AssistanceCard> cards;

    @Test
    public void testCount(){
        Deck deck = new Deck();
        assertEquals(10,10);
    }


    @Test
    void testRemove_used_card() {
        Deck deck = new Deck();
        deck.remove_used_card(AssistanceCard.CAT);
        assertEquals(9,deck.count_elements());
    }

    @Test
    void testGetCards() {
        Deck deck = new Deck();
        cards = new ArrayList<AssistanceCard>(Arrays.asList(
                AssistanceCard.TORTOISE,
                AssistanceCard.ELEPHANT,
                AssistanceCard.BULLDOG,
                AssistanceCard.OCTOPUS,
                AssistanceCard.LIZARD,
                AssistanceCard.FOX,
                AssistanceCard.EAGLE,
                AssistanceCard.CAT,
                AssistanceCard.OSTRICH,
                AssistanceCard.LION));
     assertEquals(cards,deck.getCards());
    }

}