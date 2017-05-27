package Package2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Егор on 13.11.2016.
 */
public class Bourders {
    public static double[] getBorder(HashMap<Double,Double> myMap,int lenBorder){
        double[] mas = new double[2];

        ArrayList<Double> list = new ArrayList<>();
        for(Map.Entry<Double,Double> map:myMap.entrySet()){
            list.add(map.getKey());
        }

        Collections.sort(list);

        for(int i=0;i<lenBorder;i++){
            list.remove(i);
            list.remove(lenBorder-i-1);
        }
        mas[0] = list.get(0);
        mas[1] = list.get(list.size()-1);
        return mas;
    }

    public static void main(String[] args) {
        HashMap<Double,Double> map = ReadData.readData();
        double[] dd = getBorder(map,4);
        System.out.println(dd[0]+"  "+dd[1]);
    }

}
