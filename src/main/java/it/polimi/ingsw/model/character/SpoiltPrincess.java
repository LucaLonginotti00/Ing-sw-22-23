package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.GameState;

/**
 * This class represents the spoilt princess character card.
 */
public class SpoiltPrincess extends CharacterCard {
    private final int ID_code=11;
    private int cost=2;
    private int uses=0;
    private int[] students;
    private int current_player;
    private int chosen_student;

    private String name = "SPOILT PRINCESS";

    /**
     * Constructor of the class.
     * @param princess_drawn The initial array containing 4 students.
     */
    public SpoiltPrincess(int[] princess_drawn){
        this.students = princess_drawn;
    }

    /**
     * This method implements the effect of the spoilt princess card.
     * The spoilt princess starts with 4 students on the card.
     * When the card is used, one of the students can be moved to the board.
     * Then another student is drawn and placed on the card.
     * @param game_state The game state.
     */
    @Override
    public void effect(GameState game_state){
        game_state.getGT().getBoards()[current_player].setOneStudent(chosen_student);
        students[chosen_student]--;
        students[game_state.getGT().drawOne()]++;
        setUses();
    }

    /**
     * this method return the name of the card.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * This method sets the student to be moved from the card to the island.
     * @param chosen_student The student to be moved from the card to the island.
     */
    public void setChosen_student(int chosen_student) {
        this.chosen_student = chosen_student;
    }

    /**
     * This method sets the player that plays the card.
     * @param player The number representing the player that played the card.
     */
    @Override
    public void setCurrent_player(int player) {
        this.current_player = player;
    }

    /**
     * This method is used when a card has been used.
     * It increases the uses and the cost by 1.
     */
    public void setUses() {
        this.uses++;
        setCost(cost+1);
    }

    /**
     * This method returns the cost.
     * @return The cost.
     */
    public int getCost() {
        return cost;
    }

    /**
     * This method sets the cost.
     * @param cost The new cost of the card.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * This method returns the uses of the card.
     * @return The uses of the card.
     */
    public int getUses() {
        return uses;
    }

    /**
     * This method returns the ID code of the card.
     * @return The ID code of the card.
     */
    public int getID_code() {
        return ID_code;
    }

    /**
     * This method returns the array of students on the card.
     * @return The array of students on the card.
     */
    @Override
    public int[] getStudents() {
        return students;
    }
}
