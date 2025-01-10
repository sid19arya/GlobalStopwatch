package src;

import java.util.Dictionary;
import java.util.Hashtable;

public class WatchManager {
    Dictionary<String, Watch> watches = new Hashtable<String, Watch>();
    String ActiveWatch;

//    This function reacts to a change in foreground
    public void WindowChange(String newWindow){
        Watch oldWatch = watches.get(ActiveWatch);
        if (oldWatch != null){
            oldWatch.Stop();
        }

        if (watches.get(newWindow) == null){
            watches.put(newWindow, new Watch());
        }

        ActiveWatch = newWindow;

        watches.get(ActiveWatch).Start();

    }

}
