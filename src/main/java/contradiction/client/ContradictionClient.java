package contradiction.client;

import api.model.Contradiction;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/28/13
 * Time: 2:46 PM
 */
@Component
public class ContradictionClient {

    RestTemplate restTemplate = new RestTemplate();

    private String contradictionEndpointURL;

    public void saveContradictionsToRepository(List<Contradiction> contradictions) {

       //restTemplate.postForObject(contradictionEndpointURL,contradictions,null,null);
        System.out.println(contradictions);
    }
}
