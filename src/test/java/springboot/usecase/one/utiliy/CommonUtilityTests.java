package springboot.usecase.one.utiliy;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import springboot.usecase.one.utility.CommonUtility;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CommonUtility.class)
class CommonUtilityTests {

	@Test
	void getSHA512() throws NoSuchAlgorithmException {
		String sha = CommonUtility.getSHA512("12345");
		assertNotNull(sha);
	}

	@Test
	void getDateTime() {
		String date = CommonUtility.getDateTime();
		assertNotNull(date);
	}

}
