package se.sundsvall.quotationrequest.apptest;

import static java.util.Map.entry;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
import se.sundsvall.quotationrequest.Application;

/**
 * Get MetaData tests.
 */
@WireMockAppTestSuite(files = "classpath:/GetMetaDataIT/", classes = Application.class)
class GetMetaDataIT extends AbstractAppTest {

	private static final Entry<String, List<String>> EXPECTED_SUCCESS_CONTENT_TYPE_HEADER = entry(CONTENT_TYPE, List.of(APPLICATION_JSON_VALUE));
	private static final String RESPONSE_FILE = "response.json";

	@Test
	void test01_getMetaData() throws Exception {
		setupCall()
			.withServicePath("/meta-data")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponseHeader(EXPECTED_SUCCESS_CONTENT_TYPE_HEADER.getKey(), EXPECTED_SUCCESS_CONTENT_TYPE_HEADER.getValue())
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test02_getMetaDataEmptyResponseFromLime() throws Exception {
		setupCall()
			.withServicePath("/meta-data")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponseHeader(EXPECTED_SUCCESS_CONTENT_TYPE_HEADER.getKey(), EXPECTED_SUCCESS_CONTENT_TYPE_HEADER.getValue())
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test03_getMetaDataAllInactiveInResponseFromLime() throws Exception {
		setupCall()
			.withServicePath("/meta-data")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponseHeader(EXPECTED_SUCCESS_CONTENT_TYPE_HEADER.getKey(), EXPECTED_SUCCESS_CONTENT_TYPE_HEADER.getValue())
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}
}
