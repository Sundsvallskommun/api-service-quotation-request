package se.sundsvall.quotationrequest.integration.lime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import generated.se.sundsvall.seab.lime.Helpdesk;
import generated.se.sundsvall.seab.lime.Helpdeskcategory;
import generated.se.sundsvall.seab.lime.Office;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;

@ExtendWith(MockitoExtension.class)
class LimeIntegrationTest {

	@Mock
	private LimeClient limeClientMock;

	@InjectMocks
	private LimeIntegration limeIntegration;

	@AfterEach
	void afterEach() {
		verifyNoMoreInteractions(limeClientMock);
	}

	@Test
	void getHelpdeskCategories() {
		var collectionModel = CollectionModel.of(List.of(new Helpdeskcategory(), new Helpdeskcategory()));

		when(limeClientMock.getHelpdeskcategoryList())
			.thenReturn(collectionModel);

		var result = limeIntegration.getHelpdeskCategories();

		assertThat(result).isNotNull().isEqualTo(collectionModel);
		verify(limeClientMock).getHelpdeskcategoryList();
	}

	@Test
	void getOffices() {
		var collectionModel = CollectionModel.of(List.of(new Office(), new Office()));

		when(limeClientMock.getOfficeList()).thenReturn(collectionModel);

		var result = limeIntegration.getOffices();

		assertThat(result).isNotNull().isEqualTo(collectionModel);
		verify(limeClientMock).getOfficeList();
	}

	@Test
	void createHelpdesk() {
		var helpdeskRequest = new Helpdesk();
		var newHelpdesk = new Helpdesk();

		when(limeClientMock.createHelpdesk(helpdeskRequest)).thenReturn(newHelpdesk);

		var result = limeIntegration.createHelpdesk(helpdeskRequest);

		assertThat(result).isNotNull().isEqualTo(newHelpdesk);
		verify(limeClientMock).createHelpdesk(helpdeskRequest);
	}

}
