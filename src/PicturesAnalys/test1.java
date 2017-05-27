package PicturesAnalys;

/**
 * Created by Ольга on 09.12.2016.
 */import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class test1 extends JFrame {
    public static BufferedImage myImage;
    public static Image visibleImage;
    public JFrame jf;
    public JPanel jp = new JPanel();
    test1(){
        JFrame jf = new JFrame();
        jf.setSize(300,300);
        jp.setLayout(new BorderLayout());
        jf.add(jp);
        jf.setVisible(true);

    }

    public static void main(String[] args) throws IOException{
        visibleImage = (Image) ImageIO.read(new File("C://002.jpg"));
        test1 t = new test1();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(visibleImage, 100, 100, jf);
    }
}
