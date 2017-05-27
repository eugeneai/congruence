package Package2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;

/**
 * Created by Егор on 12.11.2016.
 */
public class SplitMap {
    public static ArrayList<HashMap<Double,Double>> splitMap(int len,int step,HashMap<Double,Double> mainMap){
        ArrayList<HashMap<Double,Double>> listMaps = new ArrayList<>();
        int count=0;
        for(int i = len;i<mainMap.size();i+=step){
            count++;
        }

        System.out.println(count);
        Iterator<HashMap.Entry<Double, Double>> iterator = mainMap.entrySet().iterator();
        ArrayList<Double> listOfKeys = new ArrayList<>();
        while (iterator.hasNext())
        {
            Map.Entry<Double, Double> pair = iterator.next();
            listOfKeys.add(pair.getKey());
        }
        Collections.sort(listOfKeys); //потому что HshMap не сортирует, здесь сортируются ключи


        int k=0;
        for(int j=0;j<count;j++) {
            listMaps.add(new HashMap<Double, Double>());
            for (int i = 0; i < len; i++) {

                listMaps.get(j).put(listOfKeys.get(k+i), mainMap.get(listOfKeys.get(k+i)));
            }
            k+=step;
        }
        return listMaps;
    }

    public static void main(String[] args) {
        HashMap<Double,Double> map = Package2.ReadData.readData();


        ArrayList<HashMap<Double,Double>> listMaps = splitMap(7,2,map);
        Iterator<HashMap.Entry<Double, Double>> iterator;

        for (HashMap<Double,Double> map1: listMaps)
        {
            for (Map.Entry<Double, Double> pair : map1.entrySet())
            {
                Double key = pair.getKey();                      //ключ
                Double value = pair.getValue();                  //значение
                System.out.println(key + ":" + value);
            }
            System.out.println("/////////////////////");
        }
    }


}
