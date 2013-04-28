package contradiction.client;

import java.util.List;

import api.model.Opinion;
import api.repository.OpinionRepository;
import contradiction.client.dto.solr.SolrResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/27/13
 * Time: 7:38 PM
 */
@Component
public class SolrClient {

    @Value("solr.opinion.core.endpoint")
    private String solrEndpointURL;

    private RestTemplate restTemplate;

    @Autowired
    OpinionRepository solrRepository;

    @PostConstruct
    private void initialize() {
        restTemplate = new RestTemplate();
    }

    public List<Opinion> findOpinionsForHolder(String holder) {

        List<Opinion> opinions = solrRepository.findByHolderLike(holder);

        return opinions;
    }

    public List<Opinion> findOpinionsByHolderAndTarget(String holder, String target) {
        List<Opinion> opinions = solrRepository.findByHolderAndTarget(holder,target);

        return opinions;
    }

    private Map<String, String> constructParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("wt", "json");
        params.put("indent","true");

        return params;
    }
}
