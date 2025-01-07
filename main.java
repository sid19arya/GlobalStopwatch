import src.App;

import java.sql.SQLOutput;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class main {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Executor");
            executor.shutdown();
        }));

        App app = new App(executor);

    }
}
