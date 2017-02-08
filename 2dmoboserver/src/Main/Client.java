
package Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable{
    
    private final Socket client;
    private final PrintWriter out;
    private final ObjectInputStream oin;
    private boolean running;
    private int playerid;
    
    public Client() throws IOException
    {
        client = new Socket("localhost",8000);
        out = new PrintWriter(client.getOutputStream()); 
        oin = new ObjectInputStream(client.getInputStream());
    }
    
    public void stop()
    {
        running = false;
        sendMsg("quit");
        try {
            client.close();
            out.close();
            oin.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void sendMsg(String msg)
    {
        out.println(msg);
        out.flush();
    }
    public ArrayList<Gameobject> getAddedObjects()
    {
        ArrayList<Gameobject> temp = null;
        
        
        return temp;
    }
    @Override
    public void run() {
        running = true;
        while(running && !client.isClosed())
        {
            try {
                String input = null;
                try {
                    Object in = oin.readObject();
                    try{
                        input = (String) in;

                        String[] inSplitted = input.split(":");
                        switch (inSplitted[0]){
                            case "GO":
                                Game.game.updateGo(inSplitted);
                                break;
                            case "VS":
                                Game.game.updateVisiblesqrs(inSplitted);
                                break;
                            case "CID":
                                playerid = Integer.parseInt(inSplitted[1]);
                                break;
                        }
                    } catch(ClassCastException ce){
                        if(Game.game.getChampion() == null && ((Gameobject)in).getid() == Gameobject.CHAMPID && ((Champion)in).playerid == playerid){
                            Game.game.player = (Champion)in;
                        }
                        Game.game.addGameobject((Gameobject)in);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
