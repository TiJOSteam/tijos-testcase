package netstack;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import tijos.framework.util.base64.Base64;
import tijos.kernel.io.security.crypto.CryptoException;
import tijos.security.crypto.Key;
import tijos.security.crypto.KeyBuilder;
import tijos.security.crypto.Mac;
import tijos.security.crypto.Signature;

public class OnenetToken {

    public static String assembleToken(String version, String resourceName, String signatureMethod, String accessKey) throws UnsupportedEncodingException, IOException, CryptoException
    {
    	String expirationTime = "4102416000"; //2100-01-01
        StringBuilder sb = new StringBuilder();
        String res = URLEncoder.encode(resourceName, "UTF-8");
        String sig = URLEncoder.encode(generatorSignature(version, resourceName, expirationTime
                , accessKey, signatureMethod), "UTF-8");
        sb.append("version=")
                .append(version)
                .append("&res=")
                .append(res)
                .append("&et=")
                .append(expirationTime)
                .append("&method=")
                .append(signatureMethod)
                .append("&sign=")
                .append(sig);
        return sb.toString();
    }

    public static String generatorSignature(String version, String resourceName, String expirationTime, String accessKey, String signatureMethod) throws IOException, CryptoException
	 {
        String encryptText = expirationTime + "\n" + signatureMethod + "\n" + resourceName + "\n" + version;
        String signature;
        byte[] bytes = HmacEncrypt(encryptText, accessKey, signatureMethod);
        signature =  Base64.encode(bytes); 
        return signature;
    }

    public static byte[] HmacEncrypt(String data, String key, String signatureMethod) throws IOException, CryptoException
    {
    	byte [] mackey = Base64.decode(key);
    	
		Key hmacKey = KeyBuilder.buildKey(KeyBuilder.ALG_TYPE_HMAC, mackey.length * 8);
		Mac sig = Mac.getInstance(Mac.ALG_HMAC_SHA_1);

		hmacKey.setKey(mackey, 0);
		sig.init(hmacKey);

		byte [] input = data.getBytes();
		byte[] rawHmac = sig.doFinal(input);

		sig.destroy();
		hmacKey.destroy();
		
		return rawHmac;
    }
    
    public static String generateDevToken(String productId, String devName, String accessKey) throws UnsupportedEncodingException, IOException, CryptoException
    {
        String version = "2018-10-31";
        String resourceName = "products/" + productId;
        if(devName != null)
        {
        	resourceName += "/devices/" + devName;
        }
        
        String signatureMethod = "sha1";
        String token = assembleToken(version, resourceName, signatureMethod, accessKey);

        return token;
    }

    public enum SignatureMethod {
        SHA1, MD5, SHA256;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, IOException, CryptoException{
        String version = "2018-10-31";
        String resourceName = "products/123123";
        String signatureMethod = SignatureMethod.SHA1.name().toLowerCase();
        String accessKey = "KuF3NT/jUBJ62LNBB/A8XZA9CqS3Cu79B/ABmfA1UCw=";
        String token = assembleToken(version, resourceName, signatureMethod, accessKey);
        System.out.println("Authorization:" + token);
        
        System.out.println(generateDevToken("123123", null, accessKey));
        
        
        System.out.println(generateDevToken("i7uXSShSHQ", "DEV867435057486946", "//vl4/m7HRehDSt/QfW7x63RZZtvh+PpayOZBZUxf3k="));
        
        
        
    }
}