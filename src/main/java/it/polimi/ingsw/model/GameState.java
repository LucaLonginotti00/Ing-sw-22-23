package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent the game state.
 * It creates the game table and the class player.
 * It also includes the flags for expert mode, the teams if the game is 4 players and the name of the players.
 * The class has the current turn.
 */
public class GameState {
    private int n_players;
    private Player[] players;
    private GameTable GT;
    private Turn turn;
    private boolean expert_mode;
    private int curr_player;

    private String[] names;
    private int[] wizard;
    private List<Player> lPlayers;

    /**
     * Constructor of the class.
     * @param n_players Number of players in the game.
     * @param names Strings containing the names of the players.
     * @param wizard Array of integers containing the numbers of the wizards to be assigned to each player.
     * @param expert_mode {@code True} if expert mode is enabled, {@code False} if not.
     * @param curr_player The current player.
     */
    public GameState(int n_players, String[] names, int wizard[], boolean expert_mode, int curr_player, List<Player> p_l){
        this.n_players = n_players;
        this.names = names;
        this.wizard = wizard;
        this.expert_mode = expert_mode;
        this.curr_player=curr_player;
        this.lPlayers = p_l;
        turn = new Turn();

        players = new Player[n_players];
        for(int i=0; i<n_players;i++){
            players[i] = p_l.get(i);
        }



        GT = new GameTable(n_players, turn);
        GT.setPl(lPlayers);
    }

    /**
     * This method returns the game table.
     * @return The game table.
     */
    public GameTable getGT() {
        return GT;
    }

    /**
     * This method returns the array of players.
     * @return The array of players.
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * This method returns the list of players.
     * @return The list of players.
     */
    public List<Player> getlPlayers() {
        return lPlayers;
    }

    /**
     * This method sets the current player.
     * @param player The current player.
     */
    public void setCurr_player(int player){
        curr_player=player;
    }


    /**
     * This method returns the current player.
     * @return The current player.
     */
    public int getCurr_player() {
        return curr_player;
    }
}
