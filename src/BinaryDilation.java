import  org.opencv.imgproc.Imgproc;

import org.opencv.utils.Converters;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;

 

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

public class BinaryDilation {
Mat BINARYMAT;
public BufferedImage dilate(BufferedImage b)throws IOException{
System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
BufferedImage CONVERTIMAGE=new BufferedImage(b.getWidth(),b.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
Graphics2D G2=CONVERTIMAGE.createGraphics();
G2.drawRenderedImage(b, null);
G2.dispose();
byte[] dataCONVERTIMAGE=((DataBufferByte)CONVERTIMAGE.getRaster().getDataBuffer()).getData();
Mat mat=new Mat(CONVERTIMAGE.getHeight(),CONVERTIMAGE.getWidth(),CvType.CV_8UC3);
mat.put(0, 0, dataCONVERTIMAGE);
Mat GRAYSCALEMAT=mat.clone();
Imgproc.cvtColor(mat, GRAYSCALEMAT, Imgproc.COLOR_BGR2GRAY);
BINARYMAT=GRAYSCALEMAT.clone();
Imgproc.threshold(GRAYSCALEMAT, BINARYMAT, 50, 255, Imgproc.THRESH_BINARY);
final List<MatOfPoint> contours=new ArrayList<>();
float[] radius=new float[contours.size()];
final Mat hierarchy=new Mat();
Imgproc.findContours(BINARYMAT, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
Mat GREENMAT=mat.clone();
        for(int i=0;i<contours.size();i++){
        Imgproc.drawContours(GREENMAT, contours, i, new Scalar(0,255,0),-1);
}
        Mat GRAYSCALEMAT2=new Mat();
        Imgproc.cvtColor(GREENMAT, GRAYSCALEMAT2, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(GRAYSCALEMAT2, BINARYMAT, 50, 255, Imgproc.THRESH_BINARY_INV);
        Mat DILATEELEMENTMAT=Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1,1));
        Imgproc.dilate(BINARYMAT,BINARYMAT, DILATEELEMENTMAT);
        
       
        
       
       // ArrayList holding all your coordinates is here...
        ArrayList<String> coordinatesBoundingBox=new ArrayList<>();
        //ArrayList holding all your coordinates is here...
        final List<MatOfPoint> contours2=new ArrayList<>();
        final Mat hierarchy2=new Mat();
        Imgproc.findContours(BINARYMAT, contours2, hierarchy2, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat BLUEMAT=mat.clone();
        for(int j=0;j<contours2.size();j++){
        Imgproc.drawContours(BLUEMAT,contours2,j,new Scalar(255,0,0),-1);
        }
        
        for (int k=0;k<contours2.size();k++) {
           
            Rect boundingRect = Imgproc.boundingRect(contours2.get(k));
            coordinatesBoundingBox.add(boundingRect.x+","+boundingRect.y+","+boundingRect.width+","+boundingRect.height);
            System.out.println(coordinatesBoundingBox.get(k));
            Core.rectangle(BINARYMAT, new Point(boundingRect.x,boundingRect.y), new Point(boundingRect.x+boundingRect.width,boundingRect.y+boundingRect.height), new Scalar (255, 0, 0, 255),3); 
        }
   
        MatOfByte BYTEMAT=new MatOfByte();
        Highgui.imencode(".PNG", BINARYMAT, BYTEMAT);
        byte[] BYTESARRAY=BYTEMAT.toArray();
        InputStream i=new ByteArrayInputStream(BYTESARRAY);
        BufferedImage OUTPUTIMAGE=ImageIO.read(i);
        
        
        return OUTPUTIMAGE;
        
        
        
}

}
