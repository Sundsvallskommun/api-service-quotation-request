package se.sundsvall.quotationrequest.integration.lime.configuration;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import generated.se.sundsvall.seab.lime.Helpdesk;
import generated.se.sundsvall.seab.lime.Helpdeskcategory;
import generated.se.sundsvall.seab.lime.Office;
import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import se.sundsvall.dept44.configuration.feign.FeignConfiguration;
import se.sundsvall.dept44.configuration.feign.FeignMultiCustomizer;
import se.sundsvall.dept44.configuration.feign.decoder.JsonPathErrorDecoder;
import se.sundsvall.dept44.configuration.feign.decoder.JsonPathErrorDecoder.JsonPathSetup;
import se.sundsvall.quotationrequest.integration.lime.configuration.mixin.IdMixIn;

@Import(FeignConfiguration.class)
public class LimeConfiguration {

	public static final String CLIENT_ID = "lime";

	private static final String API_KEY_HEADER_NAME = "x-api-key";
	private static final String ERROR_DECODER_JSON_PATH = "$.error";

	@Bean
	FeignBuilderCustomizer feignBuilderCustomizer(final LimeProperties limeProperties) {
		return FeignMultiCustomizer.create()
			.withDecoder(feignHalDecoder())
			.withErrorDecoder(errorDecoder())
			.withRequestTimeoutsInSeconds(limeProperties.connectTimeout(), limeProperties.readTimeout())
			.withRequestInterceptor(requestInterceptor(limeProperties.apiKey()))
			.composeCustomizersToOne();
	}

	RequestInterceptor requestInterceptor(final String apiKey) {
		return requestTemplate -> requestTemplate.header(API_KEY_HEADER_NAME, apiKey);
	}

	ErrorDecoder errorDecoder() {
		return new JsonPathErrorDecoder(CLIENT_ID, new JsonPathSetup(ERROR_DECODER_JSON_PATH));
	}

	Decoder feignHalDecoder() {
		final var mapper = new ObjectMapper()
			.addMixIn(Helpdeskcategory.class, IdMixIn.class)
			.addMixIn(Office.class, IdMixIn.class)
			.addMixIn(Helpdesk.class, IdMixIn.class)
			.configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerModule(new JavaTimeModule())
			.registerModule(new Jackson2HalModule());

		return new ResponseEntityDecoder(new JacksonDecoder(mapper));
	}
}
