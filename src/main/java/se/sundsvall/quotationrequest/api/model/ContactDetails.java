package se.sundsvall.quotationrequest.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Model for ContactDetails")
public class ContactDetails {

	@Schema(description = "First Name", example = "Joe", requiredMode = REQUIRED)
	@NotBlank
	private String firstName;

	@Schema(description = "Surname", example = "Doe", requiredMode = REQUIRED)
	@NotBlank
	private String surname;

	@Schema(description = "Phone Number", example = "079 1234567", requiredMode = REQUIRED)
	@NotBlank
	private String phoneNumber;

	@Schema(description = "Email Address", example = "joe.doe@test.se", requiredMode = REQUIRED)
	@Email
	@NotBlank
	private String emailAddress;

	public static ContactDetails create() {
		return new ContactDetails();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public ContactDetails withFirstName(final String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public ContactDetails withSurname(final String surname) {
		this.surname = surname;
		return this;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public ContactDetails withPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(final String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public ContactDetails withEmailAddress(final String emailAddress) {
		this.emailAddress = emailAddress;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, surname, phoneNumber, emailAddress);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final var other = (ContactDetails) obj;
		return Objects.equals(firstName, other.firstName) &&
			Objects.equals(surname, other.surname) &&
			Objects.equals(phoneNumber, other.phoneNumber) &&
			Objects.equals(emailAddress, other.emailAddress);
	}

	@Override
	public String toString() {
		final var builder = new StringBuilder();
		return builder.append("ContactDetails [firstName=").append(firstName).append(", surname=").append(surname).append(", phoneNumber=")
			.append(phoneNumber).append(", emailAddress=").append(emailAddress).append("]").toString();
	}
}
