package newPackage1;

import java.util.ArrayList;

/**
 * Created by Егор on 10.11.2016.
 */
public class Regres {


    public static double[] getRegres(double start,double step ,ArrayList<Double> aList,int otrezok,int startIndex){
        double[] mas = new double[2];
        double k = getK(start,step,aList,otrezok,startIndex); //опредеяем угловой коэфициент в прямой
        double b = getB(start,step,aList,otrezok,startIndex);

        mas[0] = start*k+b;
        mas[1] = (start+otrezok*step)*k+b;
        return mas;

    }

    private static double sredneeXY(double start,double step ,ArrayList<Double> aList,int otrezok,int startIndex){
        double summ=0.0;
        int ch=0;
        for(double i=start;i<otrezok;i+=step){
            summ+= aList.get(ch+startIndex)*start;
            ch++;
            if(start>=otrezok) i=otrezok;
        }

        return summ/ch;
    }

    private static double sredneeY(ArrayList<Double> aList,int otrezok,int startIndex)
    {
        double summ=0.0;
        for(int i=0;i<otrezok;i++){
            summ+= aList.get(i+startIndex);
        }
        return summ/otrezok;
    }

    private static double sredneeX(double start,double step ,int otrezok) // поиск
    {
        double summ=0.0;
        for(int i=0;i<otrezok;i++){
            summ+= start;
            start += step;
        }
        return summ/otrezok;
    }
    private static double sredneeKvadratX(double start,double step ,int otrezok) // поиск
    {
        double summ=0.0;
        for(int i=0;i<otrezok;i++){
            summ+= start*start;
            start += step;
        }
        return summ/otrezok;
    }

    private static double getK(double start,double step ,ArrayList<Double> aList,int otrezok,int startIndex){
        double k;
        k=(sredneeXY(start,step,aList,otrezok,startIndex)-(sredneeX(start,step,otrezok)*sredneeY(aList,otrezok,startIndex)))
                /(sredneeKvadratX(start,step,otrezok)
                -sredneeX(start,step,otrezok)*sredneeX(start,step,otrezok));
    return k;
    }

    private static double getB(double start,double step ,ArrayList<Double> aList,int otrezok,int startIndex){
        double B = sredneeY(aList,otrezok,startIndex) - getK(start,step,aList,otrezok,startIndex)*sredneeX(start,step,otrezok);
        return B;

    }
}
