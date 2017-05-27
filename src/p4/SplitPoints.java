package p4;

import javafx.geometry.Point3D;

import java.util.ArrayList;

/**
 * Created by Егор on 22.11.2016.
 */
public class SplitPoints {
    public static ArrayList<ArrayList<Point3D>> splitPoints(int len,int step,ArrayList<Point3D> list){
        ArrayList<Point3D> mainList = list;
       /* for(int i =0;i<186;i++){
            mainList.remove(0);
        }*/
        int count=0;
        for(int i = len;i<mainList.size();i+=step){
            count++;
        }

        ArrayList<ArrayList<Point3D>> listOfLists = new ArrayList<>();
        int k = 0;
        for (int j = 0; j < count; j++) {
            listOfLists.add(new ArrayList<Point3D>());
            for (int i = 0; i < len ; i++) {
                listOfLists.get(j).add(mainList.get(k+i));
            }
            k+=step;
        }
        return listOfLists;
    }

    public static void main(String[] args) {
       /* ArrayList<ArrayList<Point3D>> listOfLists = splitPoints(4,2);
         for(ArrayList<Point3D> x:listOfLists){
             for(Point3D y:x){
                 System.out.println(y.getX()+" "+y.getY()+" "+y.getZ());
             }
             System.out.println();
         }*/

    }
}
