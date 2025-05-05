package it.polimi.ingsw.model;


import java.io.Serializable;

/**
 * This class represent the islands.
 * Each island has an array of int used to keep track of the students and their type.
 * Pos. 0 represent the yellow... More info at {@link DiskColour}.
 * One island has mother nature set at the beginning of the game and then moved with a method on {@link GameTable}.
 * mother nature is represented by a boolean: {@code True} if is on the island, {@code False} if not on the island.
 * Each island can have at max 1 tower (more than 1 if merged). The tower can be placed by the player that control the island.
 */
public class Island implements Serializable {

    private Board[] boards;
    private boolean mother_nature;
    private int tower;
    private int influence_controller;
    private int player_controller;
    private int island_ID;
    private int[] arr_students;
    private boolean prohibition_card;
    private boolean include_towers;
    private boolean prohibition_colour;
    private int proh_col;
    private int extra_influence;

    /**
     * Constructor of the class.
     * @param boards Reference to the boards of the players.
     * @param island_ID the starting ID of the island, it's not very useful.
     * @param tower_colour Puts tower starter to the island, that means no tower (see: {@link TowerColour}).
     */
    public Island( Board[] boards, int island_ID, TowerColour tower_colour) {
        this.island_ID = island_ID;
        this.boards = boards;
        this.tower = tower_colour.getTower_translate();
        influence_controller = 0;
        arr_students = new int[5];
        mother_nature = false;
        player_controller=-1;
        prohibition_card = false;
        prohibition_colour=false;
        proh_col=0;
        include_towers=true;
        extra_influence=0;
    }

    /**
     * This method calculate the influence of the player on the island.
     * The player with the highest influence becomes the controller and can place a tower if mother nature is on that island.
     * @param current_player The current player of the turn.
     * @param Boards The array of the boards of the players.
     * @return {@code False} if the control of the island is changed, {@code True} otherwise.
     */
    public boolean calculate_influence(int current_player,Board[] Boards) {
        boolean same_player=true;
        int temp_influence = 0;
        if(!prohibition_card){
            for (int i = 0; i<5;i++) {
                if (boards[current_player].getArrProfessors()[i]) {
                    if(prohibition_colour){
                        if(i!=proh_col){
                            temp_influence+= arr_students[i];
                        }
                    }
                    else{
                        temp_influence+= arr_students[i];
                    }
                }
            }

            if(include_towers){
                if(current_player==player_controller){
                    temp_influence+=tower;
                }
                if (temp_influence > influence_controller) {
                    if(current_player!=player_controller){
                        if(boards.length==4){
                            if(current_player==1 || current_player==2){
                                Boards[1].setN_towers(Boards[1].getN_towers()+tower);
                                player_controller = current_player;
                                tower = 1;
                                influence_controller = temp_influence;
                                same_player=false;
                            }
                            else if(current_player==3 || current_player==4){
                                Boards[3].setN_towers(Boards[3].getN_towers()+tower);
                                player_controller = current_player;
                                tower = 1;
                                influence_controller = temp_influence;
                                same_player=false;
                            }
                        }
                        else{
                            if(player_controller!=-1){
                                Boards[player_controller].setN_towers(Boards[player_controller].getN_towers()+tower);
                            }
                            player_controller = current_player;
                            tower = 1;
                            influence_controller = temp_influence+1+extra_influence;
                            same_player=false;
                        }

                    }
                    else {
                        influence_controller = temp_influence+extra_influence;
                    }
                }
            }
            else{
                if (temp_influence > influence_controller-tower) {
                    if(current_player!=player_controller){
                        if(boards.length==4){
                            if(current_player==1 || current_player==2){
                                Boards[1].setN_towers(Boards[1].getN_towers()+tower);
                                player_controller = current_player;
                                tower = 1;
                                influence_controller = temp_influence;
                                same_player=false;
                            }
                            else if(current_player==3 || current_player==4){
                                Boards[3].setN_towers(Boards[3].getN_towers()+tower);
                                player_controller = current_player;
                                tower = 1;
                                influence_controller = temp_influence;
                                same_player=false;
                            }
                        }
                        else{
                            if(player_controller!=-1){
                                Boards[player_controller].setN_towers(Boards[player_controller].getN_towers()+tower);
                            }
                            player_controller = current_player;
                            tower = 1;
                            influence_controller = temp_influence+extra_influence;
                            same_player=false;
                        }

                    }
                    else {
                        influence_controller = temp_influence+extra_influence;
                    }
                }

            }




        }
        else{
            setProhibition_card(false);
        }
        if(prohibition_colour){
            setProhibition_colour(false);
        }

        return same_player;
    }


    /**
     * This method can be used to add students to the island.
     * @param transfer The list of students to be added to the island.
     */
    public void add_students(Student transfer) {
            arr_students[transfer.getColour()]++;
    }


    /**
     * This method returns the player that controls the island.
     * @return The player that controls the island.
     */
    public int check_controller() { return player_controller;}

    /**
     * This method can be used to add a tower to the island.
     * @param current_player The current player of the turn.
     */
    public void add_tower(int current_player) {
        this.tower = boards[current_player].getTower();
    }

    /**
     * This method returns the boards of the players
     * @return The boards of the players.
     */
    public Board[] getBoards() {
        return boards;
    }

    /**
     * This method checks if mother nature is on the island.
     * @return {@code True} if mother nature is on the island, {@code False} if not.
     */
    public boolean isMother_nature() {
        return mother_nature;
    }

    /**
     * This method returns the int that represent the colour of the tower on the island.
     * @return The int that represent the colour of the tower on the island.
     */
    public int getTower() {
        return tower;
    }
    /**
     * This method returns the influence of the player that controls the island.
     * @return The influence of the player that controls the island.
     */
    public int getInfluence_controller() {
        return influence_controller;
    }

    /**
     * This method returns the int that represent the player that controls the island.
     * @return The int that represent the player that controls the island.
     */
    public int getPlayer_controller() {
        return player_controller;
    }

    /**
     * This method returns the island ID.
     * @return The island ID.
     */
    public int getIsland_ID() {
        return island_ID;
    }

    /**
     * This method returns the students on the island
     * @return The array of students on the island
     */
    public int[] getArr_students() {
        return arr_students;
    }

    /**
     * This method sets mother nature on the island, {@code True} if is on the island, {@code False} if not on the island.
     * @param mother_nature {@code True} if is on the island, {@code False} if not on the island.
     */
    public void setMother_nature(boolean mother_nature) {
        this.mother_nature = mother_nature;
    }

    /**
     * This method sets the tower on the island.
     * @param tower The int that represent the tower to be placed on the island.
     */
    public void setTower(int tower) {
        this.tower = tower;
    }

    /**
     * This method sets the highest influence on the island.
     * @param influence_controller The number representing the highest influence on the island.
     */
    public void setInfluence_controller(int influence_controller) {
        this.influence_controller = influence_controller;
    }

    /**
     * This method sets the player that has the highest influence.
     * @param player_controller The player ID that has the highest influence.
     */
    public void setPlayer_controller(int player_controller) {
        this.player_controller = player_controller;
    }

    /**
     * This method sets the students on the island.
     * @param arr_students The array students to be set on the island.
     */
    public void setArr_students(int[] arr_students) {
        this.arr_students = arr_students;
    }

    /**
     * This method increments the array of student given the index.
     * It is used during the start of the game.
     * @param index The index to increment in array of students.
     */
    public void incrementPos(int index){
        this.arr_students[index]++;
    }

    /**
     * This method checks if a prohibition card has been put on the island.
     * @return {@code True} if a prohibition card has been put on the island, {@code False} if not.
     */
    public boolean isProhibition_card() {
        return prohibition_card;
    }

    /**
     * This method sets a prohibition card on the island.
     * @param prohibition_card {@code True} if wanted to put a prohibition card on the island, {@code False} if not.
     */
    public void setProhibition_card(boolean prohibition_card) {
        this.prohibition_card = prohibition_card;
    }

    /**
     * This method increments the element of the array of students by 1 given the index.
     * @param index The index to know which student will be added.
     */
    public void setOneStudent(int index){
        arr_students[index]++;
    }

    /**
     * This method sets the include_towers variable
     * @param include_towers indicate if the towers must be counted in the influence
     */
    public void setInclude_towers(boolean include_towers) {
        this.include_towers = include_towers;
    }

    /**
     * This method sets the prohibition.
     * @param prohibition_colour {@code True} if the brohibition is enabled, {@code False} if not.
     */
    public void setProhibition_colour(boolean prohibition_colour) {
        this.prohibition_colour = prohibition_colour;
    }

    /**
     * This method sets the extra influence activated by a character card.
     * @param extra_influence The extra influence to be added to the calculus of the influence.
     */
    public void setExtra_influence(int extra_influence) {
        this.extra_influence = extra_influence;
    }
}

