
package Main;

public class Menu implements State
{
    private Ui ui;
    
    public Menu()
    {
        ui = new Ui();
        inituiobs();
    }
    
    @Override
    public void render()
    {
        ui.render();
    }
    @Override
    public void update()
    {
        ui.update();
    }
    
    private void inituiobs()
    {
        
    }
}
