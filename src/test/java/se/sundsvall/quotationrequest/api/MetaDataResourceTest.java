package se.sundsvall.quotationrequest.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.sundsvall.quotationrequest.Application;
import se.sundsvall.quotationrequest.api.model.MetaDataResponse;
import se.sundsvall.quotationrequest.service.MetaDataService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class MetaDataResourceTest {

	private static final String PATH = "/{municipalityId}/meta-data";
	private static final String MUNICIPALITY_ID = "2281";

	@Autowired
	private WebTestClient webTestClient;

	@MockitoBean
	private MetaDataService metaDataServiceMock;

	@Test
	void getMetaData() {

		// Arrange
		when(metaDataServiceMock.getMetaData()).thenReturn(MetaDataResponse.create());

		// Act
		final var result = webTestClient.get()
			.uri(builder -> builder.path(PATH).build(Map.of("municipalityId", MUNICIPALITY_ID)))
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(MetaDataResponse.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(result).isNotNull();

		verify(metaDataServiceMock).getMetaData();
	}
}
