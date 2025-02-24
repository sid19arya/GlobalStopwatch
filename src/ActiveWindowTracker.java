package src;

public class ActiveWindowTracker {
    static {
        System.loadLibrary("ActiveWindowTracker"); // Load DLL
    }

    // Start the native hook
    public native void startHook();

    // Callback function that C will call
    public void onWindowChange(int processId, String title) {
        System.out.println("Active window changed:");
        System.out.println("PID: " + processId + ", Title: " + title);
    }

    public static void main(String[] args) {
        ActiveWindowTracker tracker = new ActiveWindowTracker();
        tracker.startHook(); // Start tracking
    }
}
