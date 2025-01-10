package src;


public class Watch {
    int millis;
    int seconds;
    int minutes;
    int hours;

    long curr_time;

    long elapsedTime;

    boolean active = false;


    public void Start(){
        curr_time = System.currentTimeMillis();
        active = true;
    }

    public long Stop(){
        elapsedTime += System.currentTimeMillis() - curr_time;
        active = false;
        return elapsedTime;
    }

    public void Reset(){
        elapsedTime = 0;
    }



    public String formattedTime(){
        long t;
        if (active) {
            t = elapsedTime + System.currentTimeMillis() - curr_time;
        } else {
            t = elapsedTime;
        }

        long hours = (t / (1000 * 60 * 60)) % 100; // At most 2 digits
        long minutes = (t / (1000 * 60)) % 60; // At most 2 digits
        long seconds = (t / 1000) % 60; // At most 2 digits
        long millis = (t % 1000) / 10;

        return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, millis);
    }
}
