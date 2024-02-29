package se.sundsvall.quotationrequest.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;
import static org.zalando.problem.Status.BAD_REQUEST;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.zalando.problem.Problem;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;

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

		final var response = webTestClient.post().uri("/quotation-request")
			.contentType(APPLICATION_JSON)
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody(Problem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getDetail()).isEqualTo(
			"Required request body is missing: org.springframework.http.ResponseEntity<java.lang.Void> se.sundsvall.quotationrequest.api.QuotationRequestResource.createQuotationRequest(org.springframework.web.util.UriComponentsBuilder,se.sundsvall.quotationrequest.api.model.QuotationRequest)");

		verifyNoInteractions(helpdeskServiceMock);
	}

	@Test
	void createQuotationRequestEmptyBody() {

		final var response = webTestClient.post().uri("/quotation-request")
			.contentType(APPLICATION_JSON)
			.bodyValue(QuotationRequest.create()) // Empty body
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(
				tuple("contactDetails", "must not be null"),
				tuple("helpdeskId", "must not be blank"),
				tuple("note", "must not be blank"),
				tuple("officeId", "must not be blank"),
				tuple("title", "must not be blank"));

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

		final var response = webTestClient.post().uri("/quotation-request")
			.contentType(APPLICATION_JSON)
			.bodyValue(quotationRequest)
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(
				tuple("contactDetails.emailAddress", "must not be blank"),
				tuple("contactDetails.firstName", "must not be blank"),
				tuple("contactDetails.phoneNumber", "must not be blank"),
				tuple("contactDetails.surname", "must not be blank"));

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

		final var response = webTestClient.post().uri("/quotation-request")
			.contentType(APPLICATION_JSON)
			.bodyValue(quotationRequest)
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(
				tuple("contactDetails.emailAddress", "must be a well-formed email address"));

		verifyNoInteractions(helpdeskServiceMock);
	}
}
