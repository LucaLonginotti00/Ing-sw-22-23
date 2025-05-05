package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.network.message.ConnectionMessage;
import it.polimi.ingsw.network.message.toClient.MessagesToClient;
import it.polimi.ingsw.network.message.toClient.TimeoutExpiredMessage;
import it.polimi.ingsw.view.View;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class manages the connection of the client with the server.
 */
public class Client implements ClientInterface {

    private Optional<String> name;
    private Optional<GameMode> expert_mode ;
    private boolean valid_name = false ;
    public static final int HEARTBEAT = 5000; //A ping message is sent every 5 seconds
    private View view;
    private Socket socket;
    private final String IPaddress;
    private final int port;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final Thread packetReceiver;
    private final Thread serverObserver;
    private BlockingQueue<Object> incomingPackets;
    private final AtomicBoolean connected = new AtomicBoolean(false);
    private final Thread pinger;
    private boolean connectionClosed = false;

    /**
     * Constructor of the class.
     * It builds the threads and the object to start the game.
     * @param IPaddress The IP of the server.
     * @param port The port of the server.
     * @param view the instance of a {@link it.polimi.ingsw.CLI}.
     */
    public Client(String IPaddress, int port, View view){
        this.name = Optional.empty();
        this.expert_mode = Optional.empty();
        this.IPaddress = IPaddress;
        this.port = port;
        this.view = view;
        this.packetReceiver = new Thread(this::manageIncomingPackets);
        this.serverObserver = new Thread(this::waitMessages);

        this.pinger = new Thread(() -> {
            while (connected.get()){
                try {
                    Thread.sleep(HEARTBEAT);
                    sendMessageToServer(ConnectionMessage.PING);
                } catch (InterruptedException e){
                    closeSocket();
                    break;
                }
            }
        });
    }

    public Client(String IPaddress, int port, View view, Optional<GameMode> expert_mode, Optional<String> name){
        this.expert_mode = expert_mode;
        this.name = name;
        this.valid_name = name.isPresent();
        this.IPaddress = IPaddress;
        this.port = port;
        this.view = view;
        this.packetReceiver = new Thread(this::manageIncomingPackets);
        this.serverObserver = new Thread(this::waitMessages);

        this.pinger = new Thread(() -> {
            while (connected.get()){
                try {
                    Thread.sleep(HEARTBEAT);
                    sendMessageToServer(ConnectionMessage.PING);
                } catch (InterruptedException e){
                    closeSocket();
                    break;
                }
            }
        });
    }

    public void start() throws IOException {
        socket = new Socket();
        this.incomingPackets = new LinkedBlockingQueue<>();

        socket.connect(new InetSocketAddress(IPaddress, port));
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        connected.set(true);
        if(!packetReceiver.isAlive()){
            packetReceiver.start();
        }
        serverObserver.start();
    }


    /**
     * Manage the messages stored in the queue by Client.waitMessages()
     */
    public void manageIncomingPackets(){
        while (connected.get()){
            Object message;
            try {
                message = incomingPackets.take();
            } catch (InterruptedException e){
                closeSocket();
                return;
            }

            ((MessagesToClient) message).handleMessage(view);
        }
    }


    /**
     * This method receives messages from the server and add them to a queue of messages.
     * It is also used to manage disconnection and ping messages.
     */
    public void waitMessages(){
        try {
            while (connected.get()){
                Object message = null;
                message = inputStream.readObject();

                if(message==ConnectionMessage.CONNECTION_CLOSED) {
                    closeSocket();
                }
                if (message instanceof TimeoutExpiredMessage){
                    connected.set(false);
                    pinger.interrupt();
                    packetReceiver.interrupt();
                    ((TimeoutExpiredMessage) message).handleMessage(view);
                    return;
                } else if (message!=null && !(message == ConnectionMessage.PING)) {
                    incomingPackets.add(message);
                }
            }
        }catch (IOException | ClassNotFoundException e){
            pinger.interrupt();
            packetReceiver.interrupt();
        }
    }

    @Override
    public synchronized void sendMessageToServer(Serializable message){
        if (connected.get()){
            try {
                outputStream.writeObject(message);
                outputStream.flush();

            }catch (IOException e){
                closeSocket();
            }
        }
    }

    /**
     * This method closes the socket.
     */
    public void closeSocket(){

        if (connectionClosed)
            return;
        connectionClosed = true;
        boolean was_connected= connected.getAndSet(false);
        if (!was_connected)
            return;
        if (packetReceiver.isAlive())
            packetReceiver.interrupt();
        if (serverObserver.isAlive())
            serverObserver.interrupt();
        view.handleCloseConnection(was_connected);
        try {
            inputStream.close();
        } catch (IOException e){}
        try {
            outputStream.close();
        } catch (IOException e){}
        try {
            socket.close();
        } catch (IOException e){}
    }

    /**
     * This method closes all the running threads.
     */
    public void closeThreads(){
        packetReceiver.interrupt();
        pinger.interrupt();
        serverObserver.interrupt();
        try {
            inputStream.close();
        } catch (IOException e){}
        try {
            outputStream.close();
        } catch (IOException e){}
        try {
            socket.close();
        } catch (IOException e){}
    }

    public void setName(String name) {
        this.name = Optional.of(name);
    }

    public void setGameMode(GameMode expert_mode) {
        this.expert_mode = Optional.of(expert_mode);
    }

    public Optional<String> getName() {
        return name;
    }

    public Optional<GameMode> getGameMode() {
        return expert_mode;
    }
}
