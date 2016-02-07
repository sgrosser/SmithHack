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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
public class BinaryDilation {
Mat BINARYMAT;
Graphics2D g2d;
BufferedImage decryptimg;
Encryption enc;
ArrayList<String> coordinatesBoundingBox;
ArrayList<byte[]> encryptedSubImages;
public BufferedImage dilate(BufferedImage b)throws IOException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, ShortBufferException, BadPaddingException, ClassNotFoundException{
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
        Mat DILATEELEMENTMAT=Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3)); //CHANGED FROM 1 1 to 3 1
        Imgproc.dilate(BINARYMAT,BINARYMAT, DILATEELEMENTMAT);
        
       
        
       
       // ArrayList holding all your coordinates is here...
        coordinatesBoundingBox=new ArrayList<String>();
        //ArrayList holding all your coordinates is here...


     
        System.out.println("WAAZUP?!?");
        
        
        
        
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
        
    

    	
    	//decrypt
    	
        
        
        
        
        
        
        MatOfByte BYTEMAT=new MatOfByte();
        Highgui.imencode(".PNG", BINARYMAT, BYTEMAT);
        byte[] BYTESARRAY=BYTEMAT.toArray();
        InputStream i=new ByteArrayInputStream(BYTESARRAY);
        BufferedImage OUTPUTIMAGE=ImageIO.read(i);
        
        
        return OUTPUTIMAGE;
        
        
        
}


public void doEncryption() throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, ShortBufferException, BadPaddingException, IOException{
    byte[] key = new byte[56];
    
    new Random().nextBytes(key);
    if(key.length!=56) System.out.println(key.length);
	byte[] iv = new byte[8];
	new Random().nextBytes(iv);
	
    enc = new Encryption(key, iv);
    
	encryptedSubImages = new ArrayList<byte[]>();
	
	//encrypt
	byte[] pixels;
	byte[] encPix;
	int cc = 0;
	BufferedImage subImg;
	for(String s: coordinatesBoundingBox){
		cc++;
		if(cc>490) break;

    	String[] coords = s.split(",");
 
    			subImg = Interface.inputImage.getSubimage(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]), Integer.parseInt(coords[3])); 
    	//subImg = Interface.inputImage.getSubimage(50, 100, 200, 300); 

		pixels = ((DataBufferByte) subImg.getRaster().getDataBuffer()).getData();
        
        //	System.out.print(cc+":  "+pixels[0]+" "+pixels[1]+" "+pixels[2]+" "+pixels[3]);
    	encPix = enc.encrypt(pixels);
    	
    	
    	//Draw onto image
    	//
    	int c = 0;
    	if(subImg.getAlphaRaster()!=null){
    	for(int y = 0; y < subImg.getHeight(); y++) {
		    for(int x = 0; x < subImg.getWidth(); x++) {
		        Interface.inputImage.setRGB(Integer.parseInt(coords[0])+x,Integer.parseInt(coords[1])+ y, (encPix[c]<<24)+(encPix[c+3]<<16)+(encPix[c+2]<<8)+encPix[c+1]);
		    	//Interface.inputImage.setRGB(Integer.parseInt(coords[0])+x,Integer.parseInt(coords[1])+ y, ((encPix[c]%255)<<24)+((encPix[c+3]%255)<<16)+((encPix[c+2]%255)<<8)+encPix[c+1]%255);
		    	//Interface.inputImage.setRGB(20+x,20+ y, new Color(pixels[c+3], pixels[c+2], pixels[c+1],pixels[c]).getRGB());
		    	//Interface.inputImage.setRGB(+x,100+ y, (((int)pixels[c]&0xff)<<24)+(((int)pixels[c+1]&0xff)<<16)+(((int)pixels[c+2]&0xff)<<8)+(pixels[c+3])&0xff);

		    	c+=4;
		    }
		}
    	}
    	
    	encryptedSubImages.add(encPix);
	}
	
	
    }

public BufferedImage doDecrypt() throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, IOException{
	int counter = 0;
	for(String s: coordinatesBoundingBox){
		if(counter>250)break;
		String[] coords = s.split(",");
		byte[] currSubIm = encryptedSubImages.get(counter);
		currSubIm = enc.decrypt(currSubIm);
		 decryptimg=new BufferedImage(Integer.parseInt(coords[2]), Integer.parseInt(coords[3]), BufferedImage.TYPE_3BYTE_BGR);
	decryptimg.setData(Raster.createRaster(decryptimg.getSampleModel(), new DataBufferByte(currSubIm, currSubIm.length), new java.awt.Point() ) );
       
      Interface.panel2.add(new JLabel ( new ImageIcon(decryptimg)),Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
       // g2d.drawImage(decryptimg,Integer.parseInt(coords[0]), Integer.parseInt(coords[1]),null);
		//draw onto image
		//
		
		
		counter++;
		
	}
	return decryptimg;
}



}
