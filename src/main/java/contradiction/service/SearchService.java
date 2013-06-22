package contradiction.service;

import api.model.Opinion;
import java.util.List;
import contradiction.client.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 5/25/13
 * Time: 1:48 PM
 */
@Service
public class SearchService {

    @Autowired
    SolrClient solrClient;

    public List<Opinion> findOpinionsForTargetAndSentimentOrientation(String target, Double sentimentOrientation) {

        List<Opinion> opinions = solrClient.findOpinionsForTargetAndSentimentOrientation(target,sentimentOrientation);

        return opinions;
    }

    public List<Opinion> findOpinionsForTargetExpansionsAndSentimentOrientation(String target, Double sentimentOrientation) {

        List<Opinion> opinions = solrClient.findOpinionsForTargetAndSentimentOrientation(target,sentimentOrientation);

        return opinions;
    }

    public List<Opinion> findOpinionsForTargetAndSentimentWord(String target, String sentimentWord) {

        List<Opinion> opinions = solrClient.findOpinionsForTargetAndSentimentWord(target, sentimentWord);

        return opinions;
    }

}
