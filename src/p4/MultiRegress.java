package p4;

import Jama.*;
import Jama.Matrix;
import javafx.geometry.Point3D;

import java.util.ArrayList;

/**
 * Created by Егор on 22.11.2016.
 */
public class MultiRegress {
    public static double[] regress(ArrayList<Point3D> list){
        double mas[] = new double[6];
        double matr[][] = {{SumX1(list),SumX2(list),(double) list.size()},
                {SumX1kvadrat(list),SumX1X2(list),SumX1(list)},
                {SumX1X2(list),SumX2kvadrat(list),SumX2(list)}};
        double b[][] = {{SumY(list)},{SumX1Y(list)},{SumX2Y(list)}};


        Jama.Matrix A = new Matrix(matr);
        Jama.Matrix bb = new Matrix(b);

        Jama.Matrix x = A.solve(bb);


        mas[0] = x.get(0,0);
        mas[1] = x.get(1,0);
        mas[2] = x.get(2,0);
        mas[3] = 30*mas[0]-12*mas[1]+mas[2];
        mas[4] = 30*mas[0]+12*mas[1]+mas[2];
        mas[5] = -267*mas[0]-207*mas[1]+mas[2];



       /*if(Math.abs(KoefrX1X2(list))>0.7) {
           mas[0] = 10000;
           mas[1] = 10000;
           mas[2] = 10000;
       }*/
       // System.out.println(mas[0]);
       // System.out.println(mas[0]+" "+mas[1]+" "+mas[2]);
       // System.out.println("rX1X2= "+KoefrX1X2(list)+" rX1Y= "+KoefrX1Y(list)+" rX2Y= "+KoefrX2Y(list)+" R= "+KoefR(list));
        return mas;
    }

    private static double KoefR(ArrayList<Point3D> list){
        return Math.sqrt(1-(1+2*KoefrX1X2(list)*KoefrX1Y(list)*KoefrX2Y(list)-KoefrX1Y(list)*KoefrX1Y(list)-KoefrX1X2(list)*KoefrX1X2(list)-KoefrX2Y(list)*KoefrX2Y(list))
                /(1-KoefrX1X2(list)*KoefrX1X2(list)));
    }
    private static double KoefrX1X2(ArrayList<Point3D> list){
        double koef =0;
        return koef = (SumX1X2(list)/list.size() - SumX1(list)*SumX2(list)/(list.size()*list.size()) )/
                (Math.sqrt( (SumX1kvadrat(list)/list.size()-SumX1(list)*SumX1(list)/(list.size()*list.size()))
                        *(SumX2kvadrat(list)/list.size()-SumX2(list)*SumX2(list)/(list.size()*list.size()))));

    }

    private static double KoefrX1Y(ArrayList<Point3D> list){
        double koef =0;
        return koef = (SumX1Y(list)/list.size() - SumX1(list)*SumY(list)/(list.size()*list.size()) )/
                (Math.sqrt( (SumX1kvadrat(list)/list.size()-SumX1(list)*SumX1(list)/(list.size()*list.size()))
                        *(SumYkvadrat(list)/list.size()-SumY(list)*SumY(list)/(list.size()*list.size()))));

    }
    private static double KoefrX2Y(ArrayList<Point3D> list){
        double koef =0;
        return koef = (SumX2Y(list)/list.size() - SumX2(list)*SumY(list)/(list.size()*list.size()) )/
                (Math.sqrt( (SumX2kvadrat(list)/list.size()-SumX2(list)*SumX2(list)/(list.size()*list.size()))
                        *(SumYkvadrat(list)/list.size()-SumY(list)*SumY(list)/(list.size()*list.size()))));

    }
    private static double SumX1 (ArrayList<Point3D> list){
        double sum = 0;
        for(Point3D a:list){
            sum+=a.getX();
        }
        return sum;
    }
    private static double SumX1kvadrat (ArrayList<Point3D> list){
        double sum = 0;
        for(Point3D a:list){
            sum+=a.getX()*a.getX();
        }
        return sum;
    }
    private static double SumYkvadrat (ArrayList<Point3D> list){
        double sum = 0;
        for(Point3D a:list){
            sum+=a.getZ()*a.getZ();
        }
        return sum;
    }
    private static double SumX2 (ArrayList<Point3D> list){
        double sum = 0;
        for(Point3D a:list){
            sum+=a.getY();
        }
        return sum;
    }

    private static double SumY (ArrayList<Point3D> list){
        double sum = 0;
        for(Point3D a:list){
            sum+=a.getZ();
        }
        return sum;

    }

    private static double SumX2kvadrat (ArrayList<Point3D> list){
        double sum = 0;
        for(Point3D a:list){
            sum+=a.getY()*a.getY();
        }
        return sum;
    }

    private static double SumX3 (ArrayList<Point3D> list){
        double sum = 0;
        for(Point3D a:list){
            sum+=a.getZ();
        }
        return sum;
    }

    private static double SumX1X2 (ArrayList<Point3D> list){
        double sum = 0;
        for(Point3D a:list){
            sum+=a.getX()*a.getY();
        }
        return sum;
    }

    private static double SumX1Y (ArrayList<Point3D> list){
        double sum = 0;
        for(Point3D a:list){
            sum+=a.getX()*a.getZ();
        }
        return sum;
    }

    private static double SumX2Y (ArrayList<Point3D> list){
        double sum = 0;
        for(Point3D a:list){
            sum+=a.getZ()*a.getY();
        }
        return sum;
    }

    public static void main(String[] args) {
        double[][] array = {{1.,2.,-1.},{0.,1.,-1.},{0.,0.,1.}};
        Matrix A = new Matrix(array);

        double b[][] = {{9.},{1.},{4.}};
        Matrix bb =new  Matrix(b);
        Matrix x = A.solve(bb);
        double mas[] = new double[3];
        System.out.println(x.get(0,0));
        System.out.println(x.get(1,0));
        System.out.println(x.get(2,0));

    }

}
