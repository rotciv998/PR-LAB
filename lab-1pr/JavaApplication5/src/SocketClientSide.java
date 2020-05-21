import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SocketClientSide {

    static String GET_URL = "http://unite.md/";
    public static void main(String[] args) {
        try {
            Map<String, String> responseMap;
            HttpClient.sendGetRequest(GET_URL);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}