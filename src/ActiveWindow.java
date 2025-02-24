package src;

public class ActiveWindow {
    static {
        System.loadLibrary("ActiveWindowDLL"); // Load the compiled DLL
    }


    // Declare native method
    public native String getActiveWindowTitle();

    public static void main(String[] args) {
        ActiveWindow aw = new ActiveWindow();
        while (true) {
            String title = aw.getActiveWindowTitle();
            System.out.println("Active Window: " + (title.isEmpty() ? "[No Active Window]" : title));
            try {
                Thread.sleep(5000); // Wait 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}