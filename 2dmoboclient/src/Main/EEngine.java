package Main;

import Graphics.Sprite;
import Graphics.Textrenderer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

public class EEngine 
{
    static String gamename = "2dMobo";
    public static final int GAME = 0,MENU = 1;
    private static State[] states = new State[2];
    public static int curstate = 0;
    public static boolean running;
    
    public static void main(String[] args)
    {
        initDisplay();
        initGL();
        initgame();
        gameLoop();
        cleanup();
    }

    private static void gameLoop()
    {
        Time.init();
        running = true;
        while(running)
        {
            Time.update();
            Input.update();
            update();
            render();
        }
    }
    public static void gameloopapp()
    {
        update();
        render();
    }
    private static void initgame()
    {
        Game.game = new Game();
        Game.game.connectServer();
        Textrenderer.init();
        Sprite.initTextures();
        Game.menu = new Menu();
        states[GAME] = Game.game;
        states[MENU] = Game.menu;
    }
    private static void initGL()
    {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0,Display.getWidth(),Display.getHeight(),0,1,-1);
        glMatrixMode(GL_MODELVIEW);

        glClearColor(0,0,0,1);
        
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
    }

    public static void cleanup()
    {
        Game.game.disconnectServer();
        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
    }

    private static void initDisplay()
    {
        try
        {
            Display.setFullscreen(false);
            Display.setTitle(gamename);
            Display.create();
            Display.setVSyncEnabled(false);
            Keyboard.create();
            Mouse.create();
        }
        catch (LWJGLException ex)
        {
            Logger.getLogger(EEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void update() 
    {
        states[curstate].update();
    }
    private static void render() 
    {
        glClear(GL_COLOR_BUFFER_BIT);
        glLoadIdentity();
        
        states[curstate].render();
        
        Display.update();
        Display.sync(60);
    }
    public static void init()
    {
        initGL();
        initgame();
    }
}
