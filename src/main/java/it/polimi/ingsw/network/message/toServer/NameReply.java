package it.polimi.ingsw.network.message.toServer;
import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;



/**
 * Message to notify the chosen name.
 */
public class NameReply implements MessagesToServer{

    private final String nickname;

    public NameReply(String nickname){
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {

        clientHandler.setNickname(nickname);

    }
    @Override
    public String toString() {
        return "Received name: " + nickname;
    }
}
