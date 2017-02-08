
package Main;

import Graphics.Animation;
import Graphics.Frame;
import Graphics.Sprite;
import java.util.ArrayList;

public class Tower extends Building
{
    private static final float ARM = 10,MAGRES = 10,ATTRANGE = 300,PROJSIZE = 16,PROJSPEED = 6;
    private static final long ATTSPEED = 1200;
    public Tower(float x,float y,float attdmg,float hp,int team)
    {
        Animation[] anims = new Animation[animamt];
        ArrayList<Frame> afkanim = new ArrayList<>();
        afkanim.add(new Frame(new Sprite(Game.SQUARESIZE,Game.SQUARESIZE,"tower"),100));
        anims[AFKANIM] = new Animation(afkanim);
        anims[ATTANIM] = new Animation(afkanim);
        anims[DEATHANIM] = new Animation(afkanim);
        Animation[] projanims = new Animation[animamt];
        ArrayList<Frame> projframes = new ArrayList<>();
        projframes.add(new Frame(new Sprite(PROJSIZE,PROJSIZE,"towerammo"),100));
        projanims[AFKANIM] = new Animation(projframes);
        projanims[DEATHANIM] = new Animation(projframes);
        this.init(x, y, Game.SQUARESIZE, Game.SQUARESIZE,team, "Tower", anims, hp, attdmg, ARM, MAGRES, ATTRANGE, ATTSPEED,PROJSIZE,PROJSIZE,PROJSPEED,projanims);
    }
    @Override
    public void update()
    {
        super.update();
        
        if(target == null) {
            look();
        }
        if(target != null){
            if(Util.dist(getx(), gety(), target.getx(), target.gety()) < getAttrange()) {
                if(attdel.over()) {
                    attack();
                }
            }else {
                chase();
            }
            if(Util.dist(getx(), gety(), target.getx(), target.gety()) > SRANGE){
                target = null;
            }
        }
    }
    private void look()
    {
        ArrayList<Gameobject> ob = Game.sphereCollide(getx(), gety(), SRANGE);
        for(Gameobject o : ob){
            if(o.getid() == CHAMPID) {
                target = (Statobject) o;
                break;
            }
        }
    }
}
