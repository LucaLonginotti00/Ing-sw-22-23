package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.view.ViewInterface;

import java.util.List;

/**
 * Message to choose a cloud.
 */
public class ChooseCloudRequest extends MessagesToClient{
    List<Cloud> clouds;
    public ChooseCloudRequest(List<Cloud> clouds) {
        super(true);
        this.clouds=clouds;
    }

    @Override
    public void handleMessage(ViewInterface view) { view.displayChooseCloudRequest(clouds);}

    @Override
    public String toString() { return "Asking to choose a cloud";}
}
