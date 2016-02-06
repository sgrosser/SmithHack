package HackAtSmith;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
	


	

	private Cipher deCipher;
	private Cipher enCipher;
	private SecretKeySpec key;
	private IvParameterSpec ivSpec;


	public Encryption(byte[] keyBytes,   byte[] ivBytes) {
	    // wrap key data in Key/IV specs to pass to cipher


	     ivSpec = new IvParameterSpec(ivBytes);
	    // create the cipher with the algorithm you choose
	    // see javadoc for Cipher class for more info, e.g.
	    try {
	         DESKeySpec dkey = new  DESKeySpec(keyBytes);
	          key = new SecretKeySpec(dkey.getKey(), "DES");
	         deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	         enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	    } catch (NoSuchAlgorithmException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (NoSuchPaddingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (InvalidKeyException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	public byte[] encrypt(byte[] obj) throws InvalidKeyException, InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, ShortBufferException, BadPaddingException {
	    
	    enCipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

	    return enCipher.doFinal(obj);


	}
	public byte[] decrypt( byte[]  encrypted) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException {
	    deCipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

	    return deCipher.doFinal(encrypted);

	}



}	