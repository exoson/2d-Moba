
package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

public class Map
{
    public static final int MAPSIZE = 100;
    
    private Square[][] squares;
    private boolean[][][] visiblesqrs;
    private float spawnx1,spawny1,spawnx2,spawny2;
    
    public Map(String mapname)
    {
        initmap(mapname,MAPSIZE,MAPSIZE);
        visiblesqrs = new boolean[2][MAPSIZE][MAPSIZE];
    }
    public void update()
    {
        checkVisibilitya();
        
    }
    private void checkVisibilitya()
    {
        for(int x = 0; x < MAPSIZE;x++){
            for(int y = 0; y < MAPSIZE;y++){
                visiblesqrs[0][x][y] = false;
                visiblesqrs[1][x][y] = false;
            }
        }
        for(int x = 0; x < MAPSIZE;x++){
            for(int y = 0; y < MAPSIZE;y++){
                visiblesqrs[0][x][y] = checkVisibility(0,x,y);
                visiblesqrs[1][x][y] = checkVisibility(1,x,y);
            }
        }
    }
    private boolean checkVisibility(int team,int x,int y)
    {
        if(getVisiblesqrs()[team][x][y]) return true;
        for(Gameobject go : Game.game.getobjects()){
            if(go.getTeam() != team) continue;
            if(Util.dist(x * Game.SQUARESIZE, y * Game.SQUARESIZE, go.getx(), go.gety()) > Statobject.SRANGE) continue;
            if(Util.los(go,x * Game.SQUARESIZE,y * Game.SQUARESIZE)) return true;
        }
        return false;
    }
    public void render()
    {
        for(int x = 0; x < MAPSIZE; x++) 
        {
            for(int y = 0;y < MAPSIZE; y++)
            {
                if(x * Game.SQUARESIZE + Game.game.getShiftX() < Display.getWidth() && y * Game.SQUARESIZE + Game.game.getShiftY() < Display.getHeight()){
                    glPushMatrix();
                    {
                        glTranslatef(x * Game.SQUARESIZE + Game.game.getShiftX(),y * Game.SQUARESIZE + Game.game.getShiftY(),0);
                        float modf = getVisiblesqrs()[Game.game.getChampion().getTeam()][x][y] ? 1 : 10;
                        getSquares()[x][y].render(1 / modf,1 / modf,1 / modf);
                    }
                    glPopMatrix();
                }
            }
        }
    }
    public Square getsquare(int x,int y)
    {
        if(x > MAPSIZE - 1 || x < 0 || y > MAPSIZE - 1 || y < 0) 
        {
            return null;
        }
        return getSquares()[x][y];
    }
    
    private void initmap(String mapname,int sx,int sy)
    {
        try {
            squares = new Square[MAPSIZE][MAPSIZE];
            
            try (BufferedReader r = new BufferedReader(new FileReader("res/maps/" + mapname + ".txt"))) 
            {
                String spawn1[] = r.readLine().split(",");
                String spawn2[] = r.readLine().split(",");
                spawnx1 = Integer.parseInt(spawn1[0]);
                spawny1 = Integer.parseInt(spawn1[1]);
                spawnx2 = Integer.parseInt(spawn2[0]);
                spawny2 = Integer.parseInt(spawn2[1]);
                for(int y = 0; y < sy; y++) { 
                    for(int x = 0; x < sx; x++) {
                        char tempchar = (char)r.read();
                        squares[x][y] = Square.values()[Integer.parseInt("" + tempchar)];
                    }
                    r.readLine();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the squares
     */
    public Square[][] getSquares() {
        return squares;
    }

    /**
     * @return the spawnx1
     */
    public float getSpawnx1() {
        return spawnx1;
    }

    /**
     * @return the spawny1
     */
    public float getSpawny1() {
        return spawny1;
    }

    /**
     * @return the spawnx2
     */
    public float getSpawnx2() {
        return spawnx2;
    }

    /**
     * @return the spawny2
     */
    public float getSpawny2() {
        return spawny2;
    }

    public boolean[][][] getVisiblesqrs() {
        return visiblesqrs;
    }

    public void setVisiblesqrs(boolean[][][] visiblesqrs) {
        this.visiblesqrs = visiblesqrs;
    }
}
