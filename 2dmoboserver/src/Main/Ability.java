
package Main;

import java.io.Serializable;

public abstract class Ability implements Serializable
{
    private String desc;
    private boolean passive;
    private Delay cd;
    
    public Ability(String desc,boolean passive,long cooldown)
    {
        this.desc = desc;
        this.passive = passive;
        this.cd = new Delay(cooldown);
    }
    
    public abstract boolean Activitate();

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @return the passive
     */
    public boolean isPassive() {
        return passive;
    }

    /**
     * @return the cd
     */
    public Delay getCd() {
        return cd;
    }
}
