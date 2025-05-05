package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This abstract class represents the character cards.
 * There are 12 character cards, all of them with different effect and some with different costs.
 * At the beginning of the game 3 cards are drawn and put in the game table. Every time a card is used,
 * its cost is increased by 1.
 * Each card has a unique ID code from 1 to 12.
 */
public abstract class CharacterCard implements Serializable {
     int ID_code;
     int cost;
     int uses = 0;
     int[] students;
     String name;

     /**
      * This method is the general method to represent the different effects of all the cards.
      * This method is overwritten by all the cards.
      * @param game_state The game state.
      */
     public void effect(GameState game_state){}

     /**
      * This method is used when a card has been used.
      * It increases the uses and the cost by 1.
      */
     public void setUses() {
          this.uses++;
          setCost(cost+1);
     }

     /**
      * this method return the name of the card.
      */
     public String getName() {
          return name;
     }

     /**
      * This method sets the student to be moved from the card to the island.
      * @param chosen_student The student to be moved from the card to the island.
      */
     public void setChosen_student(int chosen_student){}

     /**
      * This method sets the index of the island into which the prohibition card will be moved to.
      * @param index_to The index of the island into which the prohibition card will be moved to.
      */
     public void setIndex_to(int index_to){}

     /**
      * This method sets the player that plays the card.
      * @param player The number representing the player that played the card.
      */
     public void setCurrent_player(int player){}

     /**
      * This method returns the array of students on the card.
      * @return The array of students on the card.
      */
     public int[] getStudents(){return students; }

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
      * This method return the ID code of the card.
      * @return The ID code of the card.
      */
     public int getID_code() {
          return ID_code;
     }
}