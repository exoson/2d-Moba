
package Main;

import Graphics.Textrenderer;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;

public class Console extends UIobject
{
    private ArrayList<String> msgs;
    private int maxsize;
    
    public Console(float x,float y,float sx,float sy,int maxsize,boolean Displayed)
    {
        this.maxsize = maxsize;
        msgs = new ArrayList<>();
        init(x,y,sx,sy,CONSOLE,Displayed);
        write("sis");
        write("sas");
    }
    public void write(String str)
    {
        msgs.add(str);
        if(msgs.size() > maxsize){
            msgs.remove(0);
        }
    }
    @Override
    public void render()
    {
        if(isDisplayed())
        {
            glPushMatrix();
            {
                for(int i = msgs.size() - 1; i >= 0; i--){
                    Textrenderer.drawString(msgs.get(i), x, y + i * 16, Color.yellow);
                }
            }
            glPopMatrix();
        }
    }
}
