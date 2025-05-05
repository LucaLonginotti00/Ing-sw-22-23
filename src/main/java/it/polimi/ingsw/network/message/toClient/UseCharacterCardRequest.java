package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to request the use of a character card.
 */
public class UseCharacterCardRequest extends MessagesToClient{

    public UseCharacterCardRequest(){super(true);}

    @Override
    public void handleMessage(ViewInterface view) {
        view.UseCharacterCard();}
}
