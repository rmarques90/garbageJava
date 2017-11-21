package MapsAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fox on 21/11/17.
 */
public class Maps {

    private static String keyGeo =  "AIzaSyAQOmyCzcNMFx8h5c3bGifWfXMEW4oqyTo";
    private static String keyPlace =  "AIzaSyDOg-dJ-iwOf1jDyiwsR4TFor0bD0BYLoU";

    private static void getGeocoding() throws MalformedURLException {
//        https://developers.google.com/maps/documentation/geocoding/intro#GeocodingRequests
        try {
            String address = "Goiânia";
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + keyGeo);
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

    private static void getNearbySearch() {
//        https://developers.google.com/places/web-service/intro
        String params = "";
        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + params + "&key=" + keyPlace);
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MalformedURLException {
        getNearbySearch();
        //        https://www.quora.com/How-do-you-get-boundaries-of-neighborhoods-via-the-Google-Maps-api


    }


}
