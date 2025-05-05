package it.polimi.ingsw.model;

import it.polimi.ingsw.network.message.toClient.ChooseIslandOrBoardRequest;
import it.polimi.ingsw.network.message.toClient.StudentToMoveRequest;
import it.polimi.ingsw.network.message.toClient.DisplayDiningRoomColourFullRequest;
import it.polimi.ingsw.network.message.toClient.DisplayStudentChosenPreviouslyRequest;
import it.polimi.ingsw.network.server.ClientHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the board.
 * Each student has his personal board containing an array of int representing the students (see more at: {@link DiskColour}.
 * The class includes the methods to move the students into and out of the board.
 * Each board has towers with different colours (see more at: {@link TowerColour}).
 * The towers in the board are 8 if the game has 2 or 4 players, 7 if the game has 3 players.
 */
public class Board implements Serializable {
    private final int board_player;
    private int n_towers;
    private int[] arrPositionStudents;
    private Student[] arrEntranceStudents;
    private boolean[] arrProfessors;
    private int tower;
    private int maxEntranceStudents;
    private boolean[][] trackCoins = new boolean[5][3];
    private boolean farmer_state;

    /**
     * Constructor of the class.
     * @param numOfPlayers The number of players in the game.
     * @param playerID The player ID assigned to the board. It's an indirect reference to the player.
     * @param tower The tower colour assigned to the player and to the board.
     */
    public Board(int numOfPlayers, int playerID, TowerColour tower){
        this.board_player = playerID;
        this.tower = tower.getTower_translate();
        arrPositionStudents = new int[5];
        arrProfessors = new boolean[5];

        for(int i=0;i<5;i++){
            arrPositionStudents[i]=0;
        }
        for(int i=0; i<5;i++){
            for(int j=0; j<3;j++){
                trackCoins[i][j]=false;
            }
        }

        farmer_state=false;

        if(numOfPlayers == 2 ){
            arrEntranceStudents = new Student[7];
            maxEntranceStudents=7;
            n_towers = 8;

        }
        else if(numOfPlayers == 3){
            arrEntranceStudents = new Student[9];
            maxEntranceStudents=9;
            n_towers = 6;

        }
    }

    /**
     * This method adds a professor to the board.
     * @param profColour The colour of the professor to be added.
     */
    public void add_prof(DiskColour profColour){
        arrProfessors[profColour.getTranslateColour()]=true;
    }

    /**
     * This method adds a student to the board.
     * @param studentColour The colour of the student to be added.
     */
    public void add_student(DiskColour studentColour){
        arrPositionStudents[studentColour.getTranslateColour()]++;
    }

    /**
     * This method moves students to the islands.
     * @param GS The game state.
     * @param clientHandler The client handler.
     * @return The list of students sent to an island.
     */
    public List<Student> moveEntranceStudents(GameState GS, ClientHandler clientHandler){

        int studentsChosen=0;
        boolean noOneProf = true;
        List<Student> studentToIslands = new ArrayList<>();

        while(studentsChosen <(GS.getGT().getNum_players()+1)) {
            clientHandler.sendMessageToClient(new StudentToMoveRequest(this));
            if (arrEntranceStudents[clientHandler.getStudToMove()].getIsChosen() == false) {
                clientHandler.sendMessageToClient(new ChooseIslandOrBoardRequest(this,clientHandler.getStudToMove()));

                if(clientHandler.getPos() == 0){
                    if(arrPositionStudents[arrEntranceStudents[clientHandler.getStudToMove()].getColour()]<10){
                        arrPositionStudents[arrEntranceStudents[clientHandler.getStudToMove()].getColour()]++;
                        coinsEarned();
                        arrEntranceStudents[clientHandler.getStudToMove()].Chosen();
                        studentsChosen++;
                        if(farmer_state){
                            for(int j=0;j<5;j++){
                                for(int k=0;k<GS.getGT().getNum_players();k++){
                                    if(GS.getGT().getBoards()[k].getArrProfessors()[j]){
                                        noOneProf=false;
                                    }
                                }
                                for(int i=0;i<GS.getGT().getNum_players();i++){
                                    if((GS.getGT().getBoards()[GS.getCurr_player()].getArrPositionStudents()[j]>=GS.getGT().getBoards()[i].getArrPositionStudents()[j] && GS.getCurr_player()!=i) || (noOneProf==true && GS.getGT().getBoards()[GS.getCurr_player()].getArrPositionStudents()[j]>=GS.getGT().getBoards()[i].getArrPositionStudents()[j])){
                                        GS.getGT().getBoards()[GS.getCurr_player()].setprofessor(j,true);
                                        GS.getGT().getBoards()[i].setprofessor(j,false);
                                    }
                                }
                                noOneProf=true;

                            }
                        }
                        else{
                            for(int j=0;j<5;j++){
                                for(int k=0;k<GS.getGT().getNum_players();k++){
                                    if(GS.getGT().getBoards()[k].getArrProfessors()[j]){
                                        noOneProf=false;
                                    }
                                }
                                for(int i=0;i<GS.getGT().getNum_players();i++){
                                    if((GS.getGT().getBoards()[GS.getCurr_player()].getArrPositionStudents()[j]>GS.getGT().getBoards()[i].getArrPositionStudents()[j] && GS.getCurr_player()!=i && GS.getGT().getBoards()[i].getArrProfessors()[j])|| (noOneProf==true && GS.getGT().getBoards()[GS.getCurr_player()].getArrPositionStudents()[j]>GS.getGT().getBoards()[i].getArrPositionStudents()[j])){
                                        GS.getGT().getBoards()[GS.getCurr_player()].setprofessor(j,true);
                                        GS.getGT().getBoards()[i].setprofessor(j,false);

                                    }
                                }
                                noOneProf=true;

                            }
                        }
                    }
                    else{
                        clientHandler.sendMessageToClient(new DisplayDiningRoomColourFullRequest(this,clientHandler.getStudToMove()));
                    }
                }
                else if(clientHandler.getPos() == 1){
                    studentToIslands.add(new Student(arrEntranceStudents[clientHandler.getStudToMove()].getEnumColour()));
                    arrEntranceStudents[clientHandler.getStudToMove()].Chosen();
                    studentsChosen++;
                }

            }
            else if (arrEntranceStudents[clientHandler.getStudToMove()].getIsChosen() == true){
                clientHandler.sendMessageToClient(new DisplayStudentChosenPreviouslyRequest(this,clientHandler.getStudToMove()));
            }
        }
        if(farmer_state){
            farmer_state=false;
        }
        return studentToIslands;
    }

    /**
     * This method checks if coins can be earned.
     * @return The coins earned this turn, to sum with current coins each turn.
     */
    public int coinsEarned(){
        int coins=0;
        for(int i=0; i<5;i++){
            if(arrPositionStudents[i]/3==1){
                if(trackCoins[i][0]==false){
                    coins++;
                    trackCoins[i][0]=true;
                }
            }
            if(arrPositionStudents[i]/3==2){
                if(trackCoins[i][1]==false){
                    coins++;
                    trackCoins[i][1]=true;
                }
            }
            if(arrPositionStudents[i]/3==3){
                if(trackCoins[i][2]==false){
                    coins++;
                    trackCoins[i][2]=true;
                }
            }
        }
        return coins;
    }

    /**
     * This method returns the array of professors that are on the board.
     * @return The array of professors that are on the board.
     */
    public boolean[] getArrProfessors() {
        return arrProfessors;
    }

    /**
     * This method returns the array of students on the board.
     * @return The array of students on the board.
     */
    public int[] getArrPositionStudents() {
        return arrPositionStudents;
    }

    /**
     * This method returns the number of towers remaining on the board.
     * @return The number of towers remaining on the board.
     */
    public int getN_towers() {
        return n_towers;
    }

    /**
     * This method sets the number of towers on the board.
     * @param n_towers The number of towers on the board.
     */
    public void setN_towers(int n_towers) {
        this.n_towers = n_towers;
    }

    /**
     * This method sets the array of students on the board
     * @param arrPositionStudents
     */
    public void setArrPositionStudents(int[] arrPositionStudents) {
        this.arrPositionStudents = arrPositionStudents;
    }

    /**
     * This method sets the array of entrance of students.
     * @param arrEntranceStudents The array of entrance of students.
     */
    public void setArrEntranceStudents(Student arrEntranceStudents, int pos) {
        this.arrEntranceStudents[pos] = arrEntranceStudents;
    }

    /**
     * This method sets the array of professors on the board.
     * @param arrProfessors The array of professors on the board.
     */
    public void setArrProfessors(boolean[] arrProfessors) {
        this.arrProfessors = arrProfessors;
    }

    /**
     * This method sets the professor at arrProfessors[index] value.
     * @param index The Index of the professor.
     * @param state If the professor is on the board or not.
     */
    public void setprofessor(int index,boolean state){
        getArrProfessors()[index]=state;
    }

    /**
     * This method sets the towers on the board.
     * @param tower The number of towers on the board.
     */
    public void setTower(int tower) {
        this.tower = tower;
    }

    /**
     * This method returns the arrays used to keep track of the coins.
     * @return The arrays used to keep track of the coins.
     */
    public boolean[][] getTrackCoins() {
        return trackCoins;
    }

    /**
     * This method sets the arrays used to keep track of the coins.
     * @param trackCoins The arrays used to keep track of the coins.
     */
    public void setTrackCoins(boolean[][] trackCoins) {
        this.trackCoins = trackCoins;
    }

    /**
     * This method returns the tower colour on the board.
     * @return The tower colour on the board.
     */
    public int getTower() {
        return tower;
    }

    /**
     * This method returns the array of students to be moved to the entrance.
     * @return The array of students to be moved to the entrance.
     */
    public Student[] getArrEntranceStudents() {
        return arrEntranceStudents;
    }

    /**
     * This method adds a student to the board
     * @param index The index of the array of student to be added.
     */
    public void setOneStudent(int index){
        arrPositionStudents[index]++;
    }

    /**
     * This method adds a student to the entrance
     * @param Cloud_Students The array that contains the students on the clouds
     */
    public void setArrEntranceStudents(int[] Cloud_Students ){
        int pos=0;
        while(pos<5){
            if(Cloud_Students[pos]>0){
                for(int i=0;i<arrEntranceStudents.length;i++){
                    if(arrEntranceStudents[i].getIsChosen()){
                        arrEntranceStudents[i]=new Student(inverse_color(pos));
                        Cloud_Students[pos]--;
                        pos=0;
                        break;
                    }
                }
            }else{pos++;}

        }
        

    }

    /**
     * This method convert a position in the respective color
     * @param color The index of the array that represent a color
     */
    public DiskColour inverse_color(int color){
        if(color==0){
            return DiskColour.YELLOW;
        }
        else if(color==1){
            return DiskColour.RED;
        }
        else if(color==2){
            return DiskColour.PINK;
        }
        else if(color==3){
            return DiskColour.BLUE;
        }
        else{
            return DiskColour.GREEN;
        }
    }

    /**
     * This method is used to check if the farmer character card is used, and its effect it's active.
     * @param farmer_state {@code True} if the effect is active, {@code False} if not.
     */
    public void setFarmer_state(boolean farmer_state) {
        this.farmer_state = farmer_state;
    }

    /**
     * This method returns the max entrance of students.
     * @return The max entrance of students.
     */
    public int getMaxEntranceStudents() {
        return maxEntranceStudents;
    }


    /**
     * This method returns if the farmer character card has been played.
     * @return {@code True} if the farmer character card has been played.
     */
    public boolean isFarmer_state() {
        return farmer_state;
    }
}


