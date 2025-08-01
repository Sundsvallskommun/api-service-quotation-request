package se.sundsvall.quotationrequest.integration.lime;

import generated.se.sundsvall.seab.lime.Helpdesk;
import generated.se.sundsvall.seab.lime.Helpdeskcategory;
import generated.se.sundsvall.seab.lime.Office;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

@Component
public class LimeIntegration {

	private final LimeClient limeClient;

	public LimeIntegration(final LimeClient limeClient) {
		this.limeClient = limeClient;
	}

	@Cacheable("helpdeskcategories")
	public CollectionModel<Helpdeskcategory> getHelpdeskCategories() {
		return limeClient.getHelpdeskcategoryList();
	}

	@Cacheable("offices")
	public CollectionModel<Office> getOffices() {
		return limeClient.getOfficeList();
	}

	public Helpdesk createHelpdesk(final Helpdesk helpdesk) {
		return limeClient.createHelpdesk(helpdesk);
	}

}
