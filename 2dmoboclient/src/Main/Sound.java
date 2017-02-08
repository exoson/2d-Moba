
package Main;

import java.io.FileInputStream;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Sound 
{
    private AudioStream au;
    
    public Sound(String track)
    {
        au = loadaudio(track);
    }
    public void startmusic()
    {
        AudioPlayer.player.start(au);
    }
    public void stopmusic()
    {
        AudioPlayer.player.stop(au);
    }
    
    public static AudioStream loadaudio(String track)
    {
        try
        {
            InputStream in = new FileInputStream("res/sounds/" + track + ".wav");
            return new AudioStream(in);
        }
        catch(Exception e)
        {
            
        }
        return null;
    }
    
}
