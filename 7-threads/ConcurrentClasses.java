import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentClasses {
  public static void main(String[] args) {
    ConcurrentHashMap<String, String> index = new ConcurrentHashMap<>();

    index.put("a", "1");
    index.put("b", "1");
    index.put("c", "3");
    index.put("d", "4");
    index.put("e", "1");
    index.put("f", "3");
    index.put("g", "10");

    String founded = index.search(2, (key, value) -> {

      System.out.println(Thread.currentThread().getName());

      if ("10".equals(value)) {
        return key;
      } else {
        return null;
      }
    });

    System.out.println(founded);

  }
}