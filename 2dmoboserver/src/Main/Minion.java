
package Main;

import java.util.ArrayList;

public class Minion extends Statobject
{
    @Override
    public void update()
    {
        super.update();
        if(target == null) {
            look();
            targetv.setX(getx());
            targetv.setY(gety());
        }
        if(target != null){
            if(Util.dist(getx(), gety(), target.getx(), target.gety()) < getAttrange()) {
                if(attdel.over()) {
                    attack();
                }
            }else {
                chase();
            }
            targetv.setX(target.getx());
            targetv.setY(target.gety());
            if(Util.dist(getx(), gety(), target.getx(), target.gety()) > SRANGE || !isVisible(target)){
                target = null;
                targetv.setX(getx());
                targetv.setY(gety());
            }
        }
        
    }
    private void look()
    {
        ArrayList<Gameobject> ob = Game.sphereCollide(getx(), gety(), SRANGE);
        for(Gameobject o : ob){
            if(o.getid() == CHAMPID && isVisible((Statobject)o)) {
                target = (Statobject) o;
                break;
            }
        }
    }
}
