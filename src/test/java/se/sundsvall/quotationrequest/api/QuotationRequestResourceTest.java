package se.sundsvall.quotationrequest.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import se.sundsvall.quotationrequest.Application;
import se.sundsvall.quotationrequest.api.model.ContactDetails;
import se.sundsvall.quotationrequest.api.model.QuotationRequest;
import se.sundsvall.quotationrequest.service.HelpdeskService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class QuotationRequestResourceTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private HelpdeskService helpdeskServiceMock;

	@LocalServerPort
	private int port;

	@Test
	void postQuotationRequest() {

		when(helpdeskServiceMock.create(any())).thenReturn(1);

		// Parameter values.
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

		webTestClient.post().uri("/quotation-request")
			.contentType(APPLICATION_JSON)
			.bodyValue(quotationRequest)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().contentType(ALL_VALUE)
			.expectBody().isEmpty();

		verify(helpdeskServiceMock).create(quotationRequest);
	}
}
