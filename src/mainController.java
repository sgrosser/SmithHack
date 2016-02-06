
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class mainController extends Component {
BufferedImage image;
static BufferedImage inputSourceImage;
BufferedImage OUTPUTSOURCEIMAGE;
JFrame JFrame1;
JFrame JFrame2;
BufferedImage binaryDilationOutputImage;
BufferedImage i;
BinaryDilation binaryDilation1=new BinaryDilation();
 
public void readInputImage()  throws IOException{
JFrame1=new JFrame("Input Image");
JFrame1.setSize(1000,1000);
JFrame1.setBackground(Color.BLACK);
inputSourceImage=ImageIO.read(new File("/Users/tanasn/Desktop/bookPage2.png"));
JFrame1.add(new JLabel(new ImageIcon(inputSourceImage)));
JFrame1.setVisible(true);
}
    public BufferedImage invertImage(BufferedImage im) throws IOException{
    BufferedImage INVIMAGECONVERSION =new BufferedImage(im.getWidth(),im.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
    Graphics2D g=INVIMAGECONVERSION.createGraphics();
    g.drawRenderedImage(im, null);
    g.dispose();
    short[] inversionPixelArray=new short[256];
    for(int i=0;i<inversionPixelArray.length;i++){
    inversionPixelArray[i]=(short)(255-i);
    }
    BufferedImageOp invertOp = new LookupOp(new ShortLookupTable(0, inversionPixelArray), null);
    BufferedImage invertedImage=invertOp.filter(INVIMAGECONVERSION,INVIMAGECONVERSION);
ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
BufferedImage bi = op.filter(invertedImage, null);
return bi;
   
    }
    public void writeOutputFile(BufferedImage bi) throws IOException{
    File outputFile=new File("/Users/tanasn/Desktop/bookPage2result.png");
    ImageIO.write(bi, "PNG", outputFile);
   
    }
    public BufferedImage executeBinaryDilation(BufferedImage b) throws IOException{
    BufferedImage BINARYDILATEDIMAGE=binaryDilation1.dilate(b);
   
   
    return BINARYDILATEDIMAGE;
   
   
    }
    public void showOutputFile()throws IOException{
    OUTPUTSOURCEIMAGE=ImageIO.read(new File("/Users/tanasn/Desktop/bookPage2result.png"));
   JFrame2=new JFrame("edited image");
   JFrame2.setSize(1000,1000);
   JFrame2.setBackground(Color.BLACK);
JFrame2.add(new JLabel(new ImageIcon(OUTPUTSOURCEIMAGE)));
JFrame2.setVisible(true);
   
    }
    public static void main(String args[])throws IOException{
    mainController mainControllerObject=new mainController();
    mainControllerObject.readInputImage();
    mainControllerObject.writeOutputFile(mainControllerObject.executeBinaryDilation(mainControllerObject.invertImage(inputSourceImage)));
    mainControllerObject.showOutputFile();
    }
    
    }


