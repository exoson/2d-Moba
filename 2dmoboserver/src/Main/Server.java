
package Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable{
    private ArrayList<Socket> recentconnections = new ArrayList<>();
    private ArrayList<ClientServer> removedconnections = new ArrayList<>();
    private ArrayList<ClientServer> cs = new ArrayList<>();
    boolean running;
    private int playeramt = 0;
    
    public void broadcast(Object msg)
    {
        for(ClientServer c : cs){
            c.sendMsg(msg);
        }
    }
    public void stop()
    {
        running = false;
        for(ClientServer c : cs)
        {
            c.stop();
        }
    }
    @Override
    public void run()
    {
        try (ServerSocket ss = new ServerSocket(8000)) {
            ss.setSoTimeout(1000);
            running = true;
            System.out.println("Server running");
            while(running){
                Socket client = null;
                try {
                    client = ss.accept();
                    recentconnections.add(client);
                }
                catch(IOException e){
                    
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void removeConnection(ClientServer c)
    {
        removedconnections.add(c);
    }
    public void checkConnects()
    {
        for(Socket s : recentconnections){
            try {
                initClient(s);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        recentconnections.removeAll(recentconnections);
        for(ClientServer s : removedconnections){
                cs.remove(s);
        }
        removedconnections.removeAll(removedconnections);
    }
    private void initClient(Socket client) throws IOException
    {
        ClientServer clientserver = new ClientServer(this, client);
        System.out.println("Client connected " + client.getInetAddress().toString());
        new Thread(clientserver).start();
        Champion c = Game.game.addChamp(0,"seppo",playeramt);
        clientserver.sendMsg("CID:" + playeramt);
        clientserver.setChamp(c);
        for(Gameobject go : Game.game.getobjects()){
            clientserver.sendMsg(go);
        }
        for(Gameobject go : Game.game.getadded()){
            clientserver.sendMsg(go);
        }
        playeramt++;
        cs.add(clientserver);
    }
}
