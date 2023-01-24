package se.sundsvall.quotationrequest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.zalando.problem.Status.NOT_FOUND;
import static se.sundsvall.quotationrequest.service.Constants.ERROR_HELPDESK_CATEGORY_NOT_FOUND;
import static se.sundsvall.quotationrequest.service.Constants.ERROR_OFFICE_NOT_FOUND;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zalando.problem.ThrowableProblem;

import se.sundsvall.generated.clients.limeseab.Helpdesk;
import se.sundsvall.quotationrequest.api.model.ContactDetails;
import se.sundsvall.quotationrequest.api.model.QuotationRequest;
import se.sundsvall.quotationrequest.integration.lime.LimeClient;

@ExtendWith(MockitoExtension.class)
class HelpdeskServiceTest {

	@Mock
	private LimeClient limeClientMock;

	@Mock
	private MetaDataService metaDataServiceMock;

	@InjectMocks
	private HelpdeskService helpdeskService;

	@Test
	void create() {

		final var helpdeskId = "666";
		final var officeId = "777";

		when(metaDataServiceMock.helpdeskIdExists(helpdeskId)).thenReturn(true);
		when(metaDataServiceMock.officeIdExists(officeId)).thenReturn(true);

		final var input = QuotationRequest.create()
			.withContactDetails(ContactDetails.create())
			.withHelpdeskId(helpdeskId)
			.withOfficeId(officeId);

		helpdeskService.create(input);

		verify(metaDataServiceMock).helpdeskIdExists(helpdeskId);
		verify(metaDataServiceMock).officeIdExists(officeId);
		verify(limeClientMock).createHelpdesk(any(Helpdesk.class));
	}

	@Test
	void createWhenHelpdeskIdValidationFails() {

		final var helpdeskId = "666";
		final var officeId = "777";

		final var input = QuotationRequest.create()
			.withContactDetails(ContactDetails.create())
			.withHelpdeskId(helpdeskId)
			.withOfficeId(officeId);

		final var throwableProblem = assertThrows(ThrowableProblem.class, () -> helpdeskService.create(input));

		assertThat(throwableProblem.getMessage()).isEqualTo("Not Found: " + ERROR_HELPDESK_CATEGORY_NOT_FOUND);
		assertThat(throwableProblem.getStatus()).isEqualTo(NOT_FOUND);

		verify(metaDataServiceMock).helpdeskIdExists(helpdeskId);
		verify(metaDataServiceMock, never()).officeIdExists(any());
		verify(limeClientMock, never()).createHelpdesk(any());
	}

	@Test
	void createWhenOfficeIdValidationFails() {

		final var helpdeskId = "666";
		final var officeId = "777";

		when(metaDataServiceMock.helpdeskIdExists(helpdeskId)).thenReturn(true);

		final var input = QuotationRequest.create()
			.withContactDetails(ContactDetails.create())
			.withOfficeId(officeId)
			.withHelpdeskId(helpdeskId);

		final var throwableProblem = assertThrows(ThrowableProblem.class, () -> helpdeskService.create(input));

		assertThat(throwableProblem.getMessage()).isEqualTo("Not Found: " + ERROR_OFFICE_NOT_FOUND);
		assertThat(throwableProblem.getStatus()).isEqualTo(NOT_FOUND);

		verify(metaDataServiceMock).helpdeskIdExists(helpdeskId);
		verify(metaDataServiceMock).officeIdExists(officeId);
		verify(limeClientMock, never()).createHelpdesk(any());
	}
}
