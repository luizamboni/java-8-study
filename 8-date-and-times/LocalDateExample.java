import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class LocalDateExample {

  public static void main(final String[] args) throws Exception {
    GregorianCalendar gcal = new GregorianCalendar();
    XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);

    System.out.println(xgcal);
  }

}