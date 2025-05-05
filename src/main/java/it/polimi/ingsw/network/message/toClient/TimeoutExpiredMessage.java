package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to notify that the timeout has expired.
 */
public class TimeoutExpiredMessage extends MessagesToClient{
    public TimeoutExpiredMessage(){
        super(false);
    }

    @Override
    public void handleMessage(ViewInterface view){
        view.displayTimeoutCloseConnection();
    }
}
