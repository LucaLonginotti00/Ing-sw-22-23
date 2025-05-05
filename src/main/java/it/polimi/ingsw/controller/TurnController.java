package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.message.toClient.*;
import it.polimi.ingsw.network.server.ClientHandler;
import java.util.List;


/**
 * This class represents the controller of the turn. It manages the planning phase and the action phase of each player.
 * It controls and calculates the player order for the next round.
 */
public class TurnController {
    private int[] player_order;
    private GameState GS;
    private int n_players;
    List<Player> P_L;
    CharacterCard played_cCard;
    List<ClientHandler> clienthandler;
    private boolean endgame;
    private GameController gameController;

    /**
     * Constructor of the class.
     * @param n_players The number of players in the game.
     * @param names The strings containing the names of the players.
     * @param wizard The array of int containing the numbers representing the wizards.
     * @param expert_mode {@code True} if expert mode is enabled, {@code False} if not.
     * @param Player_List The list of player for turn order.
     */
    public TurnController(int n_players, String[] names, int wizard[], boolean expert_mode, List<Player> Player_List,List<ClientHandler> clienthandler){
        endgame=false;
        this.n_players=n_players;
        this.P_L=Player_List;
        player_order= new int[n_players];
        this.clienthandler=clienthandler;
        for(int i=0; i<n_players;i++){
            player_order[i]=i;
        }
        GS = new GameState(n_players, names, wizard, expert_mode,0, P_L);
    }

    /**
     * This method defines the planning phase by calculating the order of player
     * and replenishing the clouds at the beginning of the round
     */
    public void planning_phase_general(){
        GS.getGT().replenish_clouds(this);
        for(int i=0;i<n_players;i++){
            planning_phase_personal(player_order[i]);
        }
        Calculate_Player_order();
    }


    /**
     * This method represent the planning phase of the current player. Here the player decides which assistance card
     *  he wants to play.
     * @param i The ID of the player(?).
     */
    public void planning_phase_personal(int i){

        if(P_L.get(i).getDeck().getCards().size()>0){
            clienthandler.get(i).sendMessageToClient(new ChooseAssistantCardRequest(P_L.get(i),GS.getGT()));
       GS.getGT().choose_assistant(P_L.get(i),clienthandler.get(i).getAssistantCardChosen());}
        else if(P_L.get(i).getDeck().getCards().size()==0){
            endgame=true;
        }

    }


    /**
     * This method represents the action phase of the round. It moves the students to the islands, moves mother nature
     * and checks if island can be conquered and merged.
     * It's also used to play character cards.
     */
    public void action_phase(){
        List<Student> stud_to_island;
        boolean check_for_tower;
        int[] tempCloud;
        Student[] tempCCStud=new Student[4] ;

        for(int i=0;i<n_players;i++){

            if(endgame){
                break;
            }
            GS.setCurr_player(player_order[i]);

            if (gameController.getGameMode() == GameMode.EXPERT) {
                clienthandler.get(player_order[i]).sendMessageToClient(new UseCharacterCardRequest());
                if (clienthandler.get(player_order[i]).getUseCharacterCard() == 1) {
                    clienthandler.get(player_order[i]).sendMessageToClient(new ChooseCharacterCardRequest(P_L.get(player_order[i]), GS.getGT().getArr_character()));
                    if (clienthandler.get(player_order[i]).getCanBeUsed()) {
                        if(GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].getID_code()==1){
                            for(int l=0;l<4;l++){
                                tempCCStud[l]=new Student(GS.getGT().getBoards()[i].inverse_color(GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].getStudents()[l]));
                            }
                            clienthandler.get(player_order[i]).sendMessageToClient(new ShowStudentRequest(tempCCStud));
                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].setChosen_student(clienthandler.get(player_order[i]).getMonkStudent());
                            clienthandler.get(player_order[i]).sendMessageToClient(new HeraldIslandRequest(GS.getGT().getIslands()));
                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].setIndex_to(clienthandler.get(player_order[i]).getHeraldIsland());
                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].effect(getGS());
                        }
                        else if(GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].getID_code()==2){

                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].effect(getGS());
                        }
                        else if(GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].getID_code()==3){

                            clienthandler.get(player_order[i]).sendMessageToClient(new HeraldIslandRequest(GS.getGT().getIslands()));
                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].setIndex_to(clienthandler.get(player_order[i]).getHeraldIsland());
                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].effect(getGS());
                        }
                        else if(GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].getID_code()==4){

                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].effect(getGS());
                        }
                        else if(GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].getID_code()==5){

                            clienthandler.get(player_order[i]).sendMessageToClient(new HeraldIslandRequest(GS.getGT().getIslands()));
                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].setIndex_to(clienthandler.get(player_order[i]).getHeraldIsland());
                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].effect(getGS());
                        }
                        else if(GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].getID_code()==6){

                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].effect(getGS());
                        }
                        else if(GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].getID_code()==8){

                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].effect(getGS());
                        }
                        else if(GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].getID_code()==11){
                            for(int l=0;l<4;l++){
                                tempCCStud[l]=new Student(GS.getGT().getBoards()[i].inverse_color(GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].getStudents()[l]));
                            }
                            clienthandler.get(player_order[i]).sendMessageToClient(new ShowStudentRequest(tempCCStud));
                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].setChosen_student(clienthandler.get(player_order[i]).getMonkStudent());
                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].setCurrent_player(player_order[i]);
                            GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()].effect(getGS());
                        }
                        played_cCard=GS.getGT().getArr_character()[clienthandler.get(player_order[i]).getChCardUsed()];
                    }
                }
            }

            stud_to_island=GS.getGT().getBoards()[player_order[i]].moveEntranceStudents(GS,clienthandler.get(player_order[i]));


            //add all the student to the islands
            for(int j=0;j< stud_to_island.size();j++){

                clienthandler.get(player_order[i]).sendMessageToClient(new ChooseIslandRequest( GS.getGT().getIslands(),stud_to_island.get(j)));
                GS.getGT().getIslands().get(clienthandler.get(player_order[i]).getIslandToMove()).add_students(stud_to_island.get(j));
            }




            for(int j=0;j<GS.getGT().getHow_many_left();j++){
              clienthandler.get(player_order[i]).sendMessageToClient(new DisplayIslandInfoRequest((GS.getGT().getIslands().get(j)),j));
            }

            clienthandler.get(player_order[i]).sendMessageToClient(new MoveMnRequest(GS.getGT().getMother_nature_pos(),P_L.get(GS.getCurr_player())));
            GS.getGT().move_mother_nature(clienthandler.get(player_order[i]).getMnmovement());


            check_for_tower=GS.getGT().getIslands().get(GS.getGT().getMother_nature_pos()).calculate_influence(player_order[i],GS.getGT().getBoards());

            //if(!check_for_tower) means that you have gained the control of the island
            if(!check_for_tower){
                if(n_players==4){
                    if((player_order[i]==1 || player_order[i]==2) && GS.getGT().getBoards()[1].getN_towers()>0 ){
                        GS.getGT().getBoards()[1].setN_towers(GS.getGT().getBoards()[1].getN_towers()-1);
                        if(GS.getGT().getBoards()[1].getN_towers()==0){
                            endgame=true;
                        }
                    }

                    else if((player_order[i]==3 || player_order[i]==4) && GS.getGT().getBoards()[3].getN_towers()>0){
                        GS.getGT().getBoards()[3].setN_towers(GS.getGT().getBoards()[3].getN_towers()-1);
                        if(GS.getGT().getBoards()[3].getN_towers()==0){
                            endgame=true;
                        }
                    }
                }
                else{
                    if(GS.getGT().getBoards()[i].getN_towers()>0){
                    GS.getGT().getBoards()[player_order[i]].setN_towers(GS.getGT().getBoards()[player_order[i]].getN_towers()-1);
                        if(GS.getGT().getBoards()[i].getN_towers()==0){
                            endgame=true;
                        }
                    }
                }


            }
            if(endgame){
                break;
            }


            GS.getGT().merge(GS.getGT().getMother_nature_pos(),player_order[i],GS.getGT().getBoards());

            if(GS.getGT().getIslands().size()==3){
                endgame=true;
            }
            if(endgame){
                break;
            }
            tempCloud=GS.getGT().choose_cloud(clienthandler.get(player_order[i])).getArr_students();

            GS.getGT().getBoards()[player_order[i]].setArrEntranceStudents(tempCloud);

            if(clienthandler.get(player_order[i]).getUseCharacterCard()==1 && played_cCard!=null){
                if(played_cCard.getID_code()==8){
                    for(int m=0;m<GS.getGT().getIslands().size();m++){
                        GS.getGT().getIslands().get(m).setExtra_influence(0);
                    }
                }
                if(played_cCard.getID_code()==6){
                    for(int m=0;m<GS.getGT().getIslands().size();m++){
                        GS.getGT().getIslands().get(m).setInclude_towers(true);
                    }
                }
            }
        }
    }

    /**
     * This method calculates the order of the players for the turn.
     * The player with the lowest value of the played assistance card starts first.
     */
    public void Calculate_Player_order() {
        int min=-1;
        int player=0;

        for(int i=0;i<n_players;i++){
            for(int j=0;j<n_players  ;j++){
                if((P_L.get(j).getChosen_card().getValue()<min  || min==-1) && P_L.get(j).isChosen()==false){
                    min=P_L.get(j).getChosen_card().getValue();
                    player=P_L.get(j).getPlayer_ID();

                }
            }

            P_L.get(player).setChosen(true);

            min=-1;
            player_order[i]=player;

        }
        for(int i=0;i<n_players;i++){
            P_L.get(i).setChosen(false);
        }
    }

    /**
     * This method returns the game state.
     * @return The game state.
     */
    public GameState getGS() {
        return GS;
    }

    /**
     * This method returns the end game.
     * @return The end game.
     */
    public boolean getendgame(){
        return endgame;
    }

    /**
     * This method sets the end game.
     * @param endgame The end game.
     */
    public void setEndgame(boolean endgame) {
        this.endgame = endgame;
    }

    /**
     * This method sets the game controller.
     * @param gameController The game controller.
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

}
