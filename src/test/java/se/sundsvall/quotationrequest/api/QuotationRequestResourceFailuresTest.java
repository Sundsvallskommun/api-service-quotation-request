package se.sundsvall.quotationrequest.api;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import se.sundsvall.quotationrequest.Application;
import se.sundsvall.quotationrequest.api.model.ContactDetails;
import se.sundsvall.quotationrequest.api.model.QuotationRequest;
import se.sundsvall.quotationrequest.service.HelpdeskService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class QuotationRequestResourceFailuresTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private HelpdeskService helpdeskServiceMock;

	@Test
	void createQuotationRequestMissingBody() {

		webTestClient.post().uri("/quotation-request")
			.contentType(APPLICATION_JSON)
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody()
			.jsonPath("$.title").isEqualTo(BAD_REQUEST.getReasonPhrase())
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.detail").isEqualTo(
				"Required request body is missing: org.springframework.http.ResponseEntity<java.lang.Void> se.sundsvall.quotationrequest.api.QuotationRequestResource.createQuotationRequest(org.springframework.web.util.UriComponentsBuilder,se.sundsvall.quotationrequest.api.model.QuotationRequest)");

		verifyNoInteractions(helpdeskServiceMock);
	}

	@Test
	void createQuotationRequestEmptyBody() {

		webTestClient.post().uri("/quotation-request")
			.contentType(APPLICATION_JSON)
			.bodyValue(QuotationRequest.create()) // Empty body
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("contactDetails")
			.jsonPath("$.violations[0].message").isEqualTo("must not be null")
			.jsonPath("$.violations[1].field").isEqualTo("helpdeskId")
			.jsonPath("$.violations[1].message").isEqualTo("must not be blank")
			.jsonPath("$.violations[2].field").isEqualTo("note")
			.jsonPath("$.violations[2].message").isEqualTo("must not be blank")
			.jsonPath("$.violations[3].field").isEqualTo("officeId")
			.jsonPath("$.violations[3].message").isEqualTo("must not be blank")
			.jsonPath("$.violations[4].field").isEqualTo("title")
			.jsonPath("$.violations[4].message").isEqualTo("must not be blank");

		verifyNoInteractions(helpdeskServiceMock);
	}

	@Test
	void createQuotationRequestEmptyContactDetails() {

		// Parameter values.
		final var quotationRequest = QuotationRequest.create()
			.withTitle("title")
			.withContactDetails(ContactDetails.create()) // Empty contact details
			.withNote("note")
			.withHelpdeskId("helpdeskId")
			.withOfficeId("officeId");

		webTestClient.post().uri("/quotation-request")
			.contentType(APPLICATION_JSON)
			.bodyValue(quotationRequest)
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("contactDetails.emailAddress")
			.jsonPath("$.violations[0].message").isEqualTo("must not be blank")
			.jsonPath("$.violations[1].field").isEqualTo("contactDetails.firstName")
			.jsonPath("$.violations[1].message").isEqualTo("must not be blank")
			.jsonPath("$.violations[2].field").isEqualTo("contactDetails.phoneNumber")
			.jsonPath("$.violations[2].message").isEqualTo("must not be blank")
			.jsonPath("$.violations[3].field").isEqualTo("contactDetails.surname")
			.jsonPath("$.violations[3].message").isEqualTo("must not be blank");

		verifyNoInteractions(helpdeskServiceMock);
	}

	@Test
	void createQuotationRequestBadEmailAddress() {

		// Parameter values.
		final var quotationRequest = QuotationRequest.create()
			.withTitle("title")
			.withContactDetails(ContactDetails.create()
				.withEmailAddress("bad-email") // Bad email-address.
				.withFirstName("firstName")
				.withPhoneNumber("phoneNumber")
				.withSurname("surname"))
			.withNote("note")
			.withHelpdeskId("helpdeskId")
			.withOfficeId("officeId");

		webTestClient.post().uri("/quotation-request")
			.contentType(APPLICATION_JSON)
			.bodyValue(quotationRequest)
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("contactDetails.emailAddress")
			.jsonPath("$.violations[0].message").isEqualTo("must be a well-formed email address");

		verifyNoInteractions(helpdeskServiceMock);
	}
}
