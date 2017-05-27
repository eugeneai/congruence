package newPackage1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Егор on 10.11.2016.
 */
public class Data {
    public static ArrayList<Double> getData(){

        ArrayList<Double> list= new ArrayList<>();
        try{
            File fin = new File("D:/data.txt");
            BufferedReader reader = new BufferedReader(new FileReader(fin));
            String line;
            while ((line = reader.readLine()) != null && (list.size()!=200)) list.add(Double.valueOf(line));
            }
        catch (Exception e) {}


        return list;
    }


}
