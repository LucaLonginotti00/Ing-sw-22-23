package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to request a name from the new player.
 */
public class NameRequest extends MessagesToClient{
    private boolean is_retry;

    public NameRequest(boolean is_retry){
        super(true);
        this.is_retry = is_retry;
    }

    @Override
    public void handleMessage(ViewInterface view) {
        view.displayNicknameRequest(is_retry);
    }

    @Override
    public String toString(){
        return "asking nickname";
    }
}
