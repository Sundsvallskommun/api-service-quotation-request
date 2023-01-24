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

class ContactDetailsTest {

	@Test
	void testBean() {
		assertThat(ContactDetails.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var firstName = "firstName";
		final var surName = "surName";
		final var phoneNumber = "phoneNumber";
		final var emailAddress = "emailAddress";

		final var contactDetails = ContactDetails.create()
			.withFirstName(firstName)
			.withSurname(surName)
			.withPhoneNumber(phoneNumber)
			.withEmailAddress(emailAddress);

		assertThat(contactDetails).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(contactDetails.getFirstName()).isEqualTo(firstName);
		assertThat(contactDetails.getSurname()).isEqualTo(surName);
		assertThat(contactDetails.getPhoneNumber()).isEqualTo(phoneNumber);
		assertThat(contactDetails.getEmailAddress()).isEqualTo(emailAddress);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(ContactDetails.create()).hasAllNullFieldsOrProperties();
		assertThat(new ContactDetails()).hasAllNullFieldsOrProperties();
	}
}
