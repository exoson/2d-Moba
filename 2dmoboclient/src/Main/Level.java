
package Main;

public class Level 
{
    private Map map;
    
    public Level(String mapname)
    {
        map = new Map(mapname);
    }
    
    public void render()
    {
        getMap().render();
    }
    public void update()
    {
        getMap().update();
    }

    /**
     * @return the map
     */
    public Map getMap() {
        return map;
    }
    /**
     * @return the spawnx1
     */
    public float getSpawnx1() {
        return map.getSpawnx1();
    }

    /**
     * @return the spawny1
     */
    public float getSpawny1() {
        return map.getSpawny1();
    }

    /**
     * @return the spawnx2
     */
    public float getSpawnx2() {
        return map.getSpawnx2();
    }

    /**
     * @return the spawny2
     */
    public float getSpawny2() {
        return map.getSpawny2();
    }
}
