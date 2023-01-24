package se.sundsvall.quotationrequest.integration.lime.configuration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.quotationrequest.integration.lime.configuration.LimeConfiguration.CLIENT_ID;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import se.sundsvall.dept44.configuration.feign.FeignMultiCustomizer;
import se.sundsvall.dept44.configuration.feign.decoder.JsonPathErrorDecoder;

@ExtendWith(MockitoExtension.class)
class LimeConfigurationTest {

	@Mock
	private LimeProperties propertiesMock;

	@Spy
	private FeignMultiCustomizer feignMultiCustomizerSpy;

	@Captor
	private ArgumentCaptor<ErrorDecoder> errorDecoderCaptor;

	@Captor
	private ArgumentCaptor<RequestInterceptor> requestInterceptorCaptor;

	@InjectMocks
	private LimeConfiguration configuration;

	@Test
	void feignBuilderCustomizer() {

		final var connectTimeout = 123;
		final var readTimeout = 321;

		when(propertiesMock.connectTimeout()).thenReturn(connectTimeout);
		when(propertiesMock.readTimeout()).thenReturn(readTimeout);

		// Mock static FeignMultiCustomizer to enable spy and to verify that static method is being called
		try (MockedStatic<FeignMultiCustomizer> feignMultiCustomizerMock = Mockito.mockStatic(FeignMultiCustomizer.class)) {
			feignMultiCustomizerMock.when(() -> FeignMultiCustomizer.create()).thenReturn(feignMultiCustomizerSpy);

			configuration.feignBuilderCustomizer(propertiesMock);

			feignMultiCustomizerMock.verify(() -> FeignMultiCustomizer.create());
		}

		// Verifications
		verify(propertiesMock).connectTimeout();
		verify(propertiesMock).readTimeout();
		verify(feignMultiCustomizerSpy).withErrorDecoder(errorDecoderCaptor.capture());
		verify(feignMultiCustomizerSpy).withRequestTimeoutsInSeconds(connectTimeout, readTimeout);
		verify(feignMultiCustomizerSpy).withRequestInterceptor(requestInterceptorCaptor.capture());
		verify(feignMultiCustomizerSpy).composeCustomizersToOne();

		// Assert ErrorDecoder
		assertThat(errorDecoderCaptor.getValue())
			.isInstanceOf(JsonPathErrorDecoder.class)
			.hasFieldOrPropertyWithValue("integrationName", CLIENT_ID);

		// Assert RequestInterceptor
		assertThat(requestInterceptorCaptor.getValue())
			.isInstanceOf(RequestInterceptor.class);
	}

	@Test
	void requestInterceptor() {

		final var apiKey = "apiKey";

		final var template = new RequestTemplate();
		final var interceptor = configuration.requestInterceptor(apiKey);

		interceptor.apply(template);

		assertThat(template.headers()).hasFieldOrPropertyWithValue("x-api-key", List.of(apiKey));
	}
}
