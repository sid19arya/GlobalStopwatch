package src;

public class ActiveWindow {
    // Load the native library
    static {
        System.loadLibrary("ActiveWindow");
    }

    // Declare the native method
    public native String getActiveWindowTitle();

    public static void main(String[] args) throws InterruptedException {
        ActiveWindow aw = new ActiveWindow();

        while (true) {
            String title = aw.getActiveWindowTitle();
            System.out.println("Active Window: " + title);
            Thread.sleep(1000);
        }
    }
}