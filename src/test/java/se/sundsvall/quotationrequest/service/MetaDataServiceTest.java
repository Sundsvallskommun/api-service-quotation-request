package se.sundsvall.quotationrequest.service;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wiremock.com.github.jknack.handlebars.internal.lang3.math.NumberUtils.toInt;

import generated.se.sundsvall.seab.lime.Helpdeskcategory;
import generated.se.sundsvall.seab.lime.Office;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import se.sundsvall.quotationrequest.integration.lime.LimeIntegration;

@ExtendWith(MockitoExtension.class)
class MetaDataServiceTest {

	@Mock
	private LimeIntegration limeIntegrationMock;

	@InjectMocks
	private MetaDataService metaDataService;

	@Test
	void getMetaData() {

		final var helpdeskCategoryId = "666";
		final var officeId = "777";

		when(limeIntegrationMock.getHelpdeskCategories()).thenReturn(
			toCollectionModel(List.of(new Helpdeskcategory().id(toInt(helpdeskCategoryId)).active(true))));

		when(limeIntegrationMock.getOffices()).thenReturn(
			toCollectionModel(List.of(new Office().id(toInt(officeId)).active(true))));

		final var result = metaDataService.getMetaData();

		assertThat(result).isNotNull();
		assertThat(result.getHelpdeskCategories()).hasSize(1);
		assertThat(result.getOffices()).hasSize(1);

		verify(limeIntegrationMock).getHelpdeskCategories();
		verify(limeIntegrationMock).getOffices();
	}

	@Test
	void getMetaDataWhenNothingIsReturned() {

		final var result = metaDataService.getMetaData();

		assertThat(result).isNotNull();
		assertThat(result.getHelpdeskCategories()).isEmpty();
		assertThat(result.getOffices()).isEmpty();

		verify(limeIntegrationMock).getHelpdeskCategories();
		verify(limeIntegrationMock).getOffices();
	}

	@Test
	void helpdeskIdExistsReturnTrue() {

		final var helpdeskCategoryId = "666";

		when(limeIntegrationMock.getHelpdeskCategories()).thenReturn(
			toCollectionModel(List.of(new Helpdeskcategory().id(toInt(helpdeskCategoryId)).active(true))));

		final var result = metaDataService.helpdeskIdExists(helpdeskCategoryId);

		assertThat(result).isTrue();

		verify(limeIntegrationMock).getHelpdeskCategories();
		verify(limeIntegrationMock).getOffices();
	}

	@Test
	void helpdeskIdExistsReturnFalseWhenActiveIsFalse() {

		final var helpdeskCategoryId = "666";

		when(limeIntegrationMock.getHelpdeskCategories()).thenReturn(
			toCollectionModel(List.of(new Helpdeskcategory().id(toInt(helpdeskCategoryId)).active(false))));

		final var result = metaDataService.helpdeskIdExists(helpdeskCategoryId);

		assertThat(result).isFalse();

		verify(limeIntegrationMock).getHelpdeskCategories();
		verify(limeIntegrationMock).getOffices();
	}

	@Test
	void helpdeskIdExistsReturnFalseWhenEmptyResult() {

		final var helpdeskCategoryId = "666";

		when(limeIntegrationMock.getHelpdeskCategories()).thenReturn(toCollectionModel(emptyList()));

		final var result = metaDataService.helpdeskIdExists(helpdeskCategoryId);

		assertThat(result).isFalse();

		verify(limeIntegrationMock).getHelpdeskCategories();
		verify(limeIntegrationMock).getOffices();
	}

	@Test
	void officeIdExistsReturnTrue() {

		final var officeId = "777";

		when(limeIntegrationMock.getOffices()).thenReturn(
			toCollectionModel(List.of(new Office().id(toInt(officeId)).active(true))));

		final var result = metaDataService.officeIdExists(officeId);

		assertThat(result).isTrue();

		verify(limeIntegrationMock).getHelpdeskCategories();
		verify(limeIntegrationMock).getOffices();
	}

	@Test
	void officeIdExistsReturnFalseWhenActiveIsFalse() {

		final var officeId = "777";

		when(limeIntegrationMock.getOffices()).thenReturn(
			toCollectionModel(List.of(new Office().id(toInt(officeId)).active(false))));

		final var result = metaDataService.officeIdExists(officeId);

		assertThat(result).isFalse();

		verify(limeIntegrationMock).getHelpdeskCategories();
		verify(limeIntegrationMock).getOffices();
	}

	@Test
	void officeIdExistsReturnFalseWhenEmptyResult() {

		final var officeId = "777";

		when(limeIntegrationMock.getOffices()).thenReturn(
			toCollectionModel(emptyList()));

		final var result = metaDataService.officeIdExists(officeId);

		assertThat(result).isFalse();

		verify(limeIntegrationMock).getHelpdeskCategories();
		verify(limeIntegrationMock).getOffices();
	}

	private <T> CollectionModel<T> toCollectionModel(final List<T> list) {
		return CollectionModel.of(list);
	}
}
