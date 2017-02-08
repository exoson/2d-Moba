
package Main;

import Graphics.Sprite;
import static org.lwjgl.opengl.GL11.*;

public class UIobject
{
    public static final int 
            BUTTON = 0,
            PANEL = 1,
            TEXT = 2,
            MINMAP = 3;
    protected float x,y,sx,sy;
    protected Sprite spr;
    protected int type;
    private boolean displayed;
    public void update()
    {
        
    }
    public void getin()
    {
        
    }
    public void render()
    {
        if(isDisplayed())
        {
            glPushMatrix();
            {
                glTranslatef(x,y,0);
                spr.render();
            }
            glPopMatrix();
        }
    }
    
    protected void init(float x,float y,float sx,float sy,int type,boolean displayed)
    {
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        this.type = type;
        setDisplayed(displayed);
        this.spr = new Sprite(sx,sy);
    }
    protected void init(float x,float y,float sx,float sy,int type,boolean displayed, String texture)
    {
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        this.type = type;
        setDisplayed(displayed);
        this.spr = new Sprite(sx,sy,texture);
    }
    
    public void setX(float x)
    {
        this.x = x;
    }
    public void setY(float y)
    {
        this.y = y;
    }
    public void setSX(float sx)
    {
        spr.setsx(sx);
    }
    public void setSY(float sy)
    {
        spr.setsy(sy);
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public float getSX()
    {
        return spr.getsx();
    }
    public float getSY()
    {
        return spr.getsy();
    }
    public int gettype()
    {
        return type;
    }

    /**
     * @return the displayed
     */
    public boolean isDisplayed() {
        return displayed;
    }

    /**
     * @param displayed the displayed to set
     */
    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }
}
