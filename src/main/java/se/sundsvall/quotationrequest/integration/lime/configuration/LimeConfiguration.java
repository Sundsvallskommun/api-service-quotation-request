package se.sundsvall.quotationrequest.integration.lime.configuration;

import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import generated.se.sundsvall.seab.lime.Helpdesk;
import generated.se.sundsvall.seab.lime.Helpdeskcategory;
import generated.se.sundsvall.seab.lime.Office;
import java.io.IOException;
import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.mediatype.hal.HalJacksonModule;
import se.sundsvall.dept44.configuration.feign.FeignConfiguration;
import se.sundsvall.dept44.configuration.feign.FeignMultiCustomizer;
import se.sundsvall.dept44.configuration.feign.decoder.JsonPathErrorDecoder;
import se.sundsvall.dept44.configuration.feign.decoder.JsonPathErrorDecoder.JsonPathSetup;
import se.sundsvall.quotationrequest.integration.lime.configuration.mixin.IdMixIn;
import tools.jackson.databind.json.JsonMapper;

import static java.nio.charset.StandardCharsets.UTF_8;
import static tools.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

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
		final var mapper = JsonMapper.builder()
			.addMixIn(Helpdeskcategory.class, IdMixIn.class)
			.addMixIn(Office.class, IdMixIn.class)
			.addMixIn(Helpdesk.class, IdMixIn.class)
			.disable(FAIL_ON_UNKNOWN_PROPERTIES)
			.addModule(new HalJacksonModule())
			.build();

		return new ResponseEntityDecoder(jackson3Decoder(mapper));
	}

	private static Decoder jackson3Decoder(final JsonMapper mapper) {
		return (response, type) -> {
			if (response.status() == 404 || response.status() == 204 || response.body() == null) {
				return null;
			}
			try (final var reader = response.body().asReader(UTF_8)) {
				return mapper.readValue(reader, mapper.constructType(type));
			} catch (final IOException e) {
				throw new IOException("Failed to decode response body", e);
			}
		};
	}
}
