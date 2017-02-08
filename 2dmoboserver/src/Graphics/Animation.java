
package Graphics;

import java.io.Serializable;
import java.util.ArrayList;

public class Animation implements Serializable
{
    private ArrayList<Frame> frames;
    private int curframe;
    
    public Animation(ArrayList<Frame> frames)
    {
        this.frames = frames;
        curframe = 0;
    }
    public boolean render()
    {
        Frame temp = frames.get(curframe);
        
        if(temp.render())
        {
            curframe++;
            curframe %= frames.size();
            if(curframe == 0) return true; else return false;
        }
        return false;
    }
    public boolean render(float r,float g,float b,float a)
    {
        Frame temp = frames.get(curframe);
        
        if(temp.render(r,g,b,a))
        {
            curframe++;
            curframe %= frames.size();
            if(curframe == 0) return true; else return false;
        }
        return false;
    }
}
