
package Main;

import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;

public class Minimap extends UIobject
{
    private Map map;
    public Minimap(float x,float y,float sx,float sy,boolean displayed,Map map,String bg)
    {
        this.map = map;
        init(x,y,sx,sy,MINMAP,displayed,bg);
    }
    @Override
    public void render()
    {
        if(isDisplayed())
        {
            ArrayList<Gameobject> gos = Game.game.getobjects();
            glPushMatrix();
            {
                boolean[][] vis = map.getVisiblesqrs()[Game.game.getChampion().getTeam()];
                glTranslatef(x,y,0);    
                spr.render();
                glBegin(GL_QUADS);
                {
                    Square[][] sqrs = map.getSquares();
                    float modf;
                    for(int i = 0;i < sqrs.length;i++)
                    {
                        for(int j = 0;j < sqrs[i].length;j++)
                        {
                            modf = vis[i][j] ? 1 : 10;
                            glColor3f(sqrs[i][j].getR() / modf,sqrs[i][j].getG() / modf,sqrs[i][j].getB() / modf);
                            glVertex2f(i * 2,j * 2);
                            glVertex2f(i * 2 + 2,j * 2);
                            glVertex2f(i * 2 + 2,j * 2 + 2);
                            glVertex2f(i * 2,j * 2 + 2);
                        }
                    }
                }
                for(Gameobject go : gos)
                {
                    if(!vis[(int)(go.getx() / Game.SQUARESIZE)][(int)(go.gety() / Game.SQUARESIZE)]) continue;
                    if(go.getid() == Gameobject.CHAMPID)
                    {
                        glColor3f(1,0,0);
                        glVertex2f(go.getx() * 2 / Game.SQUARESIZE -3,go.gety() / Game.SQUARESIZE * 2 -3);
                        glVertex2f(go.getx() * 2 / Game.SQUARESIZE +3,go.gety() / Game.SQUARESIZE * 2 -3);
                        glVertex2f(go.getx() * 2 / Game.SQUARESIZE +3,go.gety() / Game.SQUARESIZE * 2 +3);
                        glVertex2f(go.getx() * 2 / Game.SQUARESIZE -3,go.gety() / Game.SQUARESIZE * 2 +3);
                    }
                    if(go.getid() == Gameobject.MINIONID)
                    {
                        
                        glColor3f(1,1,0);
                        glVertex2f(go.getx() * 2 / Game.SQUARESIZE -2,go.gety() * 2 / Game.SQUARESIZE -2);
                        glVertex2f(go.getx() * 2 / Game.SQUARESIZE +2,go.gety() * 2 / Game.SQUARESIZE -2);
                        glVertex2f(go.getx() * 2 / Game.SQUARESIZE +2,go.gety() * 2 / Game.SQUARESIZE +2);
                        glVertex2f(go.getx() * 2 / Game.SQUARESIZE -2,go.gety() * 2 / Game.SQUARESIZE +2);
                    }
                }
                glEnd();
            }
            glPopMatrix();
        }
    }
}
