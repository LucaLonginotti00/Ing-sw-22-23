package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class represent the deck of assistance cards. 1 deck of 10 assistance cards for each player.
 * The cards are represented in a list.
 */
public class Deck implements Serializable {
    private List<AssistanceCard> cards;

    /**
     * Constructor of the class that creates a list of 10 assistance cards.
     */
    public Deck(){
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
    }

    /**
     * This method removes the used assistance card from the list.
     * @param chosen_remove The assistance card chosen to be played and removed.
     */
    public void remove_used_card(AssistanceCard chosen_remove){
        cards.remove(chosen_remove);
    }

    /**
     * This method returns the number of assistance card left on the deck.
     * @return The number of assistance card left on the deck.
     */
    public int count_elements(){
        return cards.size();
    }

    /**
     * This method checks if a card in the list and given object are the same.
     * @param o Given object.
     * @return {@code True} if objects are the same, {@code False} if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Objects.equals(cards, deck.cards);
    }

    /**
     * This method returns the list of assistance cards.
     * @return The list of assistance cards.
     */
    public List<AssistanceCard> getCards() {
        return cards;
    }
}
