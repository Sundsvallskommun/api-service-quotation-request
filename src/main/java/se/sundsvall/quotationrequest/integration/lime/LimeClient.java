package se.sundsvall.quotationrequest.integration.lime;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static se.sundsvall.quotationrequest.integration.lime.configuration.LimeConfiguration.CLIENT_ID;

import generated.se.sundsvall.seab.lime.Helpdesk;
import generated.se.sundsvall.seab.lime.Helpdeskcategory;
import generated.se.sundsvall.seab.lime.Office;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import se.sundsvall.quotationrequest.integration.lime.configuration.LimeConfiguration;

@FeignClient(name = CLIENT_ID, url = "${integration.lime.url}", configuration = LimeConfiguration.class)
@CircuitBreaker(name = CLIENT_ID)
public interface LimeClient {

	/**
	 * Retrieves a list of Helpdeskcategory objects.
	 *
	 * @return a List of Helpdeskcategory objects
	 */
	@GetMapping(path = "/helpdeskcategory/", produces = HAL_JSON_VALUE)
	CollectionModel<Helpdeskcategory> getHelpdeskcategoryList();

	/**
	 * Retrieves a list of Office objects.
	 *
	 * @return a List of Office objects
	 */
	@GetMapping(path = "/office/", produces = HAL_JSON_VALUE)
	CollectionModel<Office> getOfficeList();

	/**
	 * Create a new Helpdesk object.
	 *
	 * @param  helpdesk The object to create.
	 * @return          the created Helpdesk object
	 */
	@PostMapping(path = "/helpdesk/", consumes = APPLICATION_JSON_VALUE, produces = HAL_JSON_VALUE)
	Helpdesk createHelpdesk(@RequestBody Helpdesk helpdesk);
}
