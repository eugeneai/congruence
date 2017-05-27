package Package2;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Егор on 12.11.2016.
 */
public class ReadData {
    public static HashMap<Double,Double> readData(){
        HashMap<Double,Double> myMap = new HashMap<Double,Double>();
        boolean flag2or1;

        ArrayList<String> list = new ArrayList<String>();

        try{
            File fin = new File("D:/data.txt");
            BufferedReader reader = new BufferedReader(new FileReader(fin));
            String line;
            while ((line = reader.readLine()) != null ) {list.add(line);}

            if (!defineSetOfData(list.get(0))) {
                for(int i = 0; i<list.size() ;i++){
                    myMap.put( (double) i+1 ,Double.valueOf(list.get(i)));
                }
            }
            else{
                for(int i = 0; i<list.size() ;i++){
                    myMap.put(Double.valueOf(splitStrings(list.get(i))[0]) ,
                            Double.valueOf(splitStrings(list.get(i))[1]));
                }
            }

        }
        catch (Exception e) {}

        System.out.println(myMap.size());
        return myMap;
    }

    private static boolean defineSetOfData(String s){ //true, если в наборе 2 столбца с данными

        for(int i = 0;i<s.length();i++){
            if (s.charAt(i)== ' ') {
                s = s.substring(i + 1);
                i=-1;
            }
            else i=s.length();
        }
        while(s.contains("  ")) {
            String replace = s.replace("  ", " ");
            s=replace;
        }

        for(int i=0;i<s.length()-1;i++){
            if(s.charAt(i) == ' ' && (s.charAt(i+1)=='-' ||  (s.charAt(i+1)>='0' && s.charAt(i+1)<='9') )){
                return true;

            }
        }
        return false;
    }

    private static String[] splitStrings(String s){
        for(int i = 0;i<s.length();i++){
            if (s.charAt(i)== ' '){
                s = s.substring(i+1);
                i=-1;
            }
            else i=s.length();
        }

        while(s.contains("  ")) {
            String replace = s.replace("  ", " ");
            s=replace;

        }

        String[] st = s.split(" ");
        return st;
    }
    public static void main(String[] args)  {
        HashMap <Double,Double> map = readData();
        for (Map.Entry<Double, Double> pair : map.entrySet())
        {
            Double key = pair.getKey();                      //ключ
            Double value = pair.getValue();                  //значение
            System.out.println(key + ":" + value);
        }

    }
}
