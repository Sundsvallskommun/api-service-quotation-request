package se.sundsvall.quotationrequest.service.mapper;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static se.sundsvall.generated.clients.limeseab.Receivedthrough.KeyEnum.FORM;

import java.util.Optional;

import se.sundsvall.generated.clients.limeseab.Helpdesk;
import se.sundsvall.generated.clients.limeseab.Receivedthrough;
import se.sundsvall.quotationrequest.api.model.QuotationRequest;

public class HelpdeskMapper {

	static final String DESCRIPTION_FORMAT_TEMPLATE = "%s %s\nTel: %s\nEmail: %s\n\n%s";
	static final Receivedthrough RECEIVEDTHROUGH = new Receivedthrough()
		.id(3_759_301)
		.key(FORM)
		.text("Formulär");

	private HelpdeskMapper() {}

	public static Helpdesk toHelpdesk(QuotationRequest quotationRequest) {
		return new Helpdesk()
			.title(quotationRequest.getTitle())
			.description(toDescription(quotationRequest))
			.office(toInt(quotationRequest.getOfficeId(), INTEGER_ZERO))
			.helpdeskcategory(toInt(quotationRequest.getHelpdeskId(), INTEGER_ZERO))
			.receivedthrough(RECEIVEDTHROUGH);
	}

	public static Integer toHelpdeskId(Helpdesk helpdesk) {
		return Optional.ofNullable(helpdesk).orElse(new Helpdesk()).getId();
	}

	private static String toDescription(QuotationRequest quotationRequest) {
		return String.format(DESCRIPTION_FORMAT_TEMPLATE,
			quotationRequest.getContactDetails().getFirstName(),
			quotationRequest.getContactDetails().getSurname(),
			quotationRequest.getContactDetails().getPhoneNumber(),
			quotationRequest.getContactDetails().getEmailAddress(),
			quotationRequest.getNote());
	}
}
