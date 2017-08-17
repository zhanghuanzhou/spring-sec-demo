package my.demo.utils;

import java.security.MessageDigest;
import java.util.Random;

/**
 * 密码工具
 * @author Ash
 *
 */
public class PasswordUtil {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private Object salt;

	private String algorithm;

	public PasswordUtil() {
		this("MD5");
	}

	public static void main(String[] args) {
		PasswordUtil passwordUtil = new PasswordUtil();
		System.out.println(passwordUtil.getSalt());
		System.out.println(passwordUtil.encode("1"));
	}
	
	public PasswordUtil(String algorithm) {
		

		Random random = new Random();
		int times = random.nextInt(5) + 1;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < times; i++) {
			int x = 33 + random.nextInt(93);
			char word = (char)x;
			buffer.append(word);
		}
		this.salt = buffer.toString();
		this.algorithm = algorithm;
	}

	/**
	 * 带salt加密
	 * 
	 * @param rawPass
	 * @return
	 */
	public String encode(String rawPass) {
		String result = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

			// 加密后的字符串
			result = byteArrayToHexString(messageDigest
					.digest(mergePasswordAndSalt(rawPass).getBytes("utf-8")));
		} catch (Exception e) {

		}
		return result;
	}

	/**
	 * 判断密码是否正确
	 * 
	 * @param encPass
	 * @param rawPass
	 * @param salt
	 * @return
	 */
	public boolean isPasswordValid(String encPass, String rawPass, String salt) {
		this.salt = salt;
		String pass1 = "" + encPass;
		String pass2 = encode(rawPass);
		return pass1.equals(pass2);
	}

	/**
	 * 合并密码和salt
	 * 
	 * @param password
	 * @return
	 */
	private String mergePasswordAndSalt(String password) {
		if (password == null) {
			password = "";
		}
		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
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
	 * 
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
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 获取salt
	 * @return
	 */
	public Object getSalt() {
		return salt;
	}

	/**
	 * MD5加密密码
	 * @param password
	 * @return
	 */
	public String encodeByMD5(String password) {
		this.algorithm = "MD5";
		return encode(password);
	}
}
