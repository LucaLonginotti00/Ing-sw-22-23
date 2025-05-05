package it.polimi.ingsw.CLI;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.DiskColour;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.toServer.ChooseCloudReply;

import java.util.List;

/**
 * This class represents the CLI for the clouds.
 */
public class CloudCLI {

    /**
     * This method lets the player choose a cloud to take the students.
     * @param client The client.
     * @param clouds The list of clouds.
     */
    public static void chooseCloud(Client client, List<Cloud> clouds){
        int choice;
        System.out.println("choose the number of the cloud you want to choose:\n");
        for(int i=0;i<clouds.size();i++){
            System.out.println("Cloud["+i+"]\n");
            for(int j=0;j<5;j++){
                System.out.println(DiskColour.printColour(j) +"["+clouds.get(i).getArr_students()[j]+"]");
            }
            System.out.println("\n");
        }

        choice=InputParser.getInt();

        while(choice<0 || choice>=clouds.size()){
            System.out.println("Number not valid,please choose a number from the list");
            choice=InputParser.getInt();
        }
        client.sendMessageToServer(new ChooseCloudReply(choice));
    }
}
