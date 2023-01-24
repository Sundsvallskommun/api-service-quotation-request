package se.sundsvall.quotationrequest.service;

import static org.apache.commons.lang3.BooleanUtils.isNotTrue;
import static org.zalando.problem.Status.NOT_FOUND;
import static se.sundsvall.quotationrequest.service.Constants.ERROR_HELPDESK_CATEGORY_NOT_FOUND;
import static se.sundsvall.quotationrequest.service.Constants.ERROR_OFFICE_NOT_FOUND;
import static se.sundsvall.quotationrequest.service.mapper.HelpdeskMapper.toHelpdesk;
import static se.sundsvall.quotationrequest.service.mapper.HelpdeskMapper.toHelpdeskId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;

import se.sundsvall.quotationrequest.api.model.QuotationRequest;
import se.sundsvall.quotationrequest.integration.lime.LimeClient;

@Service
public class HelpdeskService {

	@Autowired
	private LimeClient limeClient;

	@Autowired
	private MetaDataService metaDataService;

	public Integer create(QuotationRequest quotationRequest) {
		validate(quotationRequest);
		return toHelpdeskId(limeClient.createHelpdesk(toHelpdesk(quotationRequest)));
	}

	private void validate(QuotationRequest quotationRequest) {
		if (isNotTrue(metaDataService.helpdeskIdExists(quotationRequest.getHelpdeskId()))) {
			throw Problem.valueOf(NOT_FOUND, ERROR_HELPDESK_CATEGORY_NOT_FOUND);
		}
		if (isNotTrue(metaDataService.officeIdExists(quotationRequest.getOfficeId()))) {
			throw Problem.valueOf(NOT_FOUND, ERROR_OFFICE_NOT_FOUND);
		}
	}
}
