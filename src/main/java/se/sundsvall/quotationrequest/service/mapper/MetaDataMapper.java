package se.sundsvall.quotationrequest.service.mapper;

import static java.util.Collections.emptyList;
import static org.springframework.hateoas.CollectionModel.empty;

import generated.se.sundsvall.seab.lime.Helpdeskcategory;
import generated.se.sundsvall.seab.lime.Office;
import java.util.List;
import java.util.Optional;
import org.springframework.hateoas.CollectionModel;
import se.sundsvall.quotationrequest.api.model.MetaData;
import se.sundsvall.quotationrequest.api.model.MetaDataResponse;

public final class MetaDataMapper {

	private MetaDataMapper() {}

	public static <T> List<T> toList(final CollectionModel<T> collectionModel) {
		return Optional.ofNullable(collectionModel).orElse(empty()).getContent().stream().toList();
	}

	public static MetaDataResponse toMetaDataResponse(final List<Helpdeskcategory> helpdeskCategoryList, final List<Office> officeList) {
		return MetaDataResponse.create()
			.withHelpdeskCategories(toHelpdeskCategoryList(helpdeskCategoryList))
			.withOffices(toOfficeList(officeList));
	}

	private static List<MetaData> toHelpdeskCategoryList(final List<Helpdeskcategory> helpdeskCategoryList) {
		return Optional.ofNullable(helpdeskCategoryList).orElse(emptyList()).stream()
			.filter(Helpdeskcategory::getActive)
			.map(obj -> MetaData.create().withId(String.valueOf(obj.getId())).withName(obj.getName()))
			.toList();
	}

	private static List<MetaData> toOfficeList(final List<Office> officeList) {
		return Optional.ofNullable(officeList).orElse(emptyList()).stream()
			.filter(Office::getActive)
			.map(obj -> MetaData.create().withId(String.valueOf(obj.getId())).withName(obj.getName()))
			.toList();
	}
}
