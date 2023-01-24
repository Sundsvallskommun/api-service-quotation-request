package se.sundsvall.quotationrequest.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class MetaDataResponseTest {

	@Test
	void testBean() {
		assertThat(MetaDataResponse.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var officeName = "officeName";
		final var officeId = "officeId";
		final var helpdeskName = "helpdeskName";
		final var helpdeskId = "helpdeskId";
		final var offices = List.of(MetaData.create().withId(officeId).withName(officeName));
		final var helpdesks = List.of(MetaData.create().withName(helpdeskName).withId(helpdeskId));
		final var metaDataResponse = MetaDataResponse.create()
			.withOffices(offices)
			.withHelpdeskCategories(helpdesks);

		assertThat(metaDataResponse).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(metaDataResponse.getOffices()).isEqualTo(offices);
		assertThat(metaDataResponse.getHelpdeskCategories()).isEqualTo(helpdesks);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(MetaDataResponse.create()).hasAllNullFieldsOrProperties();
		assertThat(new MetaDataResponse()).hasAllNullFieldsOrProperties();
	}
}
