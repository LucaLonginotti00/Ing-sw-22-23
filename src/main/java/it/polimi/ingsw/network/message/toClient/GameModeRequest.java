package it.polimi.ingsw.network.message.toClient;


import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to ask the game mode.
 */
public class GameModeRequest extends MessagesToClient{

    public GameModeRequest() {
        super(true);
    }

    @Override
    public void handleMessage(ViewInterface view) {
        view.displayGameModeRequest();
    }

    @Override
    public String toString(){
        return "Asking the game mode";
    }
}
