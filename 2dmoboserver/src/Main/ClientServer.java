
package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientServer implements Runnable
{
    private final Socket clientSocket;
    private final BufferedReader in;
    private final ObjectOutputStream oout;
    private final Server s;
    private boolean running;
    private Champion champ;
    
    public ClientServer(Server s, Socket client) throws IOException{
        this.s = s;
        client.setSoTimeout(10);
        client.setKeepAlive(true);
        clientSocket = client;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        oout = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        running = true;
        while(running && !clientSocket.isClosed()){
            String input = "";
            try {
                input = in.readLine();
                if(input != null){
                    String[] inSplitted = input.split(":");
                    switch (inSplitted[0]){
                        case "quit": 
                            stop();
                            break;
                        case "TO":
                            champ.setTarget((Statobject)Game.game.getobjects().get(Integer.parseInt(inSplitted[1])));
                            break;
                        case "TV":
                            champ.setTargetv(new Vector2f(Float.parseFloat(inSplitted[1]),Float.parseFloat(inSplitted[2])));
                            break;
                    }
                }
                
            } catch (IOException ex) {
            }
        }
    }
    public void sendMsg(Object msg)
    {
        try {
            oout.writeObject(msg);
            oout.flush();
        } catch (IOException ex) {
            stop();
        }
    }
    public void stop()
    {
        running = false;
        try {
            Game.game.getServer().removeConnection(this);
            clientSocket.close();
            System.out.println("Client disconnected: " + clientSocket.getInetAddress().toString());
            champ.remove();
        } catch (IOException ex) {
            Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Champion getChamp() {
        return champ;
    }
    public void setChamp(Champion champ) {
        this.champ = champ;
    }
}
