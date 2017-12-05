package MapsAPI;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fox on 21/11/17.
 */
public class Maps {

    private static Logger logger = LoggerFactory.getLogger(Maps.class);

    private static String csvFile = "address.csv";
    private static String line = "";
    private static String csvSplitBy = ",";

    private static String keyGeo =  "AIzaSyAQOmyCzcNMFx8h5c3bGifWfXMEW4oqyTo";
    private static String keyPlace =  "AIzaSyDOg-dJ-iwOf1jDyiwsR4TFor0bD0BYLoU";

    private static List<String> getAddress() throws FileNotFoundException {
        logger.info("Iniciando leitura do CSV");
        List<String> address = new ArrayList<String>();
        File file = new File(csvFile);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                while ((line = br.readLine()) != null) {
                    String[] lineAddress = line.split(csvSplitBy);
                    address.add(lineAddress[0]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("Leitura CSV finalizada.");
            return address;
        } else {
            return null;
        }
    }

    private static void getGeocoding() throws MalformedURLException {
//        https://developers.google.com/maps/documentation/geocoding/intro#GeocodingRequests
        try {
            List<String> adds;
            try {
                adds = getAddress();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            ObjectMapper obj = new ObjectMapper();
            obj.configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            int i = 0;
            String lat = "";
            String lng = "";
            String street = "";
            ArrayNode arrayNode = obj.createArrayNode();

            while (i <= adds.size() - 1) {
                logger.info("Capturando coordenada do CEP: {}", adds.get(i));
                URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + adds.get(i) + "&key=" + keyGeo);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                try {
                    InputStream in = connection.getInputStream();
                    JsonNode treeNode = obj.readTree(in);
                    street = treeNode.path("results").get(0).get("formatted_address").toString().replaceAll("\"", "");
                    JsonNode geometryNode = treeNode.path("results").get(0).get("geometry").get("location");
                    lat = geometryNode.get("lat").toString().replaceAll("\"", "");
                    lng = geometryNode.get("lng").toString().replaceAll("\"", "");

                    ObjectNode objectNode = obj.createObjectNode();
                    objectNode.put("index", i);
                    objectNode.put("lat", lat);
                    objectNode.put("lng", lng);
                    objectNode.put("add", street);
                    arrayNode.add(objectNode);

                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
            logger.info("##### Salvando JSON... #####");
            File file = new File(System.getProperty("user.dir") + File.separator + "ends.json");
            ObjectWriter writer = obj.writer(new DefaultPrettyPrinter());
            writer.writeValue(file, arrayNode);
            logger.info("##### JSON salvo com sucesso #####");

        } catch(Exception e){
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
        Stopwatch counter = Stopwatch.createStarted();
        logger.info("##### Iniciando captura de coordenadas... #####");
        getGeocoding();
        logger.info("##### Tempo de execução da captura de coordenadas: {}", counter.stop());
        //        https://www.quora.com/How-do-you-get-boundaries-of-neighborhoods-via-the-Google-Maps-api
//        https://stackoverflow.com/questions/10522393/google-maps-get-polygon-border-of-zones-neighborhood
//        http://wikimapia.org/api/


    }


}
