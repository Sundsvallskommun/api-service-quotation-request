package se.sundsvall.quotationrequest.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "QuotationRequest creation request model")
public class QuotationRequest {

	@Schema(description = "Title of Quotation Request", example = "Installation av elnät", requiredMode = REQUIRED)
	@NotBlank
	private String title;

	@Schema(description = "Description of Quotation Request", example = "Beskrivning av installationen", requiredMode = REQUIRED)
	@NotBlank
	private String note;

	@Schema(description = "Category of Quotation Request", example = "3601", requiredMode = REQUIRED)
	@NotBlank
	private String helpdeskId;

	@Schema(description = "Where the Quotation Request should end up", example = "2701", requiredMode = REQUIRED)
	@NotBlank
	private String officeId;

	@NotNull
	@Valid
	private ContactDetails contactDetails;

	public static QuotationRequest create() {
		return new QuotationRequest();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public QuotationRequest withTitle(final String title) {
		this.title = title;
		return this;
	}

	public String getNote() {
		return note;
	}

	public void setNote(final String note) {
		this.note = note;
	}

	public QuotationRequest withNote(final String note) {
		this.note = note;
		return this;
	}

	public String getHelpdeskId() {
		return helpdeskId;
	}

	public void setHelpdeskId(final String helpdeskId) {
		this.helpdeskId = helpdeskId;
	}

	public QuotationRequest withHelpdeskId(final String helpdeskId) {
		this.helpdeskId = helpdeskId;
		return this;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(final String officeId) {
		this.officeId = officeId;
	}

	public QuotationRequest withOfficeId(final String officeId) {
		this.officeId = officeId;
		return this;
	}

	public ContactDetails getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(final ContactDetails contactDetails) {
		this.contactDetails = contactDetails;
	}

	public QuotationRequest withContactDetails(final ContactDetails contactDetails) {
		this.contactDetails = contactDetails;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, contactDetails, note, helpdeskId, officeId);
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
		final var other = (QuotationRequest) obj;
		return Objects.equals(title, other.title) && Objects.equals(contactDetails, other.contactDetails)
			&& Objects.equals(note, other.note) && Objects.equals(helpdeskId, other.helpdeskId)
			&& Objects.equals(officeId, other.officeId);
	}

	@Override
	public String toString() {
		final var builder = new StringBuilder();
		builder.append("QuotationRequest [title=").append(title).append(", contactDetails=").append(contactDetails).append(", note=")
			.append(note).append(", helpdeskId=").append(helpdeskId).append(", officeId=").append(officeId)
			.append("]");
		return builder.toString();
	}
}
