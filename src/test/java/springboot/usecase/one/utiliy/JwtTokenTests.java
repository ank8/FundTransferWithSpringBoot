package springboot.usecase.one.utiliy;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import springboot.usecase.one.utility.CommonUtility;
import springboot.usecase.one.utility.JwtToken;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = JwtToken.class)
class JwtTokenTests {

	@Test
	void createToken() throws NoSuchAlgorithmException {
		String token = JwtToken.createToken(1, 15);
		assertNotNull(token);
	}

	@Test
	void getDateTime() {
		String token = JwtToken.createToken(1, 15);
		long id = JwtToken.validateToken(token);
		assertNotNull(id);
	}

}
