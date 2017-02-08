
package Main;

import Graphics.Textrenderer;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;

public class Textbox extends UIobject
{
    private StringBuilder input;
    private Console console;
    public Textbox(float x,float y,float sx,float sy,boolean displayed,Console con)
    {
        input = new StringBuilder();
        console = con;
        init(x,y,sx,sy,TEXTBOX,displayed);
    }
    
    @Override
    public void update()
    {
        for(Character c : Input.getPressedKeys())
        {
            if((int)c == 13){
                console.write(input.toString());
                input = new StringBuilder();
            }else if ((int)c == 8){
                if(input.length() > 0)
                    input.deleteCharAt(input.length()-1);
            }else{
                input.append(c);
            }
        }
    }
    @Override
    public void render()
    {
        
        if(isDisplayed())
        {
            glPushMatrix();
            {
                Textrenderer.drawString(input.toString(), x, y, Color.yellow);
            }
            glPopMatrix();
        }
    }
}
