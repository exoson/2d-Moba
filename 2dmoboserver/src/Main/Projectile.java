
package Main;

import Graphics.Animation;

public class Projectile extends Gameobject
{
    
    private Statobject target;
    private float dmg,speed;
    public Projectile(Statobject target,float dmg,float speed,float x,float y,float sx,float sy,int team,Animation[] anims)
    {
        this.target = target;
        this.dmg = dmg;
        this.speed = speed;
        init(x,y,sx,sy,PROJECTILEID,team,"ammo",anims);
    }
    
    @Override
    public void update()
    {
        chase();
    }
    private void hit()
    {
        target.dmg(dmg);
        curanim = DEATHANIM;
    }
    private boolean chase()
    {
        Vector2f movev = target.pos.minus(pos);
        if(movev.length() < movev.normalize().mult(speed * Time.getdelta() * Statobject.DAMPING).length()) {
            Vector2f temp = pos.add(movev);
            if(temp.getX() < 0 || temp.getX() > Map.MAPSIZE * Game.SQUARESIZE || temp.getY() < 0 || temp.getY() > Map.MAPSIZE * Game.SQUARESIZE) {
                return false;
            }
            pos = temp;
            hit();
            return true;
        }
        else {
            Vector2f temp = pos.add(movev.normalize().mult(speed * Time.getdelta() * Statobject.DAMPING));
            if(temp.getX() < 0 || temp.getX() > Map.MAPSIZE * Game.SQUARESIZE - sx || temp.getY() < 0 || temp.getY() > Map.MAPSIZE * Game.SQUARESIZE - sy) {
                return false;
            }
            pos = temp;
            return true;
        }
    }
}
