package Main;

public class Util 
{
    public static boolean los(Gameobject go1, Gameobject go2)
    {
        return los(go1.getx(),go1.gety(),go2.getx(),go2.gety());
    }
    public static boolean los(Gameobject go1, float x,float y)
    {
        return los(go1.getx(),go1.gety(),x,y);
    }
    public static boolean los(float x1,float y1,float x2,float y2)
    {
        //check that start point is closer to 0,y than end point
        int startx = (int)((x1 < x2 ? x1 : x2) / Game.SQUARESIZE),starty = (int)((x1 < x2 ? y1 : y2) / Game.SQUARESIZE),endx = (int)((x1 > x2 ? x1 : x2) / Game.SQUARESIZE),endy = (int)((x1 > x2 ? y1 : y2) / Game.SQUARESIZE);
        
        float dx = (int)((endx-startx)),dy = (int)((endy - starty));
        if((int)((x2 - x1)/ Game.SQUARESIZE) == 0 && (int)((y2 - y1) / Game.SQUARESIZE) == 0) return true;
        
        int x,y;
        if(dx == 0){
            for(int i = 0; Math.abs(i) < Math.abs(dy);i += Math.abs(dy) / dy){
                x = startx;
                y = starty + i;
                Square sqr = Game.game.getLevel().getMap().getsquare(x, y);
                if(sqr != null) if(!sqr.getTransparent()) return false;
            }
            
        }else if(dy == 0){
            for(int i = 0; Math.abs(i) < Math.abs(dx);i += Math.abs(dx) / dx){
                x = startx + i;
                y = starty;
                Square sqr = Game.game.getLevel().getMap().getsquare(x, y);
                if(sqr != null) if(!sqr.getTransparent()) return false;
            }
        }else{
            float k = dy / dx;
            if(Math.abs(k) < 1)
            {
                for(float i = 0; i < dx; i += Math.abs(k))
                {
                    x = startx + (int) i;
                    y = starty + (int)(i * k);
                    Square sqr = Game.game.getLevel().getMap().getsquare(x, y);
                    if(sqr != null) if(!sqr.getTransparent()) return false;
                }
            }else{
                for(float i = 0; Math.abs(i) < Math.abs(dy); i +=  1 / k)
                {
                    x = startx + (int)(i / k);
                    y = starty + (int)(i);
                    Square sqr = Game.game.getLevel().getMap().getsquare(x, y);
                    if(sqr != null) if(!sqr.getTransparent()) return false;
                }
            }
        }
        return true;
    }
    public static float dist(float x1, float y1, float x2, float y2)
    {
        double x = x2 - x1;
        double y = y2 - y1;
        
        return(float)(Math.sqrt((x * x) + (y * y)));
        
    }
}
