package se.sundsvall.quotationrequest.integration.lime.configuration.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Necessary, since the Lime json-property is "_id" and not "id" (as in the generated classes).
 */
public interface IdMixIn {

	@JsonProperty("_id")
	Integer getId();

	@JsonProperty("_id")
	void setId(Integer id);
}
