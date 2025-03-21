package se.sundsvall.quotationrequest.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;
import static org.zalando.problem.Status.BAD_REQUEST;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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

	private static final String PATH = "/{municipalityId}/quotation-request";
	private static final String MUNICIPALITY_ID = "2281";

	@Autowired
	private WebTestClient webTestClient;

	@MockitoBean
	private HelpdeskService helpdeskServiceMock;

	@Test
	void createQuotationRequestMissingBody() {

		// Act
		final var response = webTestClient.post()
			.uri(builder -> builder.path(PATH).build(Map.of("municipalityId", MUNICIPALITY_ID)))
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
			"Required request body is missing: org.springframework.http.ResponseEntity<java.lang.Void> se.sundsvall.quotationrequest.api.QuotationRequestResource.createQuotationRequest(java.lang.String,se.sundsvall.quotationrequest.api.model.QuotationRequest)");

		verifyNoInteractions(helpdeskServiceMock);
	}

	@Test
	void createQuotationRequestEmptyBody() {

		// Act
		final var response = webTestClient.post()
			.uri(builder -> builder.path(PATH).build(Map.of("municipalityId", MUNICIPALITY_ID)))
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

		// Arrange
		final var quotationRequest = QuotationRequest.create()
			.withTitle("title")
			.withContactDetails(ContactDetails.create()) // Empty contact details
			.withNote("note")
			.withHelpdeskId("helpdeskId")
			.withOfficeId("officeId");

		// Act
		final var response = webTestClient.post()
			.uri(builder -> builder.path(PATH).build(Map.of("municipalityId", MUNICIPALITY_ID)))
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

		// Arrange
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

		// Act
		final var response = webTestClient.post()
			.uri(builder -> builder.path(PATH).build(Map.of("municipalityId", MUNICIPALITY_ID)))
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

	@Test
	void getMetaDataInvalidMunicipalityId() {

		// Arrange
		final var municipalityId = "invalid";
		final var quotationRequest = QuotationRequest.create()
			.withTitle("title")
			.withContactDetails(ContactDetails.create()
				.withEmailAddress("test@test.com")
				.withFirstName("firstName")
				.withPhoneNumber("phoneNumber")
				.withSurname("surname"))
			.withNote("note")
			.withHelpdeskId("helpdeskId")
			.withOfficeId("officeId");

		// Act
		final var response = webTestClient.post()
			.uri(builder -> builder.path(PATH).build(Map.of("municipalityId", municipalityId)))
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
			.containsExactly(tuple("createQuotationRequest.municipalityId", "not a valid municipality ID"));

		verifyNoInteractions(helpdeskServiceMock);
	}
}
