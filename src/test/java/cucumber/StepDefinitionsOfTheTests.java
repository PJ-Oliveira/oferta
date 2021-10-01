package cucumber;

import com.pj.offer.OfferApplication;
import com.pj.offer.domain.model.Offer;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@ContextConfiguration(classes = OfferApplication.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StepDefinitionsOfTheTests {

    @LocalServerPort
    private int port;
    private final String URL = "http://localhost";
    private RestTemplate restTemplate = new RestTemplate();

    @Given("That, well, I can list all offer")
    public void willListAllOffer(){
        String connection = URL + ":" + port + "/offers/api/v1";
        List<Offer> allOffers = restTemplate.getForObject(connection, List.class);
        log.info("{}!", allOffers);
        assertTrue(!allOffers.isEmpty());

    }

    @Then("I can search for a another offer")
    public void iCanSearchForAnotherOffer(){
        String connection = URL + ":" + port + "/offers/api/v1/" + 1l;
        Offer offer = restTemplate.getForObject(connection, Offer.class);
        log.info("{}!", offer);
        assertNotNull(offer);
    }

    @And("I can create a newly offer too")
    public void willSendAOffer(){
        String connection = URL + ":" + port + "/offers/api/v1";
        var newOffer = ScenarioContextOfTheTests.offerCucumber();
        Offer offerSaved = restTemplate.postForObject(connection, newOffer, Offer.class);
        log.info("{}!", offerSaved);
        assertNotNull(offerSaved);
    }

    @Then("If I want to, I can delete a offer as well")
    public void ifIWantToDelete(){
        String connection = URL + ":" + port + "/offers/api/v1";
        var newOffer = ScenarioContextOfTheTests.offerCucumber();
        Offer offerSaved = restTemplate.postForObject(connection, newOffer, Offer.class);
        log.info("{}!", offerSaved);
        connection = connection + "/" + offerSaved.getId();
        restTemplate.delete(connection);
    }
}
