
package Main;

import Graphics.Sprite;

public enum Square 
{
    grass(new Sprite(Game.SQUARESIZE,Game.SQUARESIZE,"grass"),Consts.WALKABLE,0f,1f,0f),
    stone(new Sprite(Game.SQUARESIZE,Game.SQUARESIZE,"stone"),Consts.NONFLYABLE,0.7f,0.7f,0.7f),
    road(new Sprite(Game.SQUARESIZE,Game.SQUARESIZE,"road"),Consts.WALKABLE,0.7f,0.3f,0.3f);
    private int type;
    private Sprite spr;
    private float r,g,b;
    
    public boolean getTransparent(){
        return type != Consts.NONFLYABLE;
    }
    
    private Square(Sprite spr,int type,float r,float g,float b)
    {
        this.spr = spr;
        this.type = type;
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public void render(float r,float g,float b)
    {
        spr.render(r,g,b);
    }
    public int gettype()
    {
        return type;
    }
    /**
     * @return the r
     */
    public float getR() {
        return r;
    }
    /**
     * @return the g
     */
    public float getG() {
        return g;
    }
    /**
     * @return the b
     */
    public float getB() {
        return b;
    }
}
