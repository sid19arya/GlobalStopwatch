package src;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class WatchManager {
    Hashtable<Integer, Watch> watches = new Hashtable<Integer, Watch>();
    String ActiveWatch;

    Integer activeWatchId;

    public WatchManager() {
        activeWatchId = -1;
        watches.put(activeWatchId, new Watch("Limbo"));
        watches.get(activeWatchId).Start();
    }

    public void Start(Integer watchId, String title) {

        watches.get(activeWatchId).Stop();
        activeWatchId = watchId;

        if (watches.containsKey(watchId)) {
//            System.out.println("Watch " + watchId + " started up");
            watches.get(activeWatchId).Start();
        }
        else{
//            System.out.println("Watch " + watchId + " created");
           watches.put(activeWatchId, new Watch(title));
           watches.get(activeWatchId).Start();
        }
    }

    public void WatchReport(){
        Iterator<Map.Entry<Integer, Watch>> iterator = this.watches.entrySet().iterator();
        System.out.println("PID  | Title       | Time");
        System.out.println("----------------------------");

        while (iterator.hasNext()) {
            Map.Entry<Integer, Watch> entry = iterator.next();
            Integer pid = entry.getKey();
            Watch watch = entry.getValue();

            System.out.printf("%-4d | %-10s | %s%n", pid, watch.title, watch.formattedTime());
        }
    }





}

