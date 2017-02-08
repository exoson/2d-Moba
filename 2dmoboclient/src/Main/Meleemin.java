
package Main;

import Graphics.Animation;
import Graphics.Frame;
import Graphics.Sprite;
import java.util.ArrayList;

public class Meleemin extends Minion
{
    public Meleemin(float x,float y,int team)
    {
        Animation[] anims = new Animation[animamt];
        
        ArrayList<Frame> afkframes = new ArrayList<>();
        afkframes.add(new Frame(new Sprite(Game.SQUARESIZE / 2,Game.SQUARESIZE / 2,"playerafk0"),100));
        anims[AFKANIM] = new Animation(afkframes);
        anims[DEATHANIM] = new Animation(afkframes);
        init(x,y,Game.SQUARESIZE / 2,Game.SQUARESIZE / 2,MINIONID,team,"Meleeminion",anims,100,10,10,10,3,0,Game.SQUARESIZE / 2 + 10,1000);
    }
}
