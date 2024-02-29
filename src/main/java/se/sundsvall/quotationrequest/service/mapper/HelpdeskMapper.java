package se.sundsvall.quotationrequest.service.mapper;

import static generated.se.sundsvall.seab.lime.Receivedthrough.KeyEnum.FORM;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.toInt;

import java.util.Optional;

import generated.se.sundsvall.seab.lime.Helpdesk;
import generated.se.sundsvall.seab.lime.Receivedthrough;
import se.sundsvall.quotationrequest.api.model.QuotationRequest;

public final class HelpdeskMapper {

	static final String DESCRIPTION_FORMAT_TEMPLATE = "%s %s\nTel: %s\nEmail: %s\n\n%s";
	static final Receivedthrough RECEIVEDTHROUGH = new Receivedthrough()
		.id(3_759_301)
		.key(FORM)
		.text("Formulär");

	private HelpdeskMapper() {}

	public static Helpdesk toHelpdesk(final QuotationRequest quotationRequest) {
		return new Helpdesk()
			.title(quotationRequest.getTitle())
			.description(toDescription(quotationRequest))
			.office(toInt(quotationRequest.getOfficeId(), INTEGER_ZERO))
			.helpdeskcategory(toInt(quotationRequest.getHelpdeskId(), INTEGER_ZERO))
			.receivedthrough(RECEIVEDTHROUGH);
	}

	public static Integer toHelpdeskId(final Helpdesk helpdesk) {
		return Optional.ofNullable(helpdesk).orElse(new Helpdesk()).getId();
	}

	private static String toDescription(final QuotationRequest quotationRequest) {
		return String.format(DESCRIPTION_FORMAT_TEMPLATE,
			quotationRequest.getContactDetails().getFirstName(),
			quotationRequest.getContactDetails().getSurname(),
			quotationRequest.getContactDetails().getPhoneNumber(),
			quotationRequest.getContactDetails().getEmailAddress(),
			quotationRequest.getNote());
	}
}
