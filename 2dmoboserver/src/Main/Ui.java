
package Main;

import java.util.ArrayList;

public class Ui 
{
    private ArrayList<UIobject> uiobs;
    
    public Ui()
    {
        uiobs = new ArrayList<>();
    }
    
    public void update()
    {
        for(UIobject u : uiobs)
        {
            u.update();
        }
        Input.checkbuts0(uiobs);
        Input.checkbuts1(uiobs);
    }
    public void render()
    {
        for(UIobject uo : uiobs)
        {
            uo.render();
        }
    }
    
    public void addobject(UIobject uo)
    {
        uiobs.add(uo);
    }
}
