package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static Utils.Constants.*;

/**
 * Created by fox on 26/09/17.
 */
public class Routes {
    private double x;
    private double y;
    private double garbage;
    private int collectPoint;
    private String separator;

    public Routes(double posX, double posY, double garbageLoad, int point) {
        x = posX;
        y = posY;
        garbage = garbageLoad;
        collectPoint = point;
    }

    public Routes(String separator) {
        this.separator = separator;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setGarbage(double garbage) {
        this.garbage = garbage;
    }

    public void setCollectPoint(int collectPoint) {
        this.collectPoint = collectPoint;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public double getX () {
        return x;
    }

    public double getY () {
        return y;
    }

    public double getGarbage () {
        return garbage;
    }

    public int getCollectPoint() {
        return collectPoint;
    }

    public static void initialize_collect_points(Boolean csvFile) {
        if (csvFile) {
            String line;

            BufferedReader stream;
            String[] splitted;

            try {
                stream = new BufferedReader(new FileReader(routesFilePath));
                while ((stream.readLine()) != null) {
                    pointsNumber++;
                }
                stream = new BufferedReader(new FileReader(routesFilePath));
                nodes = new ArrayList<Routes>();
                while ((line = stream.readLine()) != null) {
                    splitted = line.split(";");
                    nodes.add(new Routes(
                            Double.parseDouble(splitted[0]),
                            Double.parseDouble(splitted[1]),
                            Double.parseDouble(splitted[2]),
                            Integer.parseInt(splitted[3])));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            pointsNumber = nodes.size();
        } else {
            Random random = new Random();
            nodes = new ArrayList<Routes>();;
            for (int i = 0; i < 10; i++){
                nodes.add(new Routes(i*random.nextInt(30), i*random.nextInt(30), i*random.nextInt(10), i));
            }
        }
        for (int i =0; i <= trucks; i++) {
            nodes.add(new Routes("t"));
            nodes.add(new Routes(0,0,0,0));
        }
        pointsNumber = nodes.size() - trucks;
    }
}
