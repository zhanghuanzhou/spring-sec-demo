package my.demo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
* MD5 签名工具包
* @author qidb
* @date 2015-5-22
* @version 1.0
*/
public class MD5Util {
	 // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    
    //加密后的字符串
    private static String responseMsg;

    public  static String getMd5(String s) { 
    	try {
    		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    		responseMsg = byteArrayToHexString(messageDigest.digest(s.getBytes("utf-8")));
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
//    	  char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
//    	    'a', 'b', 'c', 'd', 'e', 'f' };  
//    	  try {  
//    	   byte[] strTemp = s.getBytes();  
//    	   MessageDigest mdTemp = MessageDigest.getInstance("MD5");  
//    	   mdTemp.update(strTemp);  
//    	   byte[] md = mdTemp.digest();  
//    	   int j = md.length;  
//    	   char str[] = new char[j * 2];  
//    	   int k = 0;  
//    	   for (int i = 0; i < j; i++) {  
//    	    byte byte0 = md[i];  
//    	    str[k++] = hexDigits[byte0 >>> 4 & 0xf]md;  
//    	    str[k++] = hexDigits[byte0 & 0xf];  
//    	   }  
//    	   responseMsg = new String(str);  
//    	  } catch (Exception e) {  
//    	   return null;  
//    	  }
		return responseMsg;  
    }  
    
    /**
	 * 把字符串数组转成16进制
	 * 
	 * @param b
	 * @return
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}
	
	/**
	 * 把字节专程16进制字符串
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return strDigits[d1] + strDigits[d2];
	}
	
	/**
	 * 生成登录标识令牌
	 * @return
	 */
    public static String getLoginToken(){
    	String Token="";
//    	try {
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    	return Token;
    }
    
    
	/**
	 * MD5加码。32位
	 * @param inStr
	 * @return
	 */
     public static String setMD5(String inStr) {
      MessageDigest md5 = null;
      try {
       md5 = MessageDigest.getInstance("MD5");
      } catch (Exception e) {
       System.out.println(e.toString());
       e.printStackTrace();
       return "";
      }
      char[] charArray = inStr.toCharArray();
      byte[] byteArray = new byte[charArray.length];

      for (int i = 0; i < charArray.length; i++)
       byteArray[i] = (byte) charArray[i];

      byte[] md5Bytes = md5.digest(byteArray);

      StringBuffer hexValue = new StringBuffer();

      for (int i = 0; i < md5Bytes.length; i++) {
       int val = ((int) md5Bytes[i]) & 0xff;
       if (val < 16)
        hexValue.append("0");
       hexValue.append(Integer.toHexString(val));
      }

      return hexValue.toString();
     }

     /**
      * 可逆的加密算法
      * @param inStr
      * @return
      */
     public static String KL(String inStr) {
      // String s = new String(inStr);
      char[] a = inStr.toCharArray();
      for (int i = 0; i < a.length; i++) {
       a[i] = (char) (a[i] ^ 't');
      }
      String s = new String(a);
      return s;
     }

     /**
      * 加密后解密
      * @param inStr
      * @return
      */
     public static String JM(String inStr) {
      char[] a = inStr.toCharArray();
      for (int i = 0; i < a.length; i++) {
       a[i] = (char) (a[i] ^ 't');
      }
      String k = new String(a);
      return k;
     }
     
     /**
      * 测试主函数
      * @param args
      */
     public static void main(String args[]) {
      String s = new String("791797305@qq.com11111114102574977461.0EasyInker");
      System.out.println("原始：" + s);
      System.out.println("MD5后：" + getMd5(s));
      System.out.println("MD5后再加密：" + KL(getMd5(s)));
      System.out.println("解密为MD5后的：" + JM(KL(getMd5(s))));
     }
	
     /**
      * 加密算法
      * @param strObj
      * @return
      */
	 public static String GetMD5Code(String strObj) {
	        String resultString = null;
	        try {
	            resultString = new String(strObj);
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            // md.digest() 该函数返回值为存放哈希值结果的byte数组
	            resultString = byteToString(md.digest(strObj.getBytes()));
	        } catch (NoSuchAlgorithmException ex) {
	            ex.printStackTrace();
	        }
	        return resultString;
	 }
	 
	 /**
	  * 转换字节数组为16进制字串
	  * @param bByte
	  * @return
	  */
	 private static String byteToString(byte[] bByte) {
	        StringBuffer sBuffer = new StringBuffer();
	        for (int i = 0; i < bByte.length; i++) {
	            sBuffer.append(byteToArrayString(bByte[i]));
	        }
	        return sBuffer.toString();
	 }
	    
	    /**
	     * 返回形式为数字跟字符串
	     * @param bByte
	     * @return
	     */
	 private static String byteToArrayString(byte bByte) {
	        int iRet = bByte;
	        // System.out.println("iRet="+iRet);
	        if (iRet < 0) {
	            iRet += 256;
	        }
	        int iD1 = iRet / 16;
	        int iD2 = iRet % 16;
	        return strDigits[iD1] + strDigits[iD2];
	 }

	/**
	  * 返回形式只为数字
	  * @param bByte
	  * @return
	*/
	@SuppressWarnings("unused")
	private static String byteToNum(byte bByte) {
	        int iRet = bByte;
	        System.out.println("iRet1=" + iRet);
	        if (iRet < 0) {
	            iRet += 256;
	        }
	        return String.valueOf(iRet);
	}
	 
	public String getResponseMsg() {
		return responseMsg;
	}

	@SuppressWarnings("static-access")
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
}
