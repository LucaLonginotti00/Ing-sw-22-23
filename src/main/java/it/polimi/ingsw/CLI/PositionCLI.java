package it.polimi.ingsw.CLI;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.toServer.PositionReply;

/**
 * This class represent the max number of movement for mother nature.
 */
public class PositionCLI {

    /**
     * This method checks if the selected number of moves are possible.
     * @param client The client.
     * @param upperLimit the max number of moves available.
     */
    public static void choosePositionRequest(Client client,int upperLimit){
        int choice;
        System.out.println("choose the Position:\n (allowed from 0 to "+upperLimit+" excluded)\n");
        choice=InputParser.getInt();

        while(choice<0 || choice>=upperLimit){
            System.out.println("Number not allowed,please choose another number");
            choice=InputParser.getInt();
        }
        client.sendMessageToServer(new PositionReply(choice));
    }
}
