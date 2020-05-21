import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpClient {
    

    final static Integer PORT = 80;
    private static final String OUTPUT_FILE = "C:\\Users\\victo\\Desktop\\imagini";
    public static void sendGetRequest(String url) throws Exception {

        String result = null;
        String imgUrl = null;
        List<String> urls = new ArrayList<>();

        Map<String, String> responseMap = new HashMap<>();
        URL urlObject = new URL(url);
        Socket socket = new Socket(InetAddress.getByName(urlObject.getHost()), PORT);
        System.out.println("Connected");

        //get all page content
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println("GET /" + urlObject.getFile() + " HTTP/1.0");
        printWriter.println("Host: " + urlObject.getHost());
        printWriter.println("");
        printWriter.flush();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        boolean headerDone = false;
        System.out.println("Fetching response. Please wait...");
        while ((line = bufferedReader.readLine()) != null) {
            response.append(line + "\n");
            if (line.isEmpty() && !headerDone) {
                responseMap.put("header", response.toString());
                headerDone = true;
                response = new StringBuilder();
            }
        }
        bufferedReader.close();
        printWriter.close();
        socket.close();
        System.out.println("Done!\n");

        responseMap.put("content", response.toString());

        //extract only the image urls
        try {
            for (Map.Entry<String, String> entry : responseMap.entrySet()) {
                String k = entry.getKey();
                String v = entry.getValue();
                System.out.println("Key: " + k + ", Value: " + v);
                result = entry.getValue();
                System.out.println(result);
            }
            ArrayList<String> subStrings = new ArrayList<String>();
            for(int c = 0; c < result.length(); c++) {
                if(result.charAt(c) == '"') {
                    c++;
                    String newString = "";
                    for(;c < result.length() && result.charAt(c) != '"'; c++) {
                        newString += result.charAt(c);
                    }
                    subStrings.add(newString);
                }
            }
            System.out.println(subStrings);

            for(String string : subStrings) {
                if (string.contains("jpg") && !string.contains("background") && !string.contains("youtube")) {
                    int index = string.indexOf("src=\"");
                    String substr = string.substring(index + 9);
                    int endIndex = substr.indexOf(".jpg");
                    imgUrl = substr.substring(0, endIndex);
                    imgUrl = imgUrl + ".jpg";
                    urls.add(imgUrl);
                }
            }
            System.out.println("all urls ================  " + urls.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //download images
        Runnable runnable = () -> {
            for(String string: urls) {
                URL url1 = null;
                try {
                    url1 = new URL(url + "images/" + string);
                    System.out.println(url1.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                InputStream inputStream = null;
                try {
                    inputStream = url1.openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                File filename = new File(OUTPUT_FILE + "/" + string);
                if (!filename.exists()) {
                    try {
                        filename.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                OutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(filename);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                byte[] buffer = new byte[2048];

                int length = 0;

                while (true) {
                    try {
                        if ((length = inputStream.read(buffer)) == -1) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    responseMap.put("Buffer Read of length: ", String.valueOf(length));
                    try {
                        outputStream.write(buffer, 0, length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();



        socket.close();
        System.out.println("Done!!!");
    }
}