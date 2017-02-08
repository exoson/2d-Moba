
package Main;

import static Main.Gameobject.BUILDINGID;
import static Main.Gameobject.CHAMPID;
import static Main.Gameobject.MINIONID;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;

public class Game implements State
{
    public static final float SQUARESIZE = 64;
    
    private Server server;
    private Client client;
    
    public static Game game;
    public static Menu menu;
    public Level curlvl;
    private ArrayList<Gameobject> objects;
    private ArrayList<Gameobject> remove;
    private ArrayList<Gameobject> added;
    private Ui ui;
    private float shiftX,shiftY;
    public Champion player;
    private boolean isServer;
    
    public Game(boolean isServer)
    {
        this.isServer = isServer;
        if(isServer){
            server = new Server();
            menu = new Menu();
            objects = new ArrayList<>();
            remove = new ArrayList<>();
            added = new ArrayList<>();
            ui = new Ui();
            initui();
            curlvl = new Level("map0");
            objects.add(new Meleemin(100 + 500,100 + 500,0));
            objects.add(new Tower(600,600,5,10,0));
        }else{
            try {
                client = new Client();
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            menu = new Menu();
            objects = new ArrayList<>();
            added = new ArrayList<>();
            remove = new ArrayList<>();
            ui = new Ui();
            curlvl = new Level("map0");
            initui();
            
        }
    }
    public void startServer()
    {
        new Thread(getServer()).start();
    }
    public void stopServer()
    {
        getServer().stop();
    }
    public void connectServer()
    {
        new Thread(client).start();
    }
    public void disconnectServer()
    {
        client.stop();
    }
    @Override
    public void update()
    {
        if(isIsServer()) {
            serverUpdate();
        }else {
            clientUpdate();
        }
    }
    private void clientUpdate()
    {
        getin();
        /*for(Gameobject go : objects)
        {
            go.update();
            if(go.getremove()) {
                remove.add(go);
            }
        }*/
        for(Gameobject a : added){
            objects.add(a);
        }
        added.removeAll(added);
        
        for(Gameobject go : remove) {
            objects.remove(go);
        }
        remove.removeAll(remove);
        curlvl.update();
        ui.update();
    }
    private void serverUpdate()
    {
        getin();
        for(Gameobject go : objects) 
        {
            go.update();
            if(go.getremove()) {
                remove.add(go);
            }
        }
        
        for(Gameobject go : added){
            objects.add(go);
        }
        added.removeAll(added);
        for(Gameobject go : remove) {
            objects.remove(go);
        }
        remove.removeAll(remove);
        curlvl.update();
        ui.update();
        sendData();
        getServer().checkConnects();
    }
    private void sendData()
    {
        for(Gameobject go : objects) {
            String msg = "GO:" + objects.indexOf(go) + ":" + go.getx() + ":" + go.gety() + ":" + go.getremove() + ":" + go.getCuranim();
            if(go.getid() == Gameobject.CHAMPID){
                Champion c = (Champion)go;
                msg += ":" + c.getStats();
            }else if(go.getid() == Gameobject.MINIONID){
                Minion m = (Minion)go;
                msg += ":" + m.getStats();
            }else if(go.getid() == Gameobject.BUILDINGID){
                Building b = (Building)go;
                msg += ":" + b.getStats();
            }else if(go.getid() == Gameobject.PROJECTILEID){
            }
            getServer().broadcast(msg);
        }
        boolean[][][] vissqrs = getLevel().getMap().getVisiblesqrs();
        StringBuilder sqrs = new StringBuilder("VS:");
        
        for(int team = 0; team < vissqrs.length; team++){
            for(int x = 0; x < vissqrs[team].length;x++){
                for (int y = 0; y < vissqrs[team][x].length; y++) {
                    sqrs.append(vissqrs[team][x][y] ? '1' : '0');
                }
            }
        }
        getServer().broadcast(sqrs.toString());
    }
    public Champion addChamp(int team,String name,int playerid)
    {
        Champion champ = new Champion(team == 0 ? getLevel().getSpawnx1() : getLevel().getSpawnx2(),
                team == 0 ? getLevel().getSpawny1() : getLevel().getSpawny2(),team,name,playerid);
        Game.game.objects.add(champ);
        server.broadcast(champ);
        return champ;
    }
    public void setTarget(int obid,float x,float y)
    {
        ((Statobject)objects.get(obid)).setTargetv(new Vector2f(x,y));
        ((Statobject)objects.get(obid)).setTarget(null);
    }
    public void setTarget(int obid,int tarid)
    {
        ((Statobject)objects.get(obid)).setTarget((Statobject)objects.get(tarid));
    }
    public void updateGo(String[] params)
    {
        if(objects.size() - 1 < Integer.parseInt(params[1])) return;
        Gameobject go = objects.get(Integer.parseInt(params[1]));
        go.setx(Float.parseFloat(params[2]));
        go.sety(Float.parseFloat(params[3]));
        if(Boolean.getBoolean(params[4]))go.remove();
        go.curanim = Integer.parseInt(params[5]);
    }
    public void updateVisiblesqrs(String[] params)
    {
        boolean[][][] sqrs = getLevel().getMap().getVisiblesqrs();
        for(int team = 0; team < sqrs.length;team++){
            for(int x = 0; x < sqrs[team].length; x++){
                for(int y = 0; y < sqrs[team][x].length; y++){
                     sqrs[team][x][y] = params[1].charAt(y + x * sqrs[team][x].length + team * sqrs[team].length * sqrs[team][x].length) == '1' ? true : false;
                }
            }
        }
        getLevel().getMap().setVisiblesqrs(sqrs);
    }
    public void setChampion(String[] params)
    {
        player = (Champion)objects.get(Integer.parseInt(params[1]));
    }
    private void getin()
    {
        if(!isServer){
            if(Input.getmousex() > Display.getWidth() - 50 || Input.getKey(Input.KEY_RIGHT)) {
                Movescreen(-10 * Time.getdelta() * Statobject.DAMPING,0);
            }
            if(Input.getmousey() > Display.getHeight() - 50 || Input.getKey(Input.KEY_DOWN)) {
                Movescreen(0,-10 * Time.getdelta() * Statobject.DAMPING);
            }
            if(Input.getmousex() < 50 || Input.getKey(Input.KEY_LEFT)) {
                Movescreen(10 * Time.getdelta() * Statobject.DAMPING,0);
            }
            if(Input.getmousey() < 50 || Input.getKey(Input.KEY_UP)) {
                Movescreen(0,10 * Time.getdelta() * Statobject.DAMPING);
            }
            if(Input.getKey(Input.KEY_SPACE))
            {
                Game.game.setShiftX(-player.getx() + Display.getWidth() / 2);
                Game.game.setShiftY(-player.gety() + Display.getHeight() / 2);
                if(Game.game.getShiftX() > 0) {
                    Game.game.setShiftX(0);
                }
                if(Game.game.getShiftY() > 0) {
                    Game.game.setShiftY(0);
                }
                if(Game.game.getShiftX() < -Map.MAPSIZE * Game.SQUARESIZE + Display.getWidth()) {
                    Game.game.setShiftX(-Map.MAPSIZE * Game.SQUARESIZE + Display.getWidth());
                }
                if(Game.game.getShiftY() < -Map.MAPSIZE * Game.SQUARESIZE + Display.getHeight()) {
                    Game.game.setShiftY(-Map.MAPSIZE * Game.SQUARESIZE + Display.getHeight());
                }
            }
            if(Input.getMousePressed(1))
            {
                player.setTargetv(new Vector2f(Input.getmousex() - Game.game.getShiftX(),Input.getmousey() - Game.game.getShiftY()));
                player.setTarget(null);
                ArrayList<Gameobject> gos = Game.sphereCollide(player.getTargetv().getX(), player.getTargetv().getY(), Game.SQUARESIZE);
                for(Gameobject go : gos)
                {
                    if(go.getid() == CHAMPID || go.getid() == MINIONID || go.getid() == BUILDINGID) {
                        if(go != player) {
                            player.setTarget((Statobject)go);
                            System.out.println(player.getTarget().getname());
                            break;
                        }
                    }
                }
                if(player.getTarget() != null){
                    client.sendMsg("TO:" + objects.indexOf(player.target));
                }else {
                    client.sendMsg("TV:" + player.getTargetv().getX() + ":" + player.getTargetv().getY());
                }
            }
        }
    }
    @Override
    public void render()
    {
        if(!isIsServer()){
            curlvl.render();
            for(Gameobject go : objects) {
                go.render();
            }
        }
        ui.render();
    }
    public void addGameobject(Gameobject go)
    {
        added.add(go);
    }
    public void adduiobj(UIobject uo)
    {
        ui.addobject(uo);
    }
    public Champion getChampion()
    {
        return player;
    }
    public Level getLevel()
    {
        return curlvl;
    }
    public Menu getmenu()
    {
        return menu;
    }
    public Ui getui()
    {
        return ui;
    }
    public ArrayList<Gameobject> getobjects()
    {
        return objects;
    }
    public ArrayList<Gameobject> getadded()
    {
        return added;
    }
    public int getstate()
    {
        return EEngine.curstate;
    }
    public void setstate(int state)
    {
        EEngine.curstate = state;
    }
    public static ArrayList<Gameobject> sphereCollide(float x,float y,float radius)
    {
        ArrayList<Gameobject> res = new ArrayList<>();
        
        for(Gameobject go : game.getobjects())
        {
            if(Util.dist(go.getx() + go.sx / 2, go.gety() + go.sy / 2, x, y) < radius) {
                res.add(go);
            }
        }
        
       return res;
    }
    private void initui() 
    {
        if(isServer){
            Console con = new Console(100,100,10,10,5,true);
            ui.addobject(new Textbox(100,150,10,10,true,con));
            ui.addobject(con);
        }else {
            ui.addobject(new Minimap(Display.getWidth() - 200,Display.getHeight() - 200,200,200,true,curlvl.getMap(),"shop"));
        }
    }
    public float getShiftX() {
        return shiftX;
    }
    public float getShiftY() {
        return shiftY;
    }
    public void Movescreen(float shiftX,float shiftY) {
        this.shiftY += shiftY;
        this.shiftX += shiftX;
        if(Game.game.getShiftX() > 0) {
            Game.game.setShiftX(0);
        }
        if(Game.game.getShiftY() > 0) {
            Game.game.setShiftY(0);
        }
        if(Game.game.getShiftX() < -Map.MAPSIZE * Game.SQUARESIZE + Display.getWidth()) {
            Game.game.setShiftX(-Map.MAPSIZE * Game.SQUARESIZE + Display.getWidth());
        }
        if(Game.game.getShiftY() < -Map.MAPSIZE * Game.SQUARESIZE + Display.getHeight()) {
            Game.game.setShiftY(-Map.MAPSIZE * Game.SQUARESIZE + Display.getHeight());
        }
    }

    void setShiftX(float f) {
        shiftX = f;
    }

    void setShiftY(float f) {
        shiftY = f;
    }

    public Server getServer() {
        return server;
    }

    public boolean isIsServer() {
        return isServer;
    }
}
