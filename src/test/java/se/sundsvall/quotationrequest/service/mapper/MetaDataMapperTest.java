package se.sundsvall.quotationrequest.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.util.List;

import org.junit.jupiter.api.Test;

import se.sundsvall.generated.clients.limeseab.Helpdeskcategory;
import se.sundsvall.generated.clients.limeseab.Office;
import se.sundsvall.quotationrequest.api.model.MetaData;

class MetaDataMapperTest {

	@Test
	void toMetaDataResponse() {
		final var result = MetaDataMapper.toMetaDataResponse(setupHelpdeskcategoryList(), setupOfficeList());

		assertThat(result).isNotNull();
		assertThat(result.getHelpdeskCategories())
			.extracting(MetaData::getId, MetaData::getName)
			.containsExactly(
				tuple("1", "helpdeskcategory-1"),
				tuple("3", "helpdeskcategory-3"));
		assertThat(result.getOffices())
			.extracting(MetaData::getId, MetaData::getName)
			.containsExactly(
				tuple("1", "office-1"),
				tuple("3", "office-3"));
	}

	@Test
	void toMetaDataResponseFromNull() {
		final var result = MetaDataMapper.toMetaDataResponse(null, null);

		assertThat(result).isNotNull();
		assertThat(result.getHelpdeskCategories()).isEmpty();
		assertThat(result.getOffices()).isEmpty();
	}

	private List<Helpdeskcategory> setupHelpdeskcategoryList() {
		return List.of(
			new Helpdeskcategory().active(true).name("helpdeskcategory-1").id(1),
			new Helpdeskcategory().active(false).name("helpdeskcategory-2").id(2), // Will not end up in the result.
			new Helpdeskcategory().active(true).name("helpdeskcategory-3").id(3));
	}

	private List<Office> setupOfficeList() {
		return List.of(
			new Office().active(true).name("office-1").id(1),
			new Office().active(false).name("office-2").id(2), // Will not end up in the result.
			new Office().active(true).name("office-3").id(3));
	}
}
