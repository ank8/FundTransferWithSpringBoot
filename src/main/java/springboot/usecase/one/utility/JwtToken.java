package springboot.usecase.one.utility;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.exception.CustomExceptionHandler;

public class JwtToken {
	private JwtToken() {
		throw new CustomExceptionHandler("802","JwtToken class exception");
	}

	
	private static final Key SIGNING_KEY = new SecretKeySpec(
			DatatypeConverter.parseBase64Binary(CommonConstants.JWT_TOKEN_KEY), SignatureAlgorithm.HS512.getJcaName());

	
	public static String createToken(final long customerId, final int expire) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, expire);
		Date date = cal.getTime();
		return Jwts.builder().claim("customerId", customerId).setExpiration(date)
				.signWith(SignatureAlgorithm.HS512, SIGNING_KEY).compact();
	}

	public static long validateToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
			return Integer.parseInt(claims.get("customerId").toString());
		} catch (Exception e) {
			throw new CustomExceptionHandler("999","Invalid Token");
		}
	}
}
