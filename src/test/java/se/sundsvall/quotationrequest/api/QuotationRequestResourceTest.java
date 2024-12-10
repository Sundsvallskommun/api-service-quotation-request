package se.sundsvall.quotationrequest.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.sundsvall.quotationrequest.Application;
import se.sundsvall.quotationrequest.api.model.ContactDetails;
import se.sundsvall.quotationrequest.api.model.QuotationRequest;
import se.sundsvall.quotationrequest.service.HelpdeskService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class QuotationRequestResourceTest {

	private static final String PATH = "/{municipalityId}/quotation-request";
	private static final String MUNICIPALITY_ID = "2281";

	@Autowired
	private WebTestClient webTestClient;

	@MockitoBean
	private HelpdeskService helpdeskServiceMock;

	@Test
	void postQuotationRequest() {

		// Arrange
		final var id = 123;
		final var contactDetails = ContactDetails.create()
			.withFirstName("firstName")
			.withSurname("surname")
			.withPhoneNumber("phoneNumber")
			.withEmailAddress("emailAddress@host.com");
		final var quotationRequest = QuotationRequest.create()
			.withTitle("title")
			.withContactDetails(contactDetails)
			.withNote("note")
			.withHelpdeskId("helpdeskId")
			.withOfficeId("officeId");

		when(helpdeskServiceMock.create(any())).thenReturn(id);

		// Act
		webTestClient.post()
			.uri(builder -> builder.path(PATH).build(Map.of("municipalityId", MUNICIPALITY_ID)))
			.contentType(APPLICATION_JSON)
			.bodyValue(quotationRequest)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().contentType(ALL_VALUE)
			.expectHeader().location("/" + MUNICIPALITY_ID + "/quotation-request/" + id)
			.expectBody().isEmpty();

		// Assert
		verify(helpdeskServiceMock).create(quotationRequest);
	}
}
