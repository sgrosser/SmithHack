import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.FileDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

public class Interface extends Component{
	JButton browseButton;
	JFileChooser fileChooser;
	JTextArea log;
	int result;
	int returnVal;
	int repetition;
	File file;
	mainController mC=new mainController();

	JLabel encryptImageLabel;
	 JLabel decryptImageLabel;
	 JPanel panel1;
	 static JPanel panel2;
	 String outputPath;
	 static BufferedImage inputImage;
	 BufferedImage inputImage2;

	 
	public void show(){
		 log = new JTextArea(5,20);
	        log.setMargin(new Insets(5,5,5,5));
	        log.setEditable(false);
	        JScrollPane logScrollPane = new JScrollPane(log);
		JFrame f1=new JFrame("Home");
		f1.setSize(1000,850);
		f1.getContentPane().setBackground(new Color(153,255,204));
	
		
		JTabbedPane options=new JTabbedPane();
		options.setSize(200, 100);
		options.setTabPlacement(JTabbedPane.LEFT);
		 JPanel encryptPanel = new JPanel();
		
		 JPanel keyPanel=new JPanel();

		 Font font = new Font("Arial", Font.PLAIN,24);
		 JLabel encryptionLabel=new JLabel("Encryption Area");
		 JLabel addImage=new JLabel("Choose files");
		 browseButton=new JButton("Browse");
		 browseButton.setSize(100,35);
		 browseButton.setLocation(660,180);
		 
		 //encryptionLabel.setLocation(100,200);
		 JLabel keyLabel=new JLabel("Set Key");
		 keyLabel.setSize(300,35);
		 keyLabel.setLocation(450, 10);
		 encryptionLabel.setSize(300,35);
		 addImage.setSize(300,35);
		 
		 encryptionLabel.setLocation(400,25);

		 addImage.setLocation(90,180);
		 addImage.setFont(font);
		
        // System.out.println(keyPanel.getLayout());
		 keyPanel.setLayout(null);
		 encryptPanel.setLayout(null);
		 encryptionLabel.setFont(font);
		 keyLabel.setFont(font);
		 keyLabel.setVisible(true);
		 addImage.setVisible(true);
		 browseButton.setVisible(true);
		 encryptionLabel.setVisible(true);
	     encryptPanel.add(encryptionLabel);
	     encryptPanel.add(browseButton);
	     keyPanel.add(keyLabel);
	     encryptPanel.add(addImage);
	     
	     keyPanel.setSize(900, 750);
	     options.addTab("Key",keyPanel);
	     options.addTab("Encrypt", encryptPanel);
	     
	     //keyPanel.setSize(900,750);
	     encryptPanel.setSize(900,750);
	     keyPanel.setBackground(new Color(255,255,255,110));
	     encryptPanel.setBackground(new Color(255,255,255,110));
	     JPanel panel1=new JPanel();
	     panel1.setSize(350,500);
	     panel1.setLocation(50,250);
	     panel1.setBackground(new Color(255,255,255,110));
	     
	     
	     JPanel panel2=new JPanel();
	     panel2.setSize(350,500);
	     panel2.setLocation(550,250);
	     panel2.setBackground(new Color(255,255,255,110));
	     
	     
	  
	     
	     
	     panel1.setVisible(true);
	     panel2.setVisible(true);
	     
	     JButton encryptButton=new JButton("<<Encrypt");
	     JButton decryptButton=new JButton(">>Decrypt");
	     
	     encryptButton.setLocation(430,350);
	     decryptButton.setLocation(430,410);
	     
	     encryptButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
	    	if(e.getSource()==encryptButton){
	    		
	    		
	    	 
			try {
				outputPath = mC.writeOutputFile(mC.executeBinaryDilation(mC.invertImage(ImageIO.read(file))));
				 inputImage2=ImageIO.read(file);
				
				mC.binaryDilation1.doEncryption();
				
				
				
				
	    	    encryptImageLabel=new JLabel(new ImageIcon(inputImage));
	    	  
      	    	 
     	    	  //encryptImageLabel.setSize(350,500);
     	    	  //encryptImageLabel.setLocation(550,250);
     	    	   panel1.add(encryptImageLabel);
     	    	  encryptImageLabel.setVisible(true);
     	    	  System.out.println("yo");
	    		}catch(IOException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | ShortBufferException | BadPaddingException | ClassNotFoundException io){
	    			
	    		
	    	
	    		}}}});
	     encryptButton.setSize(85,50);
	     decryptButton.setSize(85,50);
	     
	     decryptButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
	    	 if(e.getSource()==decryptButton){
	    		
	    		
	    			
	    			 decryptImageLabel=new JLabel(new ImageIcon(inputImage2));
	      	    	 
	     	    	  //encryptImageLabel.setSize(350,500);
	     	    	  //encryptImageLabel.setLocation(550,250);
	     	    	   panel2.add(decryptImageLabel);
	     	    	  decryptImageLabel.setVisible(true);
					//mC.binaryDilation1.doDecrypt();
			
	    		 
	    	 }
	     }});
	     
	     encryptPanel.add(encryptButton);
	     encryptPanel.add(decryptButton);
	     
	     
	     
	     encryptPanel.add(panel1);
	     encryptPanel.add(panel2);
	     
	     browseButton.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e) {
			 
	        
	         if (e.getSource() == browseButton) {
	        	 fileChooser=new JFileChooser();

		        int returnVal = fileChooser.showOpenDialog(Interface.this);
		             if (returnVal == JFileChooser.APPROVE_OPTION) {
		            	 repetition++;
		            	 
		                 file = fileChooser.getSelectedFile();
		                 //This is where a real application would save the file.
		                
		       	    	 try {
							inputImage=ImageIO.read(file);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		       	    	 
		                	 encryptImageLabel=new JLabel(new ImageIcon(inputImage));
		       	    	 
		       	    	  //encryptImageLabel.setSize(350,500);
		       	    	  //encryptImageLabel.setLocation(550,250);
		       	    	   panel1.add(encryptImageLabel);
		       	    	  encryptImageLabel.setVisible(true);
		       	    	 /*
		       	     }else if(repetition==2){
		       	    	 decryptImageLabel=new JLabel(new ImageIcon(file.getAbsolutePath()));
		       	    	 System.out.println(file.getAbsolutePath());
		       	    	 //decryptImageLabel.setSize(350,500);
		       	    	 //decryptImageLabel.setLocation(550,250);
		       	    	panel2.add(decryptImageLabel);
		       	         decryptImageLabel.setVisible(true);
		       	    	
		       	     }
		       	     */
		       	     
		                 log.append("Saving: " + file.getName() + "." );
		             } else {
		                 log.append("Save command cancelled by user.");
		             }
		             log.setCaretPosition(log.getDocument().getLength());
		         }
		         
		     
		     }
	            
	             
	         
	     
	     }
	         
	 	);
	     
	     
	     
	     
	     
	     
	     
		 f1.add(options);
		 f1.setVisible(true);
	}
	
	
	public static void main(String args[]){
		Interface i=new Interface();
		i.show();


		        
		      
	}
}