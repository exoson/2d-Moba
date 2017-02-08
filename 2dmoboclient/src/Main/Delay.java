
package Main;

import java.io.Serializable;

public class Delay implements Serializable
{
    private long length;
    private boolean started;
    private long endtime;
    
    public Delay(long length)
    {
        this.length = length;
    }
    public void setLength(long length)
    {
        this.length = length;
    }
    public long timeleft()
    {
        if(over())
            return 0;
        if(active())
            return endtime - Time.gettime();
        else return 0;
    }
    
    public boolean over()
    {
        if(!started) return false;
        
        return Time.gettime() >= endtime;
    }
    public void start()
    {
        started = true;
        endtime = length * 1000000 + Time.gettime();
    }
    public void terminate()
    {
        started = false;
    }
    public boolean active()
    {
        return started;
    }
    public void end()
    {
        started = true;
        endtime = 0;
    }
}
