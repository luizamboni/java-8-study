import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wsse:Security")
public class Sec {

    @XmlElement(name = "UsernameToken")
    private UserNameToken usernameToken;

    public void setUserNameToken(UserNameToken val) {
        this.usernameToken = val;
    }

    public String getUserNameToken() {
       return this.usernameToken;
    }


}