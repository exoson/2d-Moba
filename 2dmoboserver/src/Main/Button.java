
package Main;

import java.awt.Rectangle;

public abstract class Button extends UIobject
{
    private Rectangle rect;
    
    public Button(int x,int y,int sx,int sy,boolean dis,String texture)
    {
        rect = new Rectangle(x,y,sx,sy);
        init(x,y,sx,sy,BUTTON,dis,texture);
    }
    public Button(int x,int y,int sx,int sy,boolean dis)
    {
        rect = new Rectangle(x,y,sx,sy);
        init(x,y,sx,sy,BUTTON,dis);
    }
    public Button(int x,int y,int size,boolean dis)
    {
        rect = new Rectangle(x,y,size,size);
        init(x,y,size,size,BUTTON,dis);
    }
    public Button(int x,int y,int size,boolean dis,String texture)
    {
        rect = new Rectangle(x,y,size,size);
        init(x,y,size,size,BUTTON,dis,texture);
    }
    
    public abstract void click0();
    
    public abstract void click1();
    
    public Rectangle getrect()
    {
        return rect;
    }
}
