
package Main;

import java.awt.Rectangle;

public class Physics 
{
    public static Gameobject checkcollision(Gameobject go1, Gameobject go2)
    {
        return checkcollision(new Rectangle((int) (go1.getx()),(int)(go1.gety()),(int)go1.getsx(),(int)go1.getsy()), go2);
    }
    public static Gameobject checkcollision(Rectangle r1, Gameobject go2)
    {
        Rectangle r2 = new Rectangle((int)go2.getx(),(int)go2.gety(),(int)go2.getsx(),(int)go2.getsy());
        
        boolean res = r1.intersects(r2);
        
        if(res)
            return go2;
        else
            return null;
    }
    public static boolean rectangleincpoint(Rectangle r,float x,float y)
    {
        return r.contains(x, y);
    }
}
