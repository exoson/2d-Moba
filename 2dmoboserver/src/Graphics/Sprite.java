package Graphics;

import Main.EEngine;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sprite implements Serializable
{
    private static HashMap<String,Texture> textures;
    private float sx;
    private float sy;
    private String text;
    
    public static void initTextures()
    {
        textures = new HashMap<>();
        maptexture("playerafk0");
        maptexture("playerafk1");
        maptexture("playeratt0");
        maptexture("playeratt1");
        maptexture("playeratt2");
        maptexture("playeratt3");
        maptexture("playerwalk0");
        maptexture("playerwalk1");
        maptexture("playerwalk2");
        maptexture("playerwalk3");
        maptexture("grass");
        maptexture("road");
        maptexture("shop");
        maptexture("stone");
        maptexture("tower");
        maptexture("towerammo");
        maptexture("lol");
        maptexture("lul");
    }
    
    public Sprite(float sx, float sy,String texture)
    {
        if(texture == null)
        {
            text = "lol";
        }else{
            text = texture;
        }
        this.sx = sx;
        this.sy = sy;
    }
    public Sprite(float sx, float sy)
    {
        this.sx = sx;
        this.sy = sy;
        text = "lol";
    }
    public void render()
    {
        textures.get(text).bind();
        glColor3f(1,1,1);
        
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0,0); glVertex2f(0,0);
            glTexCoord2f(1,0); glVertex2f(sx,0);
            glTexCoord2f(1,1); glVertex2f(sx,sy);
            glTexCoord2f(0,1); glVertex2f(0,sy);
        }
        glEnd();
        
    }
    public void render(float r,float g,float b)
    {
        textures.get(text).bind();
        glColor3f(r,g,b);
        
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0,0); glVertex2f(0,0);
            glTexCoord2f(1,0); glVertex2f(sx,0);
            glTexCoord2f(1,1); glVertex2f(sx,sy);
            glTexCoord2f(0,1); glVertex2f(0,sy);
        }
        glEnd();
    }
    public void render(float r,float g,float b,float a)
    {
        textures.get(text).bind();
        glColor4f(r,g,b,a);
        
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0,0); glVertex2f(0,0);
            glTexCoord2f(1,0); glVertex2f(sx,0);
            glTexCoord2f(1,1); glVertex2f(sx,sy);
            glTexCoord2f(0,1); glVertex2f(0,sy);
        }
        glEnd();
    }
    public void render(float sx,float sy)
    {
        textures.get(text).bind();
        glColor3f(1,1,1);
        
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0,1); glVertex2f(0,0);
            glTexCoord2f(1,1); glVertex2f(sx,0);
            glTexCoord2f(1,0); glVertex2f(sx,sy);
            glTexCoord2f(0,0); glVertex2f(0,sy);
        }
        glEnd();
        
    }
    public float getsx()
    {
        return sx;
    }
    public float getsy()
    {
        return sy;
    }
    public void setsx(float sx)
    {
        this.sx = sx;
    }
    public void setsy(float sy)
    {
        this.sy = sy;
    }
    private static void maptexture(String key){
        try
        {
            Texture texture = TextureLoader.getTexture("PNG", ResourceLoader.getResource("res/textures/" + key + ".png").openStream());
            textures.put(key, texture);
        }
        catch (IOException ex)
        {
            System.out.println(key);
            Logger.getLogger(EEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
