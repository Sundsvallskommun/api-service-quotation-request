package se.sundsvall.quotationrequest.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import se.sundsvall.quotationrequest.Application;
import se.sundsvall.quotationrequest.api.model.MetaDataResponse;
import se.sundsvall.quotationrequest.service.MetaDataService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class MetaDataResourceTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private MetaDataService metaDataServiceMock;

	@Test
	void getMetaData() {

		when(metaDataServiceMock.getMetaData()).thenReturn(MetaDataResponse.create());

		final var result = webTestClient.get().uri("/meta-data")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(MetaDataResponse.class)
			.returnResult()
			.getResponseBody();

		assertThat(result).isNotNull();

		verify(metaDataServiceMock).getMetaData();
	}
}
