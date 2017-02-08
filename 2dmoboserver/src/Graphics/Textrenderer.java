
package Graphics;

import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;



public class Textrenderer
{
    private static UnicodeFont font;
    
    public static void init()
    {
        font = new UnicodeFont(new Font("Times New Roman",Font.PLAIN,24));
        font.addAsciiGlyphs();
        font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        try 
        {
            font.loadGlyphs();
        } 
        catch (SlickException ex) 
        {
            Logger.getLogger(Textrenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void drawString(String s,float x,float y,Color color)
    {
        glPushMatrix();
        {
            font.drawString(x, y, s,color);
        }
        glPopMatrix();
    }
    
}