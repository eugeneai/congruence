package Package2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Егор on 12.11.2016.
 */
public class Regress {
    public static double[] regressAnalys(HashMap<Double,Double> myMap,int lenBorder){
        double[] mas = new double[6];
        double k = getK(myMap);//опредеяем угловой коэфициент в прямой
        double b =getB(myMap);

        ArrayList<Double> list = new ArrayList<>();
        for(Map.Entry<Double,Double> map:myMap.entrySet()){
            list.add(map.getKey());
        }
        Collections.sort(list);

        for(int i=0;i<lenBorder;i++){
            list.remove(lenBorder-i-1);
            list.remove(i);

        }


        mas[0] = list.get(0);
        mas[1] = list.get(list.size()-1);
        mas[2] = b+list.get(0)*k;
        mas[3] = k*list.get(list.size()-1)+b;
        mas[4]= k;
        mas[5] = b;


        return mas;
    }

    private static double sredneeXY(HashMap<Double,Double> myMap) {
        double sum = 0.0;
        for (Map.Entry<Double, Double> m : myMap.entrySet()) {
            sum += m.getKey() * m.getValue();

        }
        return sum / myMap.size();
    }

    private static double sredneeY(HashMap<Double,Double> myMap){
        double sum=0.0;
        for (Map.Entry<Double, Double> m : myMap.entrySet()) {
            sum +=m.getValue();
        }
        return sum/myMap.size();
    }
    private static double sredneeX(HashMap<Double,Double> myMap){
        double sum=0.0;
        for (Map.Entry<Double, Double> m : myMap.entrySet()) {
            sum +=m.getKey();
        }
        return sum/myMap.size();
    }
    private static double sredneeKvadratX(HashMap<Double,Double> myMap) // поиск
    {
        double sum=0.0;
        for (Map.Entry<Double, Double> m : myMap.entrySet()) {
            sum+=m.getKey()*m.getKey();
        }
        return sum/myMap.size();
    }
    private static double getK(HashMap<Double,Double> myMap){
        double k;
        k=(sredneeXY(myMap)-(sredneeX(myMap)*sredneeY(myMap)))
                /(sredneeKvadratX(myMap)
                -sredneeX(myMap)*sredneeX(myMap));
        return k;
    }
    private static double getB(HashMap<Double,Double> myMap){
        double B = sredneeY(myMap) - getK(myMap)*sredneeX(myMap);
        return B;
    }

    public static void main(String[] args) {
        HashMap<Double,Double> map = Package2.ReadData.readData();
        double[] dd = regressAnalys(map,0);
        System.out.println(dd[0]);
        System.out.println(dd[1]);
    }

}
