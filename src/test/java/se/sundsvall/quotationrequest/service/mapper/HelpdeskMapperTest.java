package se.sundsvall.quotationrequest.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.quotationrequest.service.mapper.HelpdeskMapper.RECEIVEDTHROUGH;
import static wiremock.com.github.jknack.handlebars.internal.lang3.math.NumberUtils.toInt;

import org.junit.jupiter.api.Test;

import se.sundsvall.generated.clients.limeseab.Helpdesk;
import se.sundsvall.quotationrequest.api.model.ContactDetails;
import se.sundsvall.quotationrequest.api.model.QuotationRequest;

class HelpdeskMapperTest {

	@Test
	void toHelpdesk() {

		final var emailAddress = "emailAddress";
		final var firstName = "firstName";
		final var phoneNumber = "phoneNumber";
		final var surname = "surname";
		final var helpdeskId = "helpdeskId";
		final var note = "note";
		final var officeId = "123";
		final var title = "title";

		final var quotationRequest = QuotationRequest.create()
			.withContactDetails(ContactDetails.create()
				.withEmailAddress(emailAddress)
				.withFirstName(firstName)
				.withPhoneNumber(phoneNumber)
				.withSurname(surname))
			.withHelpdeskId(helpdeskId)
			.withNote(note)
			.withOfficeId(officeId)
			.withTitle(title);

		final var result = HelpdeskMapper.toHelpdesk(quotationRequest);

		assertThat(result).isNotNull();
		assertThat(result.getTitle()).isEqualTo(title);
		assertThat(result.getDescription()).isEqualTo("firstName surname\nTel: phoneNumber\nEmail: emailAddress\n\nnote");
		assertThat(result.getReceivedthrough()).isEqualTo(RECEIVEDTHROUGH);
		assertThat(result.getOffice()).isEqualTo(toInt(officeId));
		assertThat(result.getHelpdeskcategory()).isEqualTo(toInt(helpdeskId));
	}

	@Test
	void toHelpdeskId() {

		final var id = 123;
		final var helpdesk = new Helpdesk().id(id);

		final var result = HelpdeskMapper.toHelpdeskId(helpdesk);

		assertThat(result).isNotNull().isEqualTo(id);
	}

	@Test
	void toHelpdeskIdWhenIdIsNull() {

		final var helpdesk = new Helpdesk().id(null);

		final var result = HelpdeskMapper.toHelpdeskId(helpdesk);

		assertThat(result).isNull();
	}
}
