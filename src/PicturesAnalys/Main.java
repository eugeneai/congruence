package PicturesAnalys;

import com.sun.imageio.plugins.jpeg.JPEG;
import javafx.geometry.Point3D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ольга on 09.12.2016.
 */
public class Main extends JFrame {
    public static int Height = 1;
    public static int Width = 1;
    public static BufferedImage bfImage;
    public static ArrayList<Color> list = new ArrayList<>();
    public static void main(String[] args) {
        try {
            bfImage = ImageIO.read(new File("C://Users/Егор/Downloads/Lenna.png"));
            Height = bfImage.getHeight()+50;
            Width = bfImage.getWidth()+50;
            System.out.println(bfImage);
        } catch (IOException e) {
        }
        Main m = new Main();
        m.setSize(Width,Height);
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setVisible(true);


    }
    public void paint(Graphics g) {
        super.paint(g);
        int shift =20;
        ArrayList<Color> listOfPixels = fromImageToListOfPixels(bfImage);

        System.out.println(listOfPixels.size());
        ArrayList<Point3D> listofPoint = new ArrayList<>();
        for(Color x:listOfPixels){
            listofPoint.add(new Point3D(x.getRed(),x.getGreen(),x.getBlue()));
        }
        /*for (int i = 0; i <listOfPixels.size() ; i++) {
            System.out.println(i);
            System.out.println("point = "+listofPoint.get(i).getX()+" "+listofPoint.get(i).getY()+" "+listofPoint.get(i).getZ());
            System.out.println("pixle = "+listOfPixels.get(i).getRed()+" "+listOfPixels.get(i).getGreen()+" "+listOfPixels.get(i).getBlue());
        }*/
        listOfPixels.clear();
        ArrayList<ArrayList<Point3D>>  listOfList = p4.SplitPoints.splitPoints(5,1,listofPoint);
        ArrayList<Point3D> anotherListofPoint = new ArrayList<>();
        int mass[] = new int[3];
        ArrayList<double[]> tempList = new ArrayList<>();
         for (int i = 0; i < listOfList.size(); i++) {
            try{
                //System.out.println(i);
                double mas[] = p4.MultiRegress.regress(listOfList.get(i));
                double d[] = new double[3];

                d[0] = mas[0];
                d[1] = mas[1];
                d[2] = mas[2];
                //System.out.println(d[0]+" "+d[1]+" "+d[2]);
                tempList.add(d);

            }
            catch (Exception e){ }
        }
        double[] MaxMinX = FindMaxMin(tempList,0);
        double[] MaxMinY = FindMaxMin(tempList,1);
        double[] MaxMinZ = FindMaxMin(tempList,2);
        System.out.println(MaxMinX[0]+" "+MaxMinX[1]);
        System.out.println(MaxMinY[0]+" "+MaxMinY[1]);
        System.out.println(MaxMinZ[0]+" "+MaxMinZ[1]);

        for (int i = 0; i < listOfList.size(); i++) {
            try{
                //System.out.println(i);
                double mas[] = p4.MultiRegress.regress(listOfList.get(i));
                mas[0] = ((mas[0]-MaxMinX[1])/(MaxMinX[0]-MaxMinX[1]))*256;
                mas[1] = ((mas[1]-MaxMinY[1])/(MaxMinY[0]-MaxMinY[1]))*256;
                mas[2] = ((mas[2]-MaxMinZ[1])/(MaxMinZ[0]-MaxMinZ[1]))*256;
                //System.out.println(mas[0]+" "+mas[1]+" "+mas[2]);
                if(mas[0]>255){
                    mas[0]=255;
                }
                if(mas[0]<0){
                    mas[0]=mas[0]*-1;
                }
                if(mas[1]>255){
                    mas[1]=255;
                }
                if(mas[1]<0){
                    mas[1]=-1*mas[1];
                }
                if(mas[2]>255){
                    mas[2]=255;
                }
                if(mas[2]<0){
                    mas[2]=-1*mas[2];
                }
                listOfPixels.add(new Color((int)mas[0],(int)mas[1],(int)mas[2]));
            }
            catch (Exception e){
                listOfPixels.add(new Color(255,255,255));

            }
        }

        /*for(Point3D x:anotherListofPoint){
            try {
                listOfPixels.add(new Color((float) x.getX(), (float)x.getY(), (float) x.getZ()));
            }catch (Exception e)
            {
                listOfPixels.add(new Color(1f,1f,1f));

            }
        }*/
        System.out.println(listOfPixels.size());
            int k=0;
            try {
                for (int i = 0; i < bfImage.getHeight(); i++) {

                    if (i % 2 == 0) {
                        for (int j = 0; j < bfImage.getWidth(); j++) {
                            g.drawLine(j + shift, i + shift, j + shift, i + shift);
                            g.setColor(listOfPixels.get(k));
                            k++;
                        }
                    } else {
                        for (int j = bfImage.getWidth() - 1; j >= 0; j--) {
                            g.drawLine(j + shift, i + shift, j + shift, i + shift);
                            g.setColor(listOfPixels.get(k));
                            k++;
                        }
                    }
                }
            }
            catch (Exception e){

            }


    }

    public static double[] FindMaxMin(ArrayList<double[]> tempList,int pl ){
        double maxmin[] = new double[2];
        double max = 0;
        double min = tempList.get(0)[pl];
        if(pl==0) {
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i)[pl] >= max && tempList.get(i)[pl] < 256) { // условия для сильно выпадающийх значений
                    max = tempList.get(i)[pl];
                }
            }
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i)[pl] < min && tempList.get(i)[pl] > -256) { // условия для сильно выпадающийх значений
                    min = tempList.get(i)[pl];
                }
            }
        }
        else if (pl == 1){
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i)[pl] >= max && tempList.get(i)[pl] < 256) {
                    max = tempList.get(i)[pl];
                }
            }
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i)[pl] < min && tempList.get(i)[pl] > -256) {
                    min = tempList.get(i)[pl];
                }
            }
        }else if (pl == 2){
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i)[pl] >= max && tempList.get(i)[pl] < 256) {
                    max = tempList.get(i)[pl];
                }
            }
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i)[pl] < min && tempList.get(i)[pl] > -256) {
                    min = tempList.get(i)[pl];
                }
            }
        }


        maxmin[0] = max;
        maxmin[1] = min;
        return maxmin;
    }
    public ArrayList<Color> fromImageToListOfPixels(BufferedImage img){
        ArrayList<Color> list = new ArrayList<>();
        int k=0,p=0;
        try {
            for (int i = 0; i < img.getHeight(); i++) {
                k=i;
                if (i % 2 == 0) {
                    for (int j = 0; j < img.getWidth(); j++) {
                        p=j;
                        list.add(new Color(img.getRGB(j, i)));
                    }
                } else {
                    for (int j = img.getWidth()-1 ; j >= 0; j--) {
                        list.add(new Color(img.getRGB(j, i)));
                    }
                }
            }
        }catch(Exception e){
            System.out.println(k+" "+p);
        }
        return list;
    }
    public BufferedImage grayscale(BufferedImage img) {
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));

                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);

                Color newColor =
                        new Color(
                                red + green + blue,
                                red + green + blue,
                                red + green + blue);

                img.setRGB(j, i, newColor.getRGB());
            }
        }

        return img;
    }
}
