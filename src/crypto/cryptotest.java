package crypto;

import tijos.framework.util.Delay;
import tijos.framework.util.Formatter;
import tijos.security.crypto.Cipher;
import tijos.security.crypto.Key;
import tijos.security.crypto.KeyBuilder;
import tijos.security.crypto.Mac;
import tijos.security.crypto.MessageDigest;
import tijos.security.crypto.RandomData;

public class cryptotest {

	public static void main(String[] args) {

		System.out.println("start ...");
	//	Delay.msDelay(3000);
		
		sha256();
		md5();
		
		random_test();
		sha256();
		aes();
		aes2();
		sha256hmac();
		
	}
	
	
	private static void random_test()
	{
		try {
			byte []  buffer = new byte[64];
			
			RandomData data = RandomData.getInstance(RandomData.ALG_SECURE_RANDOM);
			
			data.generateData(buffer, 0, buffer.length);
			data.setSeed(buffer, 0, buffer.length);
			data.generateData(buffer, 0, buffer.length);
			
			
			
			
			System.out.println("rand " + Formatter.toHexString(buffer));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

	private static void aes() {
		try {
			Key chipKey = KeyBuilder.buildKey(KeyBuilder.ALG_TYPE_AES, KeyBuilder.LENGTH_AES_128);
			RandomData random = RandomData.getInstance(RandomData.ALG_SECURE_RANDOM);
			
			byte [] key = Formatter.hexStringToByte("2b7e151628aed2a6abf7158809cf4f3c");
			byte [] plain= Formatter.hexStringToByte("6bc1bee22e409f96e93d7e117393172a");
			
			
			System.out.println("plain " + Formatter.toHexString(plain));
			chipKey.setKey(key, (short) 0);
			
			System.out.println("setKey " + Formatter.toHexString(key) + " key handle " + chipKey.getKeyHandle() + " key type " + chipKey.getType());
			
			Cipher cipherEncrypt = Cipher.getInstance(Cipher.CIPHER_AES_CBC, Cipher.PADDING_PKCS7);

			System.out.println("enc init");
			byte[] IV_ZERO_AES = new byte[16];
			cipherEncrypt.init(chipKey, Cipher.MODE_ENCRYPT, IV_ZERO_AES, (short) 0, (short) IV_ZERO_AES.length);

			
			short keyHandleOffset = 0;
			
			int len = getPaddingLength(Cipher.PADDING_PKCS7, plain.length, chipKey.getBlockSize());
			System.out.println("plain len" + plain.length + " block " + chipKey.getBlockSize() + "exp len " + len);
			
			int clen = cipherEncrypt.doFinal(plain, keyHandleOffset, (short)plain.length, null, 0);
			
			System.out.println("cipher len " + clen);
			
			byte[] cipher = new byte[clen];

			clen = cipherEncrypt.doFinal(plain, keyHandleOffset, (short)plain.length, cipher, keyHandleOffset);
			
			System.out.println("cipher " + Formatter.toHexString(cipher, 0, clen, " "));

			Cipher cipherDecrypt = Cipher.getInstance(Cipher.CIPHER_AES_CBC, Cipher.PADDING_PKCS7);
			
			System.out.println("dec init");
			cipherDecrypt.init(chipKey, Cipher.MODE_DECRYPT, IV_ZERO_AES, (short) 0, (short) IV_ZERO_AES.length);

			byte [] data = new byte[64];
			keyHandleOffset = 0;
			int plen = cipherDecrypt.doFinal(cipher, keyHandleOffset, (short) clen, data, keyHandleOffset);
			
			System.out.println("data " + Formatter.toHexString(data, 0, plen, " "));

			chipKey.destroy();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static int getPaddingLength(int padding, int blockSize, int dataLen) {
		if(padding == Cipher.PADDING_NONE) {
			return dataLen;
		}
		
		return ((dataLen / blockSize) + 1) * blockSize;
	}
	
	private static void sha256()
	{
		try {
			MessageDigest sha256 = MessageDigest.getInstance(MessageDigest.ALG_SHA_256);
			
			
			byte [] input = "123456781234567812345678123456781234567812345678123456781234567812345678123456781234567812345678123456781234567812345678123456781234567812345678123456781234567812345678123456781234567812345678123456781234567812345678".getBytes();
			
			byte [] output = sha256.doFinal(input);
			
			System.out.println(" data " + Formatter.toHexString(output));
			
			output = SHA256.getHash(input);
			System.out.println(" data " + Formatter.toHexString(output));
			
			sha256.destroy();

		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void md5() {
		
		try {
			MessageDigest md5 = MessageDigest.getInstance(MessageDigest.ALG_MD5);

			byte [] input = "abcdefghijklmnopqrstuvwxyz".getBytes();
			
			byte [] output = md5.doFinal(input);
			
			System.out.println(" data " + Formatter.toHexString(output));

			md5.destroy();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	private static void sha256hmac()
	{
		
		try {
			String keyStr = "0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b";
			String dataStr = "4869205468657265";
			String macStr = "b0344c61d8db38535ca8afceaf0bf12b881dc200c9833da726e9376c2e32cff7";
			
			byte [] key = Formatter.hexStringToByte(keyStr);
			byte [] data = Formatter.hexStringToByte(dataStr);
		
			Mac mac = Mac.getInstance(Mac.ALG_HMAC_SHA_256);
			Key hmacKey = KeyBuilder.buildKey(KeyBuilder.ALG_TYPE_HMAC, key.length * 8);
			
			System.out.println(hmacKey);
			System.out.println(key);
			
			hmacKey.setKey(key);
			
			
			mac.init(hmacKey);
			mac.update(data, 0, data.length);
			
			byte [] sigBuff = mac.doFinal();
			
			System.out.println(Formatter.toHexString(sigBuff));
			
			mac.destroy();
			hmacKey.destroy();
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
	}

	private static void aes2() {
		try {
			Key chipKey = KeyBuilder.buildKey(KeyBuilder.ALG_TYPE_AES, KeyBuilder.LENGTH_AES_256);
			
			byte [] key = "12345678123456781234567812345678".getBytes();
//			byte [] plain= Formatter.hexStringToByte("6bc1bee22e409f96e93d7e117393172a");		
			byte [] plain= "12345678123456781".getBytes();	
			
			System.out.println("plain " + Formatter.toHexString(plain));
			chipKey.setKey(key);
			
			System.out.println("setKey " + Formatter.toHexString(key) + " key handle " + chipKey.getKeyHandle() + " key type " + chipKey.getType());
			
			Cipher cipherEncrypt = Cipher.getInstance(Cipher.CIPHER_AES_CBC, Cipher.PADDING_PKCS7);

			System.out.println("enc init");
			cipherEncrypt.init(chipKey, Cipher.MODE_ENCRYPT);
		
			byte [] cipher = cipherEncrypt.doFinal(plain, 0, plain.length);
	
			
			System.out.println("cipher len " + cipher.length);
						
			System.out.println("cipher " + Formatter.toHexString(cipher));

			Cipher cipherDecrypt = Cipher.getInstance(Cipher.CIPHER_AES_CBC, Cipher.PADDING_PKCS7);
			
			System.out.println("dec init");
			cipherDecrypt.init(chipKey, Cipher.MODE_DECRYPT);

			byte [] data = cipherDecrypt.doFinal(cipher, 0, cipher.length);
			
			System.out.println("data " + Formatter.toHexString(data));

			chipKey.destroy();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
