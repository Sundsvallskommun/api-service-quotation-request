package se.sundsvall.quotationrequest.api.model;

import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Model for Meta Data Response")
public class MetaDataResponse {

	@Schema(description = "List of available offices")
	private List<MetaData> offices;

	@Schema(description = "List of available helpdeskcategories")
	private List<MetaData> helpdeskCategories;

	public static MetaDataResponse create() {
		return new MetaDataResponse();
	}

	public List<MetaData> getOffices() {
		return offices;
	}

	public void setOffices(List<MetaData> offices) {
		this.offices = offices;
	}

	public MetaDataResponse withOffices(List<MetaData> offices) {
		this.offices = offices;
		return this;
	}

	public List<MetaData> getHelpdeskCategories() {
		return helpdeskCategories;
	}

	public void setHelpdeskCategories(List<MetaData> helpdeskCategories) {
		this.helpdeskCategories = helpdeskCategories;
	}

	public MetaDataResponse withHelpdeskCategories(List<MetaData> helpdeskCategories) {
		this.helpdeskCategories = helpdeskCategories;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		MetaDataResponse that = (MetaDataResponse) o;
		return Objects.equals(offices, that.offices) && Objects.equals(helpdeskCategories, that.helpdeskCategories);
	}

	@Override
	public int hashCode() {
		return Objects.hash(offices, helpdeskCategories);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("MetaDataResponse [offices=").append(offices).append("helpdeskCategories=").append(helpdeskCategories).append("]").toString();
	}
}
