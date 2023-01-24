package se.sundsvall.quotationrequest.integration.lime.configuration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import se.sundsvall.quotationrequest.Application;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("junit")
class LimePropertiesTest {

	@Autowired(required = false)
	private LimeProperties properties;

	@Test
	void testProperties() {
		assertThat(properties).isNotNull();
		assertThat(properties.connectTimeout()).isEqualTo(10);
		assertThat(properties.readTimeout()).isEqualTo(20);
		assertThat(properties.apiKey()).isEqualTo("the-test-key");
	}
}
