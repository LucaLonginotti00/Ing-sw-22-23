package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.character.*;
import it.polimi.ingsw.network.message.toClient.ChooseCloudRequest;
import it.polimi.ingsw.network.server.ClientHandler;

import java.io.Serializable;
import java.util.*;


/**
 * This class represent the game table. Here the list of islands, the list of clouds and the array of boards is created.
 * The class includes methods to move mother nature in the islands and the merge of island if possible.
 * The discard deck of assistance cards is store here and the three character cards are drawn in the beginning.
 */
public class GameTable implements Serializable {
    private final int num_players;
    private int current_player;
    private int island_counter=12;
    private int island_index;
    private Board[] boards;
    private LinkedList<Island> islands;
    private List<Cloud> clouds;
    private List<Cloud> tempclouds;
    private int mother_nature_pos;
    private int[] bag;
    private CharacterCard[] arr_character;
    private Turn turn;
    private AssistanceCard[] discard_deck;
    private int general_reservoir;
    private List<Player> pl;

    /**
     * Constructor of the class.
     * @param num_players The number of players in the game. There can be 2,3 or 4 players.
     * @param turn The first turn of the game.
     */
    public GameTable(int num_players, Turn turn){
        this.num_players = num_players;
        this.turn = turn;
        this.current_player = turn.getCurrent_player();
        this.general_reservoir = 20-num_players;

        bag = new int[5];
        for(int i=0;i<5;i++) {
            bag[i] = 2;
        }

        boards = new Board[num_players];
        for(int i=0;i<num_players;i++) {
            boards[i] = new Board(num_players,i, TowerColour.values()[i]);
        }




        islands = new LinkedList<>();
        for(int i=0;i<12;i++){
            islands.add(new Island( boards, i+1, TowerColour.STARTER));
        }

        setMother_nature_start();
        bag_island_start();

        for (int i = 0; i < num_players; i++) {
            Random random = new Random();

            if (num_players == 2) {
                for (int j = 0; j < 7; j++) {
                    int temprand = random.nextInt(5);
                    boards[i].setArrEntranceStudents(new Student(boards[i].inverse_color(temprand)), j);
                    bag[temprand]--;
                }
            } else if (num_players == 3) {
                for (int j = 0; j < 9; j++) {
                    int temprand = random.nextInt(5);
                    boards[i].setArrEntranceStudents(new Student(boards[i].inverse_color(temprand)), j);
                    bag[temprand]--;
                }

            }
        }

        clouds = new ArrayList<>();
        tempclouds=new ArrayList<>();
        for(int i=0;i<num_players;i++){
            clouds.add(new Cloud(i+1));
        }

        reset_temp_clouds();
        discard_deck = new AssistanceCard[num_players];
        for (int i = 0; i < num_players; i++) {
            discard_deck[i] = AssistanceCard.STARTER;
        }

        arr_character = new CharacterCard[3];

        draw_three_charCards();
    }

    /**
     * This method checks if an assistance card is playable.
     * That means it can't be on the discard deck of any other player.
     * @param chosen The assistance card chosen to be played.
     * @param deck The deck of the player
     * @return {@code False} if card is already played, {@code True} otherwise.
     */
    public boolean check_if_playable(AssistanceCard chosen, Deck deck){
        boolean playable_card = true;
        for (int i = 0; i < num_players && playable_card; i++) {
            if(discard_deck[i].equals(chosen)  && !check_only_this_card(deck,chosen)){
                playable_card = false;
            }
        }
        return playable_card;
    }

    /**
     * This method checks if  assistance card is the only card playable.
     * @param chosen The assistance card chosen to be played.
     * @param deck The deck of the player
     * @return {@code False} if card isn't the only card playable
     */
    public boolean check_only_this_card(Deck deck,AssistanceCard chosen){
        boolean check = true;
        for (int i = 0; i<deck.getCards().size() && check; i++) {
            if(!deck.getCards().get(i).equals(chosen)){
                check = false;
            }
        }
        return check;
    }

    /**
     * This method lets the player choose their assistance card
     * @param player The current player that is going to choose the assistance card.
     * @param ass_chosen The chosen assistant.
     */
    public void choose_assistant(Player player,int ass_chosen){
        AssistanceCard choice;
        choice=player.getDeck().getCards().get(ass_chosen);
        player.setMoves(choice.getMother_nature_movement());
        discard_deck[player.getPlayer_ID()]=choice;
        player.setChosen_card(choice);
        player.getDeck().remove_used_card(choice);

    }

    /**
     * This method checks if islands can be merged. It checks previous and next island.
     * If the checked island is the first of the list, It checks the next one and the last of the list.
     * If the checked island is the last of the list, It checks the previous one and the first of the list.
     * @param island_index The index of the island to be checked and then merged into.
     * @return An array of two integers. index 0 is the previous island, index 1 is the next island. If a value is -1, that island can't be merged.
     */
    private int[] check_merge(int island_index){
        int[] indexes = new int[]{-1,-1} ;

        //Check if there are islands prev or next to the current that can be merged
        if(island_index<island_counter-1 && island_index>0) {
            if (islands.get(island_index).getPlayer_controller() == islands.get(island_index + 1).getPlayer_controller() && islands.get(island_index).getPlayer_controller()!=-1) {
                indexes[1] = island_index+1;
            }
            if (islands.get(island_index).getPlayer_controller() == islands.get(island_index-1).getPlayer_controller() && islands.get(island_index).getPlayer_controller()!=-1 ){
                indexes[0] = island_index-1;
            }
        }

        //Check the same thing for the last island of the list but goes to 0 to check the next
        else if(island_index == island_counter-1){
            if (islands.get(island_index).getPlayer_controller() == islands.get(0).getPlayer_controller() && islands.get(island_index).getPlayer_controller()!=-1) {
                indexes[1] = 0;
            }
            if (islands.get(island_index).getPlayer_controller() == islands.get(island_index-1).getPlayer_controller() && islands.get(island_index).getPlayer_controller()!=-1){
                indexes[0] = island_index-1;
            }
        }

        //Check the first element of the list. If prev can be merged goes to the last element of the list
        else if (island_index==0){
            if (islands.get(island_index).getPlayer_controller() == islands.get(1).getPlayer_controller() && islands.get(island_index).getPlayer_controller()!=-1) {
                indexes[1] = 1;
            }
            if (islands.get(island_index).getPlayer_controller() == islands.get(island_counter-1).getPlayer_controller() && islands.get(island_index).getPlayer_controller()!=-1){
                indexes[0] = island_counter-1;
            }
        }

        return indexes;
    }

    /**
     * This method merges the close islands. Before merging, It checks what merges can be done by calling the method check_merge.
     * @param island_index The index of the island that will be merged into.
     * @param current_player The current player of the turn.
     * @param Boards The array of the boards.
     */
    public void merge(int island_index,int current_player,Board[] Boards) {
        this.island_index = island_index;
        int[] toMerge_indexes = check_merge(island_index);

        if (toMerge_indexes[0] >= 0) {
            int[] students1 = islands.get(island_index).getArr_students();
            int[] students2 = islands.get(toMerge_indexes[0]).getArr_students();
            for (int i = 0; i < 5; i++) {
                students1[i] += students2[i];
            }
            islands.get(island_index).setArr_students(students1);
            islands.get(island_index).setTower(islands.get(island_index).getTower() + islands.get(toMerge_indexes[0]).getTower());

        }
        if (toMerge_indexes[1] >= 0) {
            int[] students1 = islands.get(island_index).getArr_students();
            int[] students2 = islands.get(toMerge_indexes[1]).getArr_students();
            for (int i = 0; i < 5; i++) {
                students1[i] += students2[i];
            }
            islands.get(island_index).setArr_students(students1);
            islands.get(island_index).setTower(islands.get(island_index).getTower() + islands.get(toMerge_indexes[1]).getTower());
        }

        islands.get(island_index).calculate_influence(current_player,Boards);


        boolean removed = false;
        if (toMerge_indexes[0] >= 0) {
            islands.remove(toMerge_indexes[0]);
            removed = true;
            island_counter--;
        }

        if (toMerge_indexes[1]>=0) {
            if (removed) {
                //If the removed island is after the next one to remove no problem
                if (toMerge_indexes[0] > toMerge_indexes[1]) {
                    islands.remove(toMerge_indexes[1]);
                }
                //If the removed island is before the next one to remove the index goes down by 1
                else{
                    islands.remove(toMerge_indexes[1] - 1);
                }
            }
            else{
                islands.remove(toMerge_indexes[1]);
            }
            island_counter--;
        }
    }

    /**
     * This method moves mother nature by a number of moves decided by the current player of the turn.
     * @param moves Number of moves that the player decides, not the max possible moves.
     */
    public void move_mother_nature(int moves){
        islands.get(mother_nature_pos).setMother_nature(false);
        if(mother_nature_pos+moves<island_counter) {
            islands.get(mother_nature_pos+moves).setMother_nature(true);
            setMother_nature_pos(mother_nature_pos+moves);
        }else {
            moves = (moves+mother_nature_pos)%island_counter;
            islands.get(moves).setMother_nature(true);
            setMother_nature_pos(moves);
        }
    }

    //Sets mother nature on a random island
    private void setMother_nature_start() {
        Random rand = new Random();
        this.mother_nature_pos = rand.nextInt(12);
        islands.get(this.mother_nature_pos).setMother_nature(true);
    }

    //Puts the first students on the islands
    private void bag_island_start(){
        Random rand = new Random();
        int tempRand;

        for(int i=0;i<12;i++){
            if(i!=(this.mother_nature_pos+6)%12 && i!=this.mother_nature_pos) {
                tempRand = rand.nextInt(5);
                while (bag[tempRand] == 0) {
                    tempRand = rand.nextInt(5);
                }
                bag[tempRand]--;
                islands.get((i) % 12).incrementPos(tempRand);
            }
        }

        for(int i=0;i<5;i++) {
            bag[i] = 24;
        }
    }

    /**
     * This method refills each cloud with students.
     * @param TC The turn controller.
     */
    public void replenish_clouds(TurnController TC){
        Random rand = new Random();
        int temprand;
        int count = 0;
        boolean bag_not_empty = true;
        boolean check = true;
        for(int i=0;i<num_players;i++){
            for(int j=0;j<5;j++){
                clouds.get(i).getArr_students()[j]=0;
            }
        }
        if(num_players==2){
            for(int i=0;i<num_players && bag_not_empty;i++){
                while(count<3 && bag_not_empty){
                    temprand=rand.nextInt(5);
                    if( bag[temprand]==0){
                        for(int j=0;j<5;j++){
                            if(bag[j]>0){
                                check=false;
                            }
                        }
                        if(check==true){
                            bag_not_empty=false;
                        }
                        else{
                            check=true;
                            while(bag[temprand]==0){
                                temprand=rand.nextInt(5);
                            }
                        }
                    }
                    if(bag_not_empty==true){
                        clouds.get(i).setArr_students(temprand);
                        bag[temprand]--;
                        count++;
                    }
                }
                count=0;
            }
        }
        else if(num_players==3){
            for(int i=0;i<num_players && bag_not_empty;i++){
                while(count<4 && bag_not_empty){
                    temprand=rand.nextInt(5);
                    if( bag[temprand]==0){
                        for(int j=0;j<5;j++){
                            if(bag[j]>0){
                                check=false;
                            }
                        }
                        if(check==true){
                            bag_not_empty=false;
                        }
                        else{
                            check=true;
                            while(bag[temprand]==0){
                                temprand=rand.nextInt(5);
                            }
                        }
                    }
                    if(bag_not_empty==true){
                        clouds.get(i).setArr_students(temprand);
                        bag[temprand]--;
                        count++;
                    }
                }
                count=0;
            }
        }
        if(!bag_not_empty){
            TC.setEndgame(true);
        }

    }

    /**
     * This method lets the player choose the cloud that he wants.
     * @param clientHandler The client handler.
     */
    public Cloud choose_cloud(ClientHandler clientHandler){
        Cloud chosen_cloud;

        clientHandler.sendMessageToClient(new ChooseCloudRequest(tempclouds));
        chosen_cloud=tempclouds.get(clientHandler.getCloudChosen());
        tempclouds=del_temp_cloud(clientHandler.getCloudChosen());
        if(tempclouds.size()==0){
            reset_temp_clouds();
        }
        return chosen_cloud;

    }

    //Random draw of the three character cards
    private void draw_three_charCards(){
        int[] drawn = new int[3];
        int[] monk_drawn = new int[5];
        int[] princess_drawn = new int[5];
        int draw_stud;
        Random rand = new Random();

        //Random draw of 4 students to be placed on the monk card.
        for (int i = 0; i < 4; i++) {
            draw_stud = rand.nextInt(5);
            while (bag[draw_stud] == 0) {
                draw_stud = rand.nextInt(5);
            }
            bag[draw_stud]--;
            monk_drawn[draw_stud]++;
        }

        //Random drawn of 4 students to be placed on the spoilt princess card.
        for (int i = 0; i < 4; i++) {
            draw_stud = rand.nextInt(5);
            while (bag[draw_stud] == 0) {
                draw_stud = rand.nextInt(5);
            }
            bag[draw_stud]--;
            princess_drawn[draw_stud]++;
        }

        List<CharacterCard> char_deck;
        char_deck = new ArrayList<>(Arrays.asList(
                new Monk(monk_drawn),
                new Farmer(),
                new Herald(),
                new MagicMailman(),
                new HerbsGrandma(),
                new Centaur(),
                new Knight(),
                new SpoiltPrincess(princess_drawn)
        ));

        drawn[0] = rand.nextInt(8);
        arr_character[0] = char_deck.get(drawn[0]);
        drawn[1] = rand.nextInt(8);
        while (drawn[1]==drawn[0]){
            drawn[1] = rand.nextInt(8);
        }
        arr_character[1] = char_deck.get(drawn[1]);
        drawn[2] = rand.nextInt(8);
        while (drawn[2]==drawn[0] || drawn[2]==drawn[1]){
            drawn[2] = rand.nextInt(8);
        }
        arr_character[2] = char_deck.get(drawn[2]);

        boolean check=false;
        //Check if a card is the monk, if not puts the students back
        for (int i = 0; i < 3 && !check; i++) {
            if ( arr_character[i].getID_code()==1)
                check = true;
        }
        if (!check){
            for (int i = 0; i < 5; i++) {
                bag[i] = bag[i]+monk_drawn[i];
            }
        }

        //Check if a card is the spoilt princess, if not puts the students back
        check=false;
        for (int i = 0; i < 3 && !check; i++) {
            if ( arr_character[i].getID_code()==11)
                check = true;
        }
        if (check==false){
            for (int i = 0; i < 5; i++) {
                bag[i] = bag[i]+princess_drawn[i];
            }
        }
    }

    /**
     * This method sets mother nature position.
     * @param mother_nature_pos Integer of position of mother nature.
     */
    public void setMother_nature_pos(int mother_nature_pos) {
        this.mother_nature_pos = mother_nature_pos;
    }

    /**
     * This method resets the temporary clouds.
     */
    public void reset_temp_clouds(){
        tempclouds.addAll(clouds);
    }

    /**
     * This method returns mother nature position.
     * @return Integer of mother nature position.
     */
    public int getMother_nature_pos() {
        return mother_nature_pos;
    }

    /**
     * This method removes a temporary cloud given his index.
     * @param cloud_index he index of the cloud to be removed.
     * @return The updated list of clouds.
     */
    public List<Cloud> del_temp_cloud(int cloud_index) {
        tempclouds.remove(cloud_index);
        return tempclouds;
    }

    /**
     * This method returns the list of clouds.
     * @return The list of clouds.
     */
    public List<Cloud> getClouds() {
        return clouds;
    }

    /**
     * This method returns the array of boards.
     * @return The array of boards.
     */
    public Board[] getBoards() {
        return boards;
    }

    /**
     * This method returns the number of islands.
     * @return The number of islands.
     */
    public int getIsland_counter() {
        return island_counter;
    }

    /**
     * This method returns the number of remaining islands.
     * @return The number of remaining islands.
     */
    public int getHow_many_left(){;
        return island_counter;
    }

    /**
     * This method returns the list of islands.
     * @return The list of islands.
     */
    public LinkedList<Island> getIslands() {
        return islands;
    }

    /**
     * This method returns the number of players in the game.
     * @return The number of players in teh game.
     */
    public int getNum_players() {
        return num_players;
    }

    /**
     * This method returns the current player.
     * @return The current player.
     */
    public int getCurrent_player() {
        return current_player;
    }

    /**
     * This method returns the bag.
     * @return The bag.
     */
    public int[] getBag() {
        return bag;
    }

    /**
     * This method sets the bag
     * @param bag The bag.
     */
    public void setBag(int[] bag) { this.bag = bag;}

    /**
     * This method returns the array of characters.
     * @return The array of characters.
     */
    public CharacterCard[] getArr_character() {
        return arr_character;
    }

    /**
     * This method returns the current turn.
     * @return The current turn.
     */
    public Turn getTurn() {
        return turn;
    }


    /**
     * This method returns the discard deck.
     * @return The discard deck.
     */
    public AssistanceCard[] getDiscard_deck() {
        return discard_deck;
    }

    /**
     * This method draws 1 student from the bag.
     * @return The index of the bag representing the colour of the drawn student.
     */
    public int drawOne() {
        boolean check = false;
        int chosen = -1;
        for (int i = 0; i < 5 && !check; i++) {
            if (bag[i] != 0)
                check = true;
        }
        if (check) {
            Random rand = new Random();
            chosen = rand.nextInt(5);
            while (bag[chosen] == 0) {
                chosen = rand.nextInt(5);
            }
            bag[chosen]--;
        }
        return chosen;
    }

    /**
     * This method sets the list of players.
     * @param pl The list of players.
     */
    public void setPl(List<Player> pl) {
        this.pl = pl;
    }

    /**
     * This method returns the list of players.
     * @return The list of players.
     */
    public List<Player> getPl() {
        return pl;
    }
}
