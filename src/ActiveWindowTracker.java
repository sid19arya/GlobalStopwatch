package src;

public class ActiveWindowTracker {
    static {
        System.loadLibrary("ActiveWindowTracker"); // Load DLL
    }

    // Start the native hook
    private Thread hookThread;

    // Native method declarations
    private native void nativeStartHook();
    private native void nativeStopHook();

    public void startHook() {
        if (hookThread != null && hookThread.isAlive()) {
            System.out.println("Hook is already running!");
            return;
        }

        hookThread = new Thread(() -> {
            System.out.println("Starting window hook...");
            nativeStartHook(); // This blocks until stopped
        });

        hookThread.setDaemon(true); // Ensures JVM can exit
        hookThread.start();

        // Register shutdown hook to automatically stop the hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("JVM shutting down, stopping hook...");
            nativeStopHook();
        }));
    }

    // Callback function that C will call
    public void onWindowChange(int processId, String title) {
        System.out.println("Active window changed:");
        System.out.println("PID: " + processId + ", Title: " + title);
    }

    public static void main(String[] args) {
        ActiveWindowTracker tracker = new ActiveWindowTracker();

        // Start hook in background
        tracker.startHook();

        // Simulate the main application running
        try {
            Thread.sleep(20000); // Run for 20 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread exiting...");

    }
}
