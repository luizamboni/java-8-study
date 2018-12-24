import java.lang.reflect.InvocationHandler;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Proxy;
import java.lang.reflect.Method;
import java.lang.CharSequence;

class DinamicProxy implements InvocationHandler {


  private final Map<String, Method> methods = new HashMap<>();
 
  private Object target;


  public DinamicProxy (Object target){
    this.target = target;
    for(Method method: target.getClass().getDeclaredMethods()) {
      this.methods.put(method.getName(), method);
    }
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object result = methods.get(method.getName()).invoke(target, args);
    System.out.println("the original value is: " + result);
    return result;
  }
}


class ProxyExample {
  public static void main(String[] args) {

    CharSequence csProxyInstance = (CharSequence) Proxy.newProxyInstance(
        DinamicProxy.class.getClassLoader(), 
        new Class[] { CharSequence.class }, 
        new DinamicProxy("Hello World")
    );
  
    System.out.println(csProxyInstance.length());

  }
}