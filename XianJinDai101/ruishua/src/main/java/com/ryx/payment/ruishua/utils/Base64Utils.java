package com.ryx.payment.ruishua.utils;

import java.io.UnsupportedEncodingException;

public class Base64Utils {
	
	public static void main(String[] args) {
		System.out.println("=========加密过程=========");
		String str="6225684321000099630";
		System.out.println("加密原值:"+str);
		String result=strEncodeHex(str);
		System.out.println("加密结果："+result);
		try {
			System.out.println("=========解密过程=========");
			System.out.println("解密密文:"+result);
			System.out.println("解密结果:"+hexDecodeStr(result));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  public static String strEncodeHex(String data) { 
	    	return toHex(encode(data.getBytes()));
	    }
	    public static String hexDecodeStr(String data) throws UnsupportedEncodingException { 
	    	return decodeStr(fromHex(data));
	    }
    private static char[] base64EncodeChars = new char[] { 
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
        'w', 'x', 'y', 'z', '0', '1', '2', '3', 
        '4', '5', '6', '7', '8', '9', '+', '/' }; 
  
    private static byte[] base64DecodeChars = new byte[] { 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 
    52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, 
    -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 
    15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, 
    -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 
    41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 }; 
  
  
    
    private static String encode(byte[] data) { 
        StringBuffer sb = new StringBuffer(); 
        int len = data.length; 
        int i = 0; 
        int b1, b2, b3; 
        while (i < len) { 
            b1 = data[i++] & 0xff; 
            if (i == len) 
            { 
                sb.append(base64EncodeChars[b1 >>> 2]); 
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]); 
                sb.append("=="); 
                break; 
            } 
            b2 = data[i++] & 0xff; 
            if (i == len) 
            { 
                sb.append(base64EncodeChars[b1 >>> 2]); 
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]); 
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]); 
                sb.append("="); 
                break; 
            } 
            b3 = data[i++] & 0xff; 
            sb.append(base64EncodeChars[b1 >>> 2]); 
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]); 
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]); 
            sb.append(base64EncodeChars[b3 & 0x3f]); 
        } 
        return sb.toString(); 
    } 
    private static String decodeStr(String str) throws UnsupportedEncodingException{
		  return new String (decodeByte(str));
	  }
    private static byte[] decodeByte(String str) throws UnsupportedEncodingException { 
        StringBuffer sb = new StringBuffer(); 
        byte[] data = str.getBytes("UTF-8"); 
        int len = data.length; 
        int i = 0; 
        int b1, b2, b3, b4; 
        while (i < len) { 
            /* b1 */ 
            do { 
                b1 = base64DecodeChars[data[i++]]; 
            } while (i < len && b1 == -1); 
            if (b1 == -1) break; 
            /* b2 */ 
            do { 
                b2 = base64DecodeChars[data[i++]]; 
            } while (i < len && b2 == -1); 
            if (b2 == -1) break; 
            sb.append((char)((b1 << 2) | ((b2 & 0x30) >>> 4))); 
            /* b3 */ 
            do { 
                b3 = data[i++]; 
                if (b3 == 61) return sb.toString().getBytes("UTF-8"); 
                b3 = base64DecodeChars[b3]; 
            } while (i < len && b3 == -1); 
            if (b3 == -1) break; 
            sb.append((char)(((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2))); 
            /* b4 */ 
            do { 
                b4 = data[i++]; 
                if (b4 == 61) return sb.toString().getBytes("UTF-8"); 
                b4 = base64DecodeChars[b4]; 
            } while (i < len && b4 == -1); 
            if (b4 == -1) break; 
            sb.append((char)(((b3 & 0x03) << 6) | b4)); 
        } 
        return sb.toString().getBytes("UTF-8"); 
    } 
    
    
    
    
    
    private static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

    private static String fromHex(String hex) {
		return new String(toByte(hex));
	}

    private static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
		return result;
	}

    private static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private final static String HEX = "0123456789ABCDEF";

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}
}