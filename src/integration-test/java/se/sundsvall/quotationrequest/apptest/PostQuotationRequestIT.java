package se.sundsvall.quotationrequest.apptest;

import static java.util.Map.entry;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
import se.sundsvall.quotationrequest.Application;

/**
 * Post QuotationRequest tests.
 */
@WireMockAppTestSuite(files = "classpath:/PostQuotationRequestIT/", classes = Application.class)
class PostQuotationRequestIT extends AbstractAppTest {

	private static final Entry<String, List<String>> EXPECTED_SUCCESS_CONTENT_TYPE_HEADER = entry(CONTENT_TYPE, List.of(ALL_VALUE));
	private static final Entry<String, List<String>> EXPECTED_PROBLEM_CONTENT_TYPE_HEADER = entry(CONTENT_TYPE, List.of(APPLICATION_PROBLEM_JSON_VALUE));
	private static final String REQUEST_FILE = "request.json";
	private static final String RESPONSE_FILE = "response.json";

	@Test
	void test01_createQuotationRequest() throws Exception {
		setupCall()
			.withServicePath("/quotation-request")
			.withHttpMethod(POST)
			.withRequest(REQUEST_FILE)
			.withExpectedResponseStatus(CREATED)
			.withExpectedResponseHeader(EXPECTED_SUCCESS_CONTENT_TYPE_HEADER.getKey(), EXPECTED_SUCCESS_CONTENT_TYPE_HEADER.getValue())
			.withExpectedResponseBodyIsNull()
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test02_createQuotationRequestWhenOfficeIsMissing() throws Exception {
		setupCall()
			.withServicePath("/quotation-request")
			.withHttpMethod(POST)
			.withRequest(REQUEST_FILE)
			.withExpectedResponseStatus(NOT_FOUND)
			.withExpectedResponseHeader(EXPECTED_PROBLEM_CONTENT_TYPE_HEADER.getKey(), EXPECTED_PROBLEM_CONTENT_TYPE_HEADER.getValue())
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test03_createQuotationRequestWhenHelpdeskcategoryIsMissing() throws Exception {
		setupCall()
			.withServicePath("/quotation-request")
			.withHttpMethod(POST)
			.withRequest(REQUEST_FILE)
			.withExpectedResponseStatus(NOT_FOUND)
			.withExpectedResponseHeader(EXPECTED_PROBLEM_CONTENT_TYPE_HEADER.getKey(), EXPECTED_PROBLEM_CONTENT_TYPE_HEADER.getValue())
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}
}
