package springboot.usecase.one.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.exception.CustomExceptionHandler;

public class CommonUtility {
	private CommonUtility() {
		throw new CustomExceptionHandler("801","CommonUtility class exception");
	}

	public static String getSHA512(String pwd) throws NoSuchAlgorithmException {
		final MessageDigest md = MessageDigest.getInstance(CommonConstants.SHA512);
		byte[] digest = md.digest(pwd.getBytes(StandardCharsets.UTF_8));
		return getHex(digest);
	}

	public static String getHex(byte[] digest) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < digest.length; ++i) {
			sb.append(Integer.toHexString(digest[i] & 0xFF | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	public static String getDateTime() {
		DateFormat sdt = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdt.format(new Date());
	}
}
