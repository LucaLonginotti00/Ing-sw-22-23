package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represent the player.
 * Each player has a player ID, from 1 to 4 and a deck containing the 10 assistance card.
 * A wizard and a tower colour is assigned to each player at the beginning.
 * Each player starts with 1 coin if the expert mode is enabled.
 */
public class Player implements Serializable {
    private final String nickname;
    private final int tower_colour;
    private final int wizard;
    private int coin;
    private final int player_ID;
    private AssistanceCard chosen_card;
    private int moves;
    private final Deck deck;
    private boolean active;
    private boolean chosen=false;

    /**
     * Constructor of the class
     * @param nickname The string containing the name of the player.
     * @param wizard The number of wizard assigned to the player (from 1 to 3).
     * @param tower_colour The colour of the tower assigned to the player. It gets translated to a number from 0 to 2 based on the colour. More info here: {@link AssistanceCard}.
     * @param player_ID Player ID assigned to the player (from 1 to 4).
     */
    public Player(String nickname, int wizard, TowerColour tower_colour, int player_ID) {
        this.coin = 1;
        this.nickname = nickname;
        this.wizard = wizard;
        this.tower_colour = tower_colour.getTower_translate();
        this.player_ID = player_ID;
        deck = new Deck();
    }

    /**
     * This method returns the player ID.
     * @return The player ID.
     */
    public int getPlayer_ID() {
        return player_ID;
    }

    /**
     * This method returns the player's coins.
     * @return The player's coins.
     */
    public int getCoin() {
        return coin;
    }

    /**
     * This method returns the tower colour.
     * @return The tower colour.
     */
    public int getTower_colour() {
        return tower_colour;
    }

    /**
     * This method returns the name of the player.
     * @return The name of the player.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method returns the wizard assigned to the player.
     * @return The wizard assigned to the player.
     */
    public int getWizard() {
        return wizard;
    }

    /**
     * This method sets the coins.
     * @param coin The new number of coins.
     */
    public void setCoin(int coin) {
        this.coin = coin;
    }

    /**
     * This method returns the chosen assistance card to be played.
     * @return The chosen assistance card to be played.
     */
    public AssistanceCard getChosen_card() {
        return chosen_card;
    }

    /**
     * This method sets the chosen assistance card to be played.
     * @param chosen_card The chosen assistance card to be played.
     */
    public void setChosen_card(AssistanceCard chosen_card) {
        this.chosen_card = chosen_card;
    }

    /**
     * This method returns the deck of assistance cards of the player.
     * @return The deck of assistance cards of the player.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * This method returns the moves of mother nature that can be done in the turn.
     * @return The moves of mother nature that can be done in the turn.
     */
    public int getMoves() {
        return moves;
    }

    /**
     * This method returns the moves of mother nature that can be done in the turn.
     * @param moves The moves of mother nature that can be done in the turn.
     */
    public void setMoves(int moves) {
        this.moves = moves;
    }

    /**
     * This method checks if player and given object are the same.
     * @param o Given object.
     * @return {@code True} if objects are the same, {@code False} if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return tower_colour == player.tower_colour && wizard == player.wizard && coin == player.coin && player_ID == player.player_ID && Objects.equals(nickname, player.nickname) & chosen_card == player.chosen_card  && Objects.equals(deck, player.deck);
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public boolean isChosen() {
        return chosen;
    }
}
