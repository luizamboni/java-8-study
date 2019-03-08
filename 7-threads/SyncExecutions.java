import static java.lang.System.out;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;
import java.util.stream.*;

public class SyncExecutions { 

  public static int NUMBER_OR_EXEC = 10000;
  public static int syncCount = 0;
  public static int count = 0;
  
  public static void main(String[] args) {

    System.out.println("\n# not syncronized executions");
    notSyncedExecutions();

    System.out.println("\n# syncronized executions");

    synchronizedExecutions();
  }


  public static void increment() {
    count = count + 1;
  }

  private static synchronized void incrementSync() {
    syncCount = syncCount + 1;
  }

  public static void synchronizedExecutions() {

    syncCount = 0;

    ExecutorService executor = Executors.newFixedThreadPool(2);

    IntStream.range(0, NUMBER_OR_EXEC)
        .forEach(i -> 
          executor.submit(SyncExecutions::incrementSync)
        );


    System.out.println("count is: " + count); 
    sleep(1);

    stop(executor);
  }

  public static void notSyncedExecutions() {

    count = 0;

    ExecutorService executor = Executors.newFixedThreadPool(2);

    IntStream.range(0, NUMBER_OR_EXEC)
        .forEach(i -> 
          executor.submit(SyncExecutions::increment)
        );


    System.out.println("count is: " + count); 
    sleep(1);
    stop(executor);
  }


  public static void stop(ExecutorService executor) {
      try {
          executor.shutdown();
          executor.awaitTermination(15, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
          System.err.println("termination interrupted");
      } finally {
          if (!executor.isTerminated()) {
              System.err.println("killing non-finished tasks");
          }
          executor.shutdownNow();
      }
  }

  public static void sleep(int seconds) {
    try {
        TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
        throw new IllegalStateException(e);
    }
  }
}