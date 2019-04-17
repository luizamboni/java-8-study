package example.reactor.withnetty.benchmark;


public class WebClients {

    static String uri = "http://api.github.com/users/luizamboni/repos";

    static Integer volume = 10;

    public static void main(String[] args) {


        System.out.println("sync single thread ---------------------------");
        new  Syncronous(uri, volume).run();

        System.out.println("with thread in thread executor ---------------");
        new ThreadConcurrency(uri, volume).run();


        System.out.println("with reactor ---------------------------------");
        new ReactorConcurrency(uri, volume).run();

        while(true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
