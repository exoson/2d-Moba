
package Main;

import Graphics.Animation;
import java.io.Serializable;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

public abstract class Gameobject implements Serializable
{
    public static final int animamt = 4;
    
    public static final int AFKANIM = 0,
            WALKANIM = 1,
            ATTANIM = 2,
            DEATHANIM = 3;
    
    public static final int CHAMPID = 0,MINIONID = 1,PROJECTILEID = 2,BUILDINGID = 3;
    
    protected int curanim = AFKANIM,id;
    private int team;
    protected float sx,sy;
    protected Vector2f pos;
    protected Animation[] anim;
    private String name;
    private boolean[] flags = new boolean[1];
    
    public void update()
    {
        
    }
    
    public void render()
    {
        if((getx() * Game.SQUARESIZE + Game.game.getShiftX() < Display.getWidth() && gety() * Game.SQUARESIZE + Game.game.getShiftY() < Display.getHeight())) return;
        if(!Game.game.curlvl.getMap().getVisiblesqrs()[Game.game.getChampion().getTeam()][(int)(getx() / Game.SQUARESIZE)][(int)(gety() / Game.SQUARESIZE)]) return;
        glPushMatrix();
        {
            glTranslatef(pos.getX() + Game.game.getShiftX(),pos.getY() + Game.game.getShiftY(),0);
            if(getCuranim() == DEATHANIM){
                if(anim[getCuranim()].render()) {
                    remove();
                }
            }else if(anim[getCuranim()].render()) {
                curanim = AFKANIM;
            }
        }
        glPopMatrix();
    }
    
    
    protected void init(float x, float y, float sx, float sy,int id,int team,String name,Animation[] anims)
    {
        this.team = team;
        this.id = id;
        this.name = name;
        this.pos = new Vector2f(x,y);
        this.sx = sx;
        this.sy = sy;
        anim = anims;
    }
    
    public void remove()
    {
        flags[0] = true;
    }
    public boolean getremove()
    {
        return flags[0];
    }
    public int getid()
    {
        return id;
    }
    public String getname()
    {
        return name;
    }
    public float getx()
    {
        return pos.getX();
    }
    public float gety()
    {
        return pos.getY();
    }
    public float getsx()
    {
        return sx;
    }
    public float getsy()
    {
        return sy;
    }
    public void setx(float var)
    {
        pos.setX(var);
    }
    public void sety(float var)
    {
        pos.setY(var);
    }
    public void setsx(float var)
    {
        sx = var;
    }
    public void setsy(float var)
    {
        sy = var;
    }
    protected void setanim(int anim)
    {
        curanim = anim;
    }

    public int getTeam() {
        return team;
    }

    public int getCuranim() {
        return curanim;
    }
}
