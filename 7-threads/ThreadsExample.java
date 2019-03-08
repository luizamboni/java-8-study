import static java.lang.System.out;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

public class ThreadsExample {


    public static void main(String[] args) {

			out.println("##	callable example ----------------------------------");
			basicThreadExampleWithCallable();

			out.println("\n##	runnable example ----------------------------------");
			basicThreadExampleWithRunnable();


			out.println("\n##	stealing example ----------------------------------");
			try {
				stealingExample();

			} catch (Exception e) {
			}

			out.println("\n##	Scheduled Executors example ----------------------------------");
			try {
				scheduledExecutorsExample();
			} catch (Exception e) {
				out.println(e.getMessage());
			}
		}


		public static void scheduledExecutorsExample() throws InterruptedException {
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			AtomicInteger executionCount = new AtomicInteger(0);

			Runnable task = () -> {
				try {
					out.println(
						"thread: " + Thread.currentThread().getName()
					);

					out.println(
						new Date().toString()
					);

					executionCount.incrementAndGet();

				} catch (Exception e) {
					/**
					 * Only to not need worwaround erros
					 */
					out.println(e.getMessage());
				}
			};
			
			// executor.schedule(task, 5,  TimeUnit.SECONDS);
			executor.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);

			while(executionCount.get() < 5) {
				out.println(executionCount.get());
				Thread.sleep(2000);
			}

			executor.shutdown();
		}


		public static void stealingExample() throws InterruptedException {
			ExecutorService executor = Executors.newWorkStealingPool();


			Callable<String> task = () -> {
				try {
					out.println(
						"thread: " + Thread.currentThread().getName()
					);

					return "returned -> " + Thread.currentThread().getName();

				} catch (Exception e) {
					/**
					 * Only to not need worwaround erros
					 */
					return "ERRORRRR";
				}
			};
			
			List<Callable<String>> callables = Arrays.asList(
				task, task, task 
			);

			executor.invokeAll(callables)
					.stream()
					.map(future -> {
							try {
									return future.get();
							} catch (Exception e) {
									throw new IllegalStateException(e);
							}
					})
					.forEach(System.out::println);
		}


		public static void basicThreadExampleWithRunnable() {

			/**
			 * Runnable not return anything , instead,
			 * one external scope variable need to be created
			 * in this case.
			 * 
			 * But, the ideal is use Callable Interface else Runnable
			 */

			AtomicInteger taskResult = new AtomicInteger();

			Runnable task = () -> {
					String threadName = Thread.currentThread().getName();
					System.out.println("Hello " + threadName);
					taskResult.set(123);
			};

			/**
			 * exanoke of run task in main thread(sync)
			 */
			task.run();
			
			Thread thread = new Thread(task);
			thread.start();
			
			System.out.println("Done! " + taskResult);

		}

		public static void scheduledExecutors() {

		}


    public static void basicThreadExampleWithCallable() {

			/**
			 * ExecutorService is a class of
			 * ThreadPool implementation
			 * 
			 * in this example we use a single Thread executor
			 */
			ExecutorService executor = Executors.newSingleThreadScheduledExecutor();

			Callable<Integer> task = () -> {
					try {
						out.println(
							"thread: " + Thread.currentThread().getName()
						);
						return 123;

					} catch (Exception e) {
							throw new IllegalStateException("task interrupted", e);
					}
			};

			/**
			 * example of running Callable in main thread
			 */
			try {
				Integer syncResult = task.call();
				out.println("syncResult: " +  syncResult);

			} catch (Exception e) {}


			try {

				Future<Integer> future = executor.submit(task);
				Integer result = null;
				while(true) {

					Boolean isDone = future.isDone();
					out.println("future done? " +isDone);

					Thread.sleep(500);

					if(isDone) {
						break;
					}

				}
				/**
				 * future.get() bloking the caller thread
				 * becouse this. Here is called only
				 * when future is done
				 */
				result = future.get();
				out.println("result: " +  result);


			} catch (Exception ex) {}

			/**
			 * need kills a thread pool
			 * or application will not exit
			 */
			executor.shutdown();
    }
}