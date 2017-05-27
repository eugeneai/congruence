/*
package Package3;

*/
/**
 * Created by Егор on 15.11.2016.
 *//*

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;


import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Test extends JPanel {

    public static BufferedImage image;
    public static JFrame picJframe = new JFrame();

    public static void main (String args[]){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Test t = new Test();

        VideoCapture camera = new VideoCapture(0);
        camera.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 1280);
        camera.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 720);


        if(!camera.isOpened()){
            System.out.println("Error");
        }
        else {
            int index = 0;
            Mat frame = new Mat();

            while(true){
                if (camera.read(frame)){
                    //System.out.println("Captured Frame Width " + frame.width() + " Height " + frame.height());

                    //Imgcodecs.imwrite("camera" + (index++) + ".jpg", frame);
                    BufferedImage image = t.convertMatToBufferedImage(frame);

                    t.window(image, "Original Image", 0, 0);
                    System.out.println("OK");

                }
            }
        }
        camera.release();
    }

    private static BufferedImage convertMatToBufferedImage(Mat mat) {
        byte[] data = new byte[mat.width() * mat.height() * (int)mat.elemSize()];
        int type;
        mat.get(0, 0, data);

        switch (mat.channels()) {
            case 1:
                type = BufferedImage.TYPE_BYTE_GRAY;
                break;
            case 3:
                type = BufferedImage.TYPE_3BYTE_BGR;
                // bgr to rgb
                byte b;
                for(int i=0; i<data.length; i=i+3) {
                    b = data[i];
                    data[i] = data[i+2];
                    data[i+2] = b;
                }
                break;
            default:
                throw new IllegalStateException("Unsupported number of channels");
        }

        BufferedImage out = new BufferedImage(mat.width(), mat.height(), type);

        out.getRaster().setDataElements(0, 0, mat.width(), mat.height(), data);

        return out;
    }


    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    public Test() {
    }

    public Test(BufferedImage img) {
        image = img;
    }

    //Show image on window
    public void window(BufferedImage img, String text, int x, int y) {
        JFrame frame0 = picJframe;
        frame0.getContentPane().add(new Test(img));
        frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame0.setTitle(text);
        frame0.setSize(img.getWidth(), img.getHeight() + 30);
        frame0.setLocation(x, y);
        frame0.setVisible(true);
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

*/
