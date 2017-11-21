package MapsAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fox on 21/11/17.
 */
public class Maps {

    private static String key =  "AIzaSyAQOmyCzcNMFx8h5c3bGifWfXMEW4oqyTo";

    private static void saveCoordinates() throws MalformedURLException {
//        https://developers.google.com/maps/documentation/geocoding/intro#GeocodingRequests
        try {
            String address = "Goiânia&types=neighborhood";
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + key);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try {
                File file = new File(System.getProperty("java.io.tmpdir") + File.separator + "teste.json");
                InputStream in = connection.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                }
                in.close();
                fos.flush();
                fos.close();
                System.out.print("Arquivo Salvo");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MalformedURLException {
        saveCoordinates();
    }


}
