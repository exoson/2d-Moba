
package Main;

import Graphics.Animation;
import static Main.Gameobject.AFKANIM;
import static Main.Gameobject.DEATHANIM;
import static Main.Consts.*;
import java.util.ArrayList;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

public class Statobject extends Gameobject
{
    
    public static final float SRANGE = 400;
    public static final float DAMPING = 0.7f;
    
    private ArrayList<Float> slows = new ArrayList<>(),asslows = new ArrayList<>();
    private boolean[] effects;
    private Animation[] projanims;
    private boolean ranged;
    private float basehp,basedmg,armor,magres,speed,hpregen,attrange,hp,projspeed,projsx,projsy;
    private long attspeed;
    protected Statobject target;
    protected Vector2f targetv;
    protected Delay attdel;
    
    public String getStats()
    {
        return "" + getArmor() + "," + getMagres() + "," + getSpeed() + "," + getHp();
    }
    
    public void init(float x, float y, float sx, float sy,int id,int team,String name,Animation[] anims,float basehp,float basedmg
            ,float armor,float magres,float speed,float hpregen,float attrange,long attspeed)
    {
        init(x,y,sx,sy,id,team,name,anims);
        effects = new boolean[EFFECTAMT];
        setTargetv(pos);
        this.setArmor(armor);
        this.setAttrange(attrange);
        this.setAttspeed(attspeed);
        this.setBasedmg(basedmg);
        this.setBasehp(basehp);
        this.setHpregen(hpregen);
        this.setMagres(magres);
        this.setSpeed(speed);
        attdel = new Delay(attspeed);
        attdel.end();
        setHp(basehp);
    }
    public void init(float x, float y, float sx, float sy,int id,int team,String name,Animation[] anims,float basehp,float basedmg
            ,float armor,float magres,float speed,float hpregen,float attrange,long attspeed, boolean ranged)
    {
        setRanged(false);
        init(x,y,sx,sy,id,team,name,anims,basehp,basedmg,armor,magres,speed,hpregen,attrange,attspeed);
        setTargetv(pos);
        this.setArmor(armor);
        this.setAttrange(attrange);
        this.setAttspeed(attspeed);
        this.setBasedmg(basedmg);
        this.setBasehp(basehp);
        this.setHpregen(hpregen);
        this.setMagres(magres);
        this.setSpeed(speed);
        attdel = new Delay(attspeed);
        attdel.end();
        setHp(basehp);
    }
    public void init(float x, float y, float sx, float sy,int id,int team,String name,Animation[] anims,float basehp,float basedmg
            ,float armor,float magres,float speed,float hpregen,float attrange,long attspeed,float projsx,float projsy,float projspeed,Animation[] projanims)
    {
        setRanged(true);
        this.projanims = projanims;
        this.projspeed = projspeed;
        this.projsx = projsx;
        this.projsy = projsy;
        init(x,y,sx,sy,id,team,name,anims,basehp,basedmg,armor,magres,speed,hpregen,attrange,attspeed);
    }
    @Override
    public void render()
    {
        if((getx() * Game.SQUARESIZE + Game.game.getShiftX() < Display.getWidth() && gety() * Game.SQUARESIZE + Game.game.getShiftY() < Display.getHeight())) return;
        if(!Game.game.curlvl.getMap().getVisiblesqrs()[Game.game.getChampion().getTeam()][(int)(getx() / Game.SQUARESIZE)][(int)(gety() / Game.SQUARESIZE)]) return;
        glPushMatrix();
        {
            glTranslatef(pos.getX() + Game.game.getShiftX(),pos.getY() + Game.game.getShiftY(),0);
            if(getCuranim() == DEATHANIM){
                if(anim[getCuranim()].render(1,1,1,getStealthed() ? 0.4f : 1)) {
                    remove();
                }
            }else if(anim[getCuranim()].render(1,1,1,getStealthed() ? 0.4f : 1)) {
                curanim = AFKANIM;
            }
        }
        glPopMatrix();
    }
    @Override
    public void update()
    {
        if(hp <= 0) {
            die();
        }
        if(target != null) {
            if(target.getHp() <= 0){
                target = null;
            }
        }
    }
    protected void attack() 
    {
        if(!ranged){
            target.dmg(getDamage());
            attdel.start();
        }else{
            Game.game.addGameobject(new Projectile(target,getDamage(),projspeed,getx(),gety(),projsx,projsy, getTeam(),projanims));
            attdel.start();
        }
        
    }
    protected boolean chase()
    {
        if(getStunned() || getSnared() || getFeared()) return false;
        Vector2f movev = getTargetv().minus(pos);
        if(movev.length() < movev.normalize().mult(speed * Time.getdelta() * DAMPING).length()) {
            Vector2f temp = pos.add(movev);
            if(temp.getX() < 0 || temp.getX() > Map.MAPSIZE * Game.SQUARESIZE || temp.getY() < 0 || temp.getY() > Map.MAPSIZE * Game.SQUARESIZE) {
                return false;
            }
            pos = temp;
            return true;
        }
        else {
            Vector2f temp = pos.add(movev.normalize().mult(speed * Time.getdelta() * DAMPING));
            if(temp.getX() < 0 || 
                    temp.getX() > Map.MAPSIZE * Game.SQUARESIZE - sx || 
                    temp.getY() < 0 || 
                    temp.getY() > Map.MAPSIZE * Game.SQUARESIZE - sy) {
                return false;
            }
            pos = temp;
            return true;
        }
    }
    public void dmg(float amt)
    {
        setHp(getHp() - amt);
    }
    public void settarget(Statobject s)
    {
        setTarget(s);
    }
    protected void die(){
        curanim = DEATHANIM;
    }
    protected boolean isVisible(Statobject so)
    {
        return Util.los(this, so) && !so.getStealthed();
    }
    /**
     * @return the basehp
     */
    public float getBasehp() {
        return basehp;
    }
    public boolean getStunned()
    {
        return effects[STUN];
    }
    public boolean getSlowed()
    {
        return effects[SLOW];
    }
    public boolean getSilenced()
    {
        return effects[SILENCE];
    }
    public boolean getSnared()
    {
        return effects[SNARE];
    }
    public boolean getFeared()
    {
        return effects[FEAR];
    }
    public boolean getASSlowed()
    {
        return effects[ASSLOW];
    }
    public boolean getBlinded()
    {
        return effects[BLIND];
    }
    public boolean getStealthed()
    {
        return effects[STEALTH];
    }
    protected void stealth()
    {
        effects[STEALTH] = true;
    }
    /**
     * @param basehp the basehp to set
     */
    public void setBasehp(float basehp) {
        this.basehp = basehp;
    }

    /**
     * @return the basedmg
     */
    public float getBasedmg() {
        return basedmg;
    }

    /**
     * @param basedmg the basedmg to set
     */
    public void setBasedmg(float basedmg) {
        this.basedmg = basedmg;
    }

    /**
     * @return the armor
     */
    public float getArmor() {
        return armor;
    }

    /**
     * @param armor the armor to set
     */
    public void setArmor(float armor) {
        this.armor = armor;
    }

    /**
     * @return the magres
     */
    public float getMagres() {
        return magres;
    }

    /**
     * @param magres the magres to set
     */
    public void setMagres(float magres) {
        this.magres = magres;
    }

    /**
     * @return the speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * @return the hpregen
     */
    public float getHpregen() {
        return hpregen;
    }

    /**
     * @param hpregen the hpregen to set
     */
    public void setHpregen(float hpregen) {
        this.hpregen = hpregen;
    }

    /**
     * @return the attrange
     */
    public float getAttrange() {
        return attrange;
    }

    /**
     * @param attrange the attrange to set
     */
    public void setAttrange(float attrange) {
        this.attrange = attrange;
    }

    /**
     * @return the attspeed
     */
    public float getAttspeed() {
        return attspeed;
    }

    /**
     * @param attspeed the attspeed to set
     */
    public void setAttspeed(long attspeed) {
        this.attspeed = attspeed;
    }

    /**
     * @return the target
     */
    public Statobject getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(Statobject target) {
        this.target = target;
    }

    /**
     * @return the targetv
     */
    public Vector2f getTargetv() {
        return targetv;
    }

    /**
     * @param targetv the targetv to set
     */
    public void setTargetv(Vector2f targetv) {
        this.targetv = targetv;
    }

    /**
     * @return the hp
     */
    public float getHp() {
        return hp;
    }

    /**
     * @param hp the hp to set
     */
    public void setHp(float hp) {
        this.hp = hp;
    }

    /**
     * @return the ranged
     */
    public boolean isRanged() {
        return ranged;
    }

    /**
     * @param ranged the ranged to set
     */
    public void setRanged(boolean ranged) {
        this.ranged = ranged;
    }

    private float getDamage() {
        return getBlinded() ? 0 : basedmg;
    }

}
