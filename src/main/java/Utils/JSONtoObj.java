package Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by guest-nungba on 05/12/17.,
 *
 * Classe para conversão do JSON para Java Object
 */
public class JSONtoObj {
    public static ListJSON[] addressList() {
        File file = new File("ends.json");
        ObjectMapper obj = new ObjectMapper();
        ListJSON[] ends = new ListJSON[0];
        try {
            ends = obj.readValue(file, ListJSON[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ends;
    }
}
