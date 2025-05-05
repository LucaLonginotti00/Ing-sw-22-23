package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to choose a character card.
 */
public class ChooseCharacterCardRequest extends MessagesToClient{
    Player player;
    CharacterCard[] cc;
    public ChooseCharacterCardRequest(Player player,CharacterCard[] cc){
        super(true);
        this.player=player;
        this.cc=cc;
    }
    @Override
    public void handleMessage(ViewInterface view) { view.ChooseCharacterCard(player,cc);}

    @Override
    public String toString() { return "Asking to choose a character card";}
}
