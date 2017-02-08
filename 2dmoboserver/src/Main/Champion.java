
package Main;

import Graphics.Animation;
import Graphics.Frame;
import Graphics.Sprite;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Champion extends Statobject
{
    int playerid;
    public Champion(float x,float y,int team,String name,int plid)
    {
        playerid = plid;
        initchamp(name,x,y,team);
    }
    
    @Override
    public void update()
    {
        super.update();
        if(target == null){
            chase();
        }
        if(target != null)
        {
            targetv.setX(target.getx() + target.getsx() / 2);
            targetv.setY(target.gety() + target.getsy() / 2);
            if(Util.dist(getx() + getsx() / 2, gety() + getsy() / 2, targetv.getX(), targetv.getY()) < getAttrange()) {
                if(attdel.over()) {
                    attack();
                }
            }
            else {
                chase();
            }
        }
    }
    
    private void initchamp(String filename,float x,float y,int team)
    {
        BufferedReader r;
        String name = "";
        long attdelay = 1000;
        float hp = 10,armor = 0,magicres = 0,ad = 0,walkspeed = 4,hpregen = 0,attrange = 0,projsx = 0,projsy = 0,projspeed = 0;
        boolean ranged = false;
        Animation[] anims = new Animation[animamt],projanims = new Animation[animamt];
        try {
            r = new BufferedReader(new FileReader(new File("res/champs/" + filename + ".chp")));
            
            name = r.readLine();
            hp = Integer.parseInt(r.readLine().split(":")[1]);
            armor = Integer.parseInt(r.readLine().split(":")[1]);
            magicres = Integer.parseInt(r.readLine().split(":")[1]);
            ad = Integer.parseInt(r.readLine().split(":")[1]);
            walkspeed = Integer.parseInt(r.readLine().split(":")[1]);
            hpregen = Integer.parseInt(r.readLine().split(":")[1]);
            attrange = Integer.parseInt(r.readLine().split(":")[1]);
            String sprname = r.readLine();
            ArrayList<Frame> frames = new ArrayList<>();
            String nam;
            for(int i = 0; i < 3; i++)
            {
                
                if(sprname != null) {
                    while(sprname.startsWith("a"))
                    {
                        nam = sprname.substring(2, sprname.lastIndexOf("."));
                        frames.add(new Frame(new Sprite(Game.SQUARESIZE,Game.SQUARESIZE,nam),Integer.parseInt(sprname.substring(sprname.lastIndexOf(".") + 1))));
                        sprname = r.readLine();
                        if(sprname == null) {
                            break;
                        }
                    }
                }
                anims[i] = new Animation(frames);
                frames = new ArrayList<>();
                sprname = r.readLine();
            }
            anims[DEATHANIM] = anims[0];
            r.close();
        } catch (IOException e) {
        }
        if(!ranged) {
            init(x,y,Game.SQUARESIZE,Game.SQUARESIZE,CHAMPID,team,name,anims,hp,ad,armor,magicres,walkspeed,hpregen,attrange,attdelay);
        }else{
            init(x,y,Game.SQUARESIZE,Game.SQUARESIZE,CHAMPID,team,name,anims,hp,ad,armor,magicres,walkspeed,hpregen,attrange,attdelay,projsx,projsy,projspeed,projanims);
        }
    }
    
}
