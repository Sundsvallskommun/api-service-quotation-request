package se.sundsvall.quotationrequest.service.mapper;

import static java.util.Collections.emptyList;
import static org.springframework.hateoas.CollectionModel.empty;

import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.CollectionModel;

import se.sundsvall.generated.clients.limeseab.Helpdeskcategory;
import se.sundsvall.generated.clients.limeseab.Office;
import se.sundsvall.quotationrequest.api.model.MetaData;
import se.sundsvall.quotationrequest.api.model.MetaDataResponse;

public class MetaDataMapper {

	private MetaDataMapper() {}

	public static <T> List<T> toList(CollectionModel<T> collectionModel) {
		return Optional.ofNullable(collectionModel).orElse(empty()).getContent().stream().toList();
	}

	public static MetaDataResponse toMetaDataResponse(List<Helpdeskcategory> helpdeskCategoryList, List<Office> officeList) {
		return MetaDataResponse.create()
			.withHelpdeskCategories(toHelpdeskCategoryList(helpdeskCategoryList))
			.withOffices(toOfficeList(officeList));
	}

	private static List<MetaData> toHelpdeskCategoryList(List<Helpdeskcategory> helpdeskCategoryList) {
		return Optional.ofNullable(helpdeskCategoryList).orElse(emptyList()).stream()
			.filter(Helpdeskcategory::getActive)
			.map(obj -> MetaData.create().withId(String.valueOf(obj.getId())).withName(obj.getName()))
			.toList();
	}

	private static List<MetaData> toOfficeList(List<Office> officeList) {
		return Optional.ofNullable(officeList).orElse(emptyList()).stream()
			.filter(Office::getActive)
			.map(obj -> MetaData.create().withId(String.valueOf(obj.getId())).withName(obj.getName()))
			.toList();
	}
}
