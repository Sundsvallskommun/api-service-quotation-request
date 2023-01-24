package se.sundsvall.quotationrequest.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class QuotationRequestTest {

	@Test
	void testBean() {
		assertThat(QuotationRequest.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var title = "title";
		final var firstName = "firstName";
		final var surname = "surname";
		final var phoneNumber = "phoneNumber";
		final var emailAddress = "emailAddress";
		final var contactDetails = ContactDetails.create()
			.withFirstName(firstName)
			.withSurname(surname)
			.withPhoneNumber(phoneNumber)
			.withEmailAddress(emailAddress);
		final var note = "note";
		final var helpdeskId = "helpdeskId";
		final var officeId = "officeId";

		final var quotationRequest = QuotationRequest.create()
			.withTitle(title)
			.withContactDetails(contactDetails)
			.withNote(note)
			.withHelpdeskId(helpdeskId)
			.withOfficeId(officeId);

		assertThat(quotationRequest).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(quotationRequest.getTitle()).isEqualTo(title);
		assertThat(quotationRequest.getContactDetails().getFirstName()).isEqualTo(firstName);
		assertThat(quotationRequest.getContactDetails().getSurname()).isEqualTo(surname);
		assertThat(quotationRequest.getContactDetails().getPhoneNumber()).isEqualTo(phoneNumber);
		assertThat(quotationRequest.getContactDetails().getEmailAddress()).isEqualTo(emailAddress);
		assertThat(quotationRequest.getNote()).isEqualTo(note);
		assertThat(quotationRequest.getHelpdeskId()).isEqualTo(helpdeskId);
		assertThat(quotationRequest.getOfficeId()).isEqualTo(officeId);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(QuotationRequest.create()).hasAllNullFieldsOrProperties();
		assertThat(new QuotationRequest()).hasAllNullFieldsOrProperties();
	}
}
