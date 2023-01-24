package se.sundsvall.quotationrequest.service;

import static se.sundsvall.quotationrequest.service.mapper.MetaDataMapper.toList;
import static se.sundsvall.quotationrequest.service.mapper.MetaDataMapper.toMetaDataResponse;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.sundsvall.quotationrequest.api.model.MetaDataResponse;
import se.sundsvall.quotationrequest.integration.lime.LimeClient;

@Service
public class MetaDataService {

	@Autowired
	private LimeClient limeClient;

	public MetaDataResponse getMetaData() {
		return toMetaDataResponse(toList(limeClient.getHelpdeskcategoryList()), toList(limeClient.getOfficeList()));
	}

	public boolean helpdeskIdExists(String helpdeskId) {
		return getMetaData().getHelpdeskCategories().stream().anyMatch(obj -> Objects.equals(obj.getId(), helpdeskId));
	}

	public boolean officeIdExists(String officeId) {
		return getMetaData().getOffices().stream().anyMatch(obj -> Objects.equals(obj.getId(), officeId));
	}
}
