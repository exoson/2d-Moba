
package Graphics;

import java.io.Serializable;

public class Frame implements Serializable
{
    private int length;
    private Sprite spr;
    private int numDisplayed;
    
    public Frame(Sprite spr, int length)
    {
        this.spr = spr;
        this.length = length;
        numDisplayed = 0;
    }
    
    public boolean render()
    {
        spr.render();
        numDisplayed++;
        
        if(numDisplayed >= length)
        {
            numDisplayed = 0;
            return true;
        }
        return false;
    }
    public boolean render(float r,float g,float b,float a)
    {
        spr.render(r,g,b,a);
        numDisplayed++;
        
        if(numDisplayed >= length)
        {
            numDisplayed = 0;
            return true;
        }
        return false;
    }
}
