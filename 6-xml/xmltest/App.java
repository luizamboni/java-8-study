
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;


public class App {


  public static void main(String[] args) {

    try {
      SecExtended xml = new SecExtended();
      UserNameToken auth = new UserNameToken();

      auth.setUsername("luiz");
      auth.setPassword("123456");
      xml.setExtra("EXTRA VALUE");


      xml.setUserNameToken(auth);

      JAXBContext SecExtendedctx = JAXBContext.newInstance(SecExtended.class);
      final Marshaller marshaller = SecExtendedctx.createMarshaller();

      marshaller.marshal(xml, System.out);


      System.out.println("-------------------------------------");
      JAXBContext Sectx = JAXBContext.newInstance(Sec.class);
      final Marshaller marshaller2 = Sectx.createMarshaller();

      marshaller2.marshal(xml, System.out);

    } catch (Exception e) {

      System.out.println("error: " + e.getMessage());

      System.out.println("error: " + e.getCause());
      System.out.println("error: " + e.getClass() );

      System.out.println("error: " + e.getStackTrace());
    }
  }
}