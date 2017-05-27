package p4;

/**
 * Created by Егор on 21.11.2016.
 */
import javafx.geometry.Point3D;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.*;
import java.util.List;

class Vector3d{
    private ArrayList<Double> vector3d;
    public Vector3d(List<Double> list){
        vector3d= new ArrayList<>(list);
    }
    public Vector3d(){
        vector3d=new ArrayList<>();
    }
    public void add(Double xyz){
        vector3d.add(xyz);
    }
    public Double get(int i) {
        return vector3d.get(i);
    }
    public void set(int i, Double d){
        vector3d.set(i,d);
    }
}
class Matrix{
    private ArrayList<Vector3d> matrix;
    public Matrix(List<Vector3d> list){
        matrix=new ArrayList<>(list);
    }
    public Matrix(){
        matrix=new ArrayList<>();
    }
    public Matrix(Matrix m){
        this(m.matrix);
    }
    public void add(Vector3d vector3d){
        matrix.add(vector3d);
    }
    public Double get(int i, int j){
        return matrix.get(i).get(j);
    }
    public void set(int i, int j, Double d){
        matrix.get(i).set(j,d);
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
class MatrixMath{
    private static Matrix unitMatrix(){
        ArrayList<Vector3d> es= new ArrayList<Vector3d>();
        es.add(new Vector3d(Arrays.asList(new Double(1),new Double(0),new Double(0))));
        es.add(new Vector3d(Arrays.asList(new Double(0),new Double(1),new Double(0))));
        es.add(new Vector3d(Arrays.asList(new Double(0),new Double(0),new Double(1))));
        return new Matrix(es);
    }
    private static Matrix matrixMultiMatrix(Matrix a, Matrix b){
        Matrix result = new Matrix();
        for (int i = 0; i < 3; i++){
            Vector3d resultVector=new Vector3d();
            for (int j = 0; j < 3; j++) {
                Double r= a.get(i,0)*b.get(0,j)+
                        a.get(i,1)*b.get(1,j)+
                        a.get(i,2)*b.get(2,j);
                resultVector.add(r);
            }
            result.add(resultVector);
        }
        return result;
    }
    private static Vector3d matrixMultiVector3d(Matrix a, Vector3d v) {
        Vector3d result = new Vector3d();
        for (int i = 0; i < 3; i++){
            Double r= a.get(i,0)*+v.get(0)+
                    a.get(i,1)*v.get(1)+
                    a.get(i,2)*v.get(2);
            result.add(r);
        }
        return result;
    }
    public static Matrix createRotationMatrix(Double x,Double y,Double z){
        Matrix p = new Matrix();
        Matrix q = new Matrix();
        p=unitMatrix();

        p.set(1,1,Math.cos(x));
        p.set(1,2,-Math.sin(x));
        p.set(2,1,Math.sin(x));
        p.set(2,2,Math.cos(x));
        q=unitMatrix();
        q.set(2,2,Math.cos(y));
        q.set(2,0,-Math.sin(y));
        q.set(0,2,Math.sin(y));
        q.set(0,0,Math.cos(y));
        Matrix result = matrixMultiMatrix(p,q);
        p=unitMatrix();
        p.set(0,0,Math.cos(z));
        p.set(0,1,-Math.sin(z));
        p.set(1,0,Math.sin(z));
        p.set(1,1,Math.cos(z));
        result = matrixMultiMatrix(result,p);
        return result;
    }
    public static Point point3d(Double x, Double y, Double z, Matrix rotationMatrix){
        Vector3d v = new Vector3d();
        v.add(x);
        v.add(y);
        v.add(z);
        v= matrixMultiVector3d(rotationMatrix,v);
        Point p = new Point();
        p.x=v.get(0).intValue();
        p.y=v.get(1).intValue();
        return p;
    }
}

class LinePoint{
    public Point start;
    public Point end;
    public Double text=null;
    public LinePoint(){
    }
    public LinePoint(LinePoint linePoint){
        this.start=linePoint.start;
        this.end=linePoint.end;
        this.text=linePoint.text;
    }
}

class Plane4p3D{
    public List<Point3D> Point3DList = new ArrayList<>();
    //public Plane4p3D(3tochki){
    //    найтип последнюю точку
    //}
}
class Plane4p2D {
    public List<Point> PointList=new ArrayList<>();
    public Plane4p2D(){
    }
    public Plane4p2D(Plane4p2D plane4p2D){
        this.PointList=plane4p2D.PointList;
    }
}

class CoordinateSystem{
    private Double x=1.0, y=0.0, z=-1.0;//вот тут вся жиза
    private Matrix RotationMatrix;
    private List<Plane4p2D> plane4p2DList= new ArrayList<>();
    private List<Plane4p3D> plane4p3DList= new ArrayList<>();
    private List<Point3D> point3DList =new ArrayList<>();
    private List<Point> pointList = new ArrayList<>();
    private Point3D min,max;
    private Point3D coefficient;
    public CoordinateSystem(){
        RotationMatrix= MatrixMath.createRotationMatrix(x,y,z);
        min=new Point3D(0,0,0);
        max=new Point3D(300,300,300);
        coefficient =new Point3D(1,1,1);
    }
    private Point point3DIn2D(Point3D p3D){
        return MatrixMath.point3d((p3D.getX()-min.getX())* coefficient.getX(),
                (p3D.getY()-min.getY())* coefficient.getY(),
                (p3D.getZ()-min.getZ())* coefficient.getZ(),RotationMatrix);
    }
    private void refreshCoefficient(){
        Double x = 280/Math.abs(max.getX()-min.getX());
        Double y = 280/Math.abs(max.getY()-min.getY());
        Double z = 280/Math.abs(max.getZ()-min.getZ());
        coefficient=new Point3D(x,y,z);
    }
    public void recreateRotationMatrix(Double alfaX, Double alfaY, Double alfaZ){
        x=x+alfaX;
        y=y+alfaY;
        z=z+alfaZ;
        RotationMatrix= MatrixMath.createRotationMatrix(x,y,z);
        plane4p2DList.clear();
        for (Plane4p3D plane4p3D : plane4p3DList) {
            Plane4p2D buffPlane4p=new Plane4p2D();
            for (Point3D p3D: plane4p3D.Point3DList) {
                buffPlane4p.PointList.add(point3DIn2D(p3D));
            }
            plane4p2DList.add(buffPlane4p);
        }
        pointList.clear();
        for (Point3D point3D: point3DList) {
            pointList.add(point3DIn2D(point3D));
        }
    }
    public void setMinMax(Point3D min, Point3D max){
        this.min=min;
        this.max=max;
        plane4p2DList.clear();
        for (Plane4p3D plane4p3D : plane4p3DList) {
            Plane4p2D buffPlane4p=new Plane4p2D();
            for (Point3D p3D: plane4p3D.Point3DList) {
                buffPlane4p.PointList.add(point3DIn2D(p3D));
            }
            plane4p2DList.add(buffPlane4p);
        }
        pointList.clear();
        for (Point3D point3D: point3DList) {
            pointList.add(point3DIn2D(point3D));
        }

        refreshCoefficient();
        recreateRotationMatrix(x,y,z);
    }
    public List<LinePoint> getCoordinateAxis(){
        List<LinePoint> result= new ArrayList<>();
        LinePoint linePoint=new LinePoint();
        linePoint.start=MatrixMath.point3d(0.0,0.0,0.0,RotationMatrix);
        linePoint.end=MatrixMath.point3d(300.0,0.0,0.0,RotationMatrix);
        result.add(new LinePoint(linePoint));
        linePoint.end=MatrixMath.point3d(0.0,300.0,0.0,RotationMatrix);
        result.add(new LinePoint(linePoint));
        linePoint.end=MatrixMath.point3d(0.0,0.0,300.0,RotationMatrix);
        result.add(new LinePoint(linePoint));
        return result;
    }

    public List<LinePoint> getCoordinateNet() {
        List<LinePoint> result= new ArrayList<>();
        LinePoint linePoint=new LinePoint();
        for (int i = 0; i <= 10; i++) {
            linePoint.start=MatrixMath.point3d(0.0,new Double(i*30),0.0,RotationMatrix);
            linePoint.end=MatrixMath.point3d(300.0,new Double(i*30),0.0,RotationMatrix);
            linePoint.text = null;
            result.add(new LinePoint(linePoint));
            linePoint.start=MatrixMath.point3d(0.0,new Double(i*30),0.0,RotationMatrix);
            linePoint.end=MatrixMath.point3d(0.0,new Double(i*30),300.0,RotationMatrix);
            linePoint.text = i*Math.abs(min.getY()-max.getY())/10+min.getY();//по другому сделать
            result.add(new LinePoint(linePoint));
            linePoint.start=MatrixMath.point3d(new Double(i*30),0.0,0.0,RotationMatrix);
            linePoint.end=MatrixMath.point3d(new Double(i*30),300.0,0.0,RotationMatrix);
            linePoint.text = i*Math.abs(min.getX()-max.getX())/10+min.getX();;
            result.add(new LinePoint(linePoint));
            linePoint.start=MatrixMath.point3d(new Double(i*30),0.0,0.0,RotationMatrix);
            linePoint.end=MatrixMath.point3d(new Double(i*30),0.0,300.0,RotationMatrix);
            linePoint.text = null;
            result.add(new LinePoint(linePoint));
            linePoint.start=MatrixMath.point3d(0.0,0.0,new Double(i*30),RotationMatrix);
            linePoint.end=MatrixMath.point3d(0.0,300.0,new Double(i*30),RotationMatrix);
            linePoint.text = i*Math.abs(min.getZ()-max.getZ())/10+min.getZ();;
            result.add(new LinePoint(linePoint));
            linePoint.start=MatrixMath.point3d(0.0,0.0,new Double(i*30),RotationMatrix);
            linePoint.end=MatrixMath.point3d(300.0,0.0,new Double(i*30),RotationMatrix);
            linePoint.text = null;
            result.add(new LinePoint(linePoint));
        }
        return result;
    }
    public void addPlane(Plane4p3D plane4p3D){
        Plane4p2D buffPlane4p=new Plane4p2D();
        plane4p3DList.add(plane4p3D);
        for (Point3D p3D: plane4p3D.Point3DList) {
            buffPlane4p.PointList.add(point3DIn2D(p3D));
        }
        plane4p2DList.add(buffPlane4p);
    }
    public void addPoint3D(Point3D point3D){
        point3DList.add(point3D);
        pointList.add(point3DIn2D(point3D));
    }
    public List<Point> getPointList(){
        return pointList;
    }
    public List<Plane4p2D> getListPlane(){
        return plane4p2DList;

    }
}

class MyJFrame extends JFrame  {
    public static final int DEFAULT_WIDTH = 700;
    public static final int DEFAULT_HEIGHE = 700;

public static int MaxX=150;
public ButtonPanel panel;
    private CoordinateSystem CS;//контер страйк
    public MyJFrame()  {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHE);
        CS=new CoordinateSystem();//подключаешь

        CS.setMinMax(new Point3D(-150,-30,-150),new Point3D(MaxX,30,400));//поставить мнимальное значение сетки и максимальное
        ArrayList<ArrayList<Point3D>>  listOfList = SplitPoints.splitPoints(3,1,ReadData.readData());
        /*for(ArrayList<Point3D> a:listOfList){
            double mas[] = p4.MultiRegress.regress(a);
            CS.addPoint3D(new Point3D(mas[0], mas[1], mas[2]));
        }*/
        ArrayList<Point3D> anotherList = new ArrayList<>();
        for (int i = 0; i < listOfList.size(); i++) {
            try{
                System.out.println(i);
                double mas[] = p4.MultiRegress.regress(listOfList.get(i));
                CS.addPoint3D(new Point3D(mas[0], mas[1], mas[2]));
                anotherList.add(new Point3D(mas[0],mas[1],mas[2]));
            }
            catch (Exception e){
                System.out.println(i);
            }
        }
      /*  ArrayList<Point3D> anotherList = new ArrayList<>();
        for (int i = 0; i < listOfList.size(); i++) {
            try{
            System.out.println(i);
            double mas[] = p4.MultiRegress.regress(listOfList.get(i));
            CS.addPoint3D(new Point3D(mas[0], mas[1], mas[2]));
                anotherList.add(new Point3D(mas[0],mas[1],mas[2]));
            }
            catch (Exception e){
                System.out.println(i);
            }
        }
        double mas2[] = p4.MultiRegress.regress(anotherList);
        Plane4p3D p1 = new Plane4p3D();
        p1.Point3DList.add(new Point3D(30,-12,mas2[3]));
        p1.Point3DList.add(new Point3D(30,12,mas2[4]));
        p1.Point3DList.add(new Point3D(-267,-27,mas2[5]));
        CS.addPlane(p1);

        Plane4p3D[] array = new Plane4p3D[179];
        for (int i = 0; i <178 ; i++) {
            array[i]= new Plane4p3D();
            array[i].Point3DList.add(new Point3D(anotherList.get(i).getX(),anotherList.get(i).getY(),anotherList.get(i).getZ()));
            array[i].Point3DList.add(new Point3D(anotherList.get(i+1).getX(),anotherList.get(i+1).getY(),anotherList.get(i+1).getZ()));
            CS.addPlane(array[i]);
        }*/

        /*ArrayList<Point3D> listAr = ReadData.readData();

            double mas[] = p4.MultiRegress.regress(listAr);
            CS.addPoint3D(new Point3D(mas[0], mas[1], mas[2]));*/
        /*Plane4p3D[] array = new Plane4p3D[179];
        for (int i = 0; i < 179; i++) {
            CS.addPlane(new Plane4p3D().Point3DList.add(new Point3D(0,0,0)).;);

        }*/
       /*
        Plane4p3D p3 = new Plane4p3D();
        p3.Point3DList.add(new Point3D(0,0,0));
        p3.Point3DList.add(new Point3D(0,50,0));
        p2.Point3DList.add(new Point3D(0,100,50));
        p2.Point3DList.add(new Point3D(15,0,50));
        CS.addPlane(p3);
        CS.setMinMax(new Point3D(0,0,0),new Point3D(100,100,100));*/
        setTitle("");
        panel = new ButtonPanel(CS,this);
        add(panel);
        repaint();
    }
    public void paint(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, DEFAULT_WIDTH,DEFAULT_HEIGHE);
        g.setColor(new Color(0, 0, 0));
        List<LinePoint> linePointList = CS.getCoordinateAxis();
        Integer divWidth=DEFAULT_WIDTH/2;
        Integer divHeigth=DEFAULT_HEIGHE/2;
        for (LinePoint linePoint: linePointList) {
            g.drawLine(divWidth + linePoint.start.x, divHeigth + linePoint.start.y, divWidth + linePoint.end.x, divHeigth + linePoint.end.y);
        }
        linePointList.clear();
        linePointList = CS.getCoordinateNet();


        for (LinePoint linePoint: linePointList) {
            g.setColor(new Color(150, 150, 150));
            g.drawLine(divWidth + linePoint.start.x, divHeigth + linePoint.start.y, divWidth + linePoint.end.x, divHeigth + linePoint.end.y);
            g.setColor(new Color(0, 0, 0));
            if (linePoint.text!=null) g.drawString(linePoint.text.toString(),divWidth+linePoint.end.x,divHeigth+linePoint.end.y);
        }
        Random r = new Random((int)(Math.random()*100));
        Polygon polygon = new Polygon();
        List<Plane4p2D> plane4p2DList = CS.getListPlane();
        for (Plane4p2D plane4p2D : plane4p2DList) {
            polygon.reset();
            for (Point point : plane4p2D.PointList) {
                polygon.addPoint(divWidth + point.x, divHeigth + point.y);
            }
            g.setColor(new Color(197, 184, 225));
            g.fillPolygon(polygon);
            g.setColor(new Color(0, 0, 0));
            g.drawPolygon(polygon);
        }
        List<Point> pointList=CS.getPointList();
        final int SIZE_POINT=1;
        for (Point point: pointList) {
            polygon.reset();
            polygon.addPoint(divWidth+point.x-SIZE_POINT,divHeigth+point.y-SIZE_POINT);
            polygon.addPoint(divWidth+point.x+-SIZE_POINT,divHeigth+point.y+SIZE_POINT);
            polygon.addPoint(divWidth+point.x+SIZE_POINT,divHeigth+point.y+SIZE_POINT);
            polygon.addPoint(divWidth+point.x+SIZE_POINT,divHeigth+point.y-SIZE_POINT);
            g.setColor(new Color(32, 26, 249));
            g.fillPolygon(polygon);
        }

    }
}
class ButtonPanel extends JPanel {

    public ButtonPanel(CoordinateSystem CS,MyJFrame m ) {
        m.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    CS.recreateRotationMatrix(0.05, 0.0, 0.0);
                    m.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    CS.recreateRotationMatrix(-0.05, 0.0, 0.0);
                    m.repaint();
                }

                if (e.getKeyCode() == KeyEvent.VK_S) {
                    CS.recreateRotationMatrix(0.0, 0.05, 0.0);
                    m.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    CS.recreateRotationMatrix(0.0, -0.05, 0.0);
                    m.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_Z) {
                    CS.recreateRotationMatrix(0.0, 0.0, 0.05);
                    m.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_X) {
                    CS.recreateRotationMatrix(0.0, 0.0,-0.05);
                    m.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_Y) {
                    m.MaxX+=200;
                    CS.recreateRotationMatrix(0.0, 0.0,0.0);
                    m.repaint();
                }
            }
        });
    }
}


public class Main {
    public static void main(String[] a) {
        MyJFrame f = new MyJFrame();
        f.setTitle("Drawing Graphics in Frames");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

}

