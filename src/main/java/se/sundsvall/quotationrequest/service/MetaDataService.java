package se.sundsvall.quotationrequest.service;

import static se.sundsvall.quotationrequest.service.mapper.MetaDataMapper.toList;
import static se.sundsvall.quotationrequest.service.mapper.MetaDataMapper.toMetaDataResponse;

import java.util.Objects;
import org.springframework.stereotype.Service;
import se.sundsvall.quotationrequest.api.model.MetaDataResponse;
import se.sundsvall.quotationrequest.integration.lime.LimeIntegration;

@Service
public class MetaDataService {

	private final LimeIntegration limeIntegration;

	public MetaDataService(final LimeIntegration limeIntegration) {
		this.limeIntegration = limeIntegration;
	}

	public MetaDataResponse getMetaData() {
		return toMetaDataResponse(toList(limeIntegration.getHelpdeskCategories()), toList(limeIntegration.getOffices()));
	}

	public boolean helpdeskIdExists(String helpdeskId) {
		return getMetaData().getHelpdeskCategories().stream().anyMatch(obj -> Objects.equals(obj.getId(), helpdeskId));
	}

	public boolean officeIdExists(String officeId) {
		return getMetaData().getOffices().stream().anyMatch(obj -> Objects.equals(obj.getId(), officeId));
	}
}
