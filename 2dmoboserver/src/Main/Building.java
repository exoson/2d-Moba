
package Main;

import Graphics.Animation;

public class Building extends Statobject
{
    public void init(float x,float y,float sx,float sy,int team,String name,Animation[] anims,float basehp,float basedmg
            ,float armor,float magres,float attrange,long attspeed)
    {
        init(x,y,sx,sy,BUILDINGID,team,name,anims,basehp,basedmg,armor,magres,0,0,attrange,attspeed);
    }
    public void init(float x,float y,float sx,float sy,int team,String name,Animation[] anims,float basehp,float basedmg
            ,float armor,float magres,float attrange,long attspeed,float projsx,float projsy,float projspeed,Animation[] projanims)
    {
        init(x,y,sx,sy,BUILDINGID,team,name,anims,basehp,basedmg,armor,magres,0,0,attrange,attspeed,projsx,projsy,projspeed,projanims);
    }
}
