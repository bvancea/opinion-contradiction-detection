package contradiction.service;

import api.model.Contradiction;

import java.util.ArrayList;
import java.util.List;

import api.model.Opinion;
import api.model.util.ContradictionConstants;
import contradiction.client.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/28/13
 * Time: 11:02 AM
 */
@Service
public class ContradictionDetectionService {

    @Autowired
    SolrClient solrClient;

    private double threshold = 0.5;

    public List<Contradiction> findContradictionsForOpinion(Opinion opinion) {

        List<Contradiction> contradictions = new ArrayList<Contradiction>();

        String holder = opinion.getHolder();
        String target = opinion.getEntity();


        List<Opinion> contradictionCandidates = solrClient.findOpinionsByHolderAndTarget(holder, target);

        for (Opinion candidate: contradictionCandidates) {
           contradictions = checkForContradiction(opinion,candidate, contradictions);
        }

        return contradictions;
    }

    private List<Contradiction> checkForContradiction(Opinion newOpinion, Opinion candidate, List<Contradiction> contradictions) {

        if (sameTarget(newOpinion, candidate)) {
            contradictions = checkForSameTarget(newOpinion, candidate, contradictions);
        } else {
            contradictions = checkForSameTargetExpansion(newOpinion, candidate, contradictions);
        }

        return contradictions;
    }

    private List<Contradiction> checkForSameTarget(Opinion newOpinion, Opinion candidate, List<Contradiction> contradictions) {
        double sentimentOrientation = newOpinion.getSentimentOrientation();
        double candidateSO = candidate.getSentimentOrientation();
        double difference = Math.abs(sentimentOrientation - candidateSO);

        if (difference > threshold) {
            Contradiction contradiction = new Contradiction();
            contradiction.setContradictionType(ContradictionConstants.SUSPECTED);
            contradiction.setFirstOpinionId(newOpinion.getId());
            contradiction.setSecondOpinionId(candidate.getId());
            contradictions.add(contradiction);
        }
        return contradictions;
    }

    private List<Contradiction> checkForSameTargetExpansion(Opinion newOpinion, Opinion candidate, List<Contradiction> contradictions) {

        List<String> expansions = candidate.getTargetExpansions();
        int index = expansions.indexOf(newOpinion.getEntity());

        double targetExpansionDistance = candidate.getTargetExpansionWeights().get(index);
        double sentimentOrientation = newOpinion.getSentimentOrientation();
        double candidateSO = candidate.getSentimentOrientation();

        candidateSO = modifySentimentOrientation(candidateSO, targetExpansionDistance);

        double difference = Math.abs(sentimentOrientation - candidateSO);

        if (difference > threshold) {
            Contradiction contradiction = new Contradiction();
            contradiction.setContradictionType(ContradictionConstants.SUSPECTED);
            contradiction.setFirstOpinionId(newOpinion.getId());
            contradiction.setSecondOpinionId(candidate.getId());
            contradictions.add(contradiction);
        }

        return contradictions;
    }

    private double modifySentimentOrientation(double candidateSO, double targetExpansionDistance) {

        if (candidateSO < 0 ) {
            candidateSO += targetExpansionDistance;
        } else if (candidateSO > 0) {
            candidateSO -= targetExpansionDistance;
        }

        return candidateSO;
    }

    private boolean sameTarget(Opinion first, Opinion second) {

        boolean result;
        if (first.getEntity() == null || second.getEntity() == null) {
            result = false;
        } else {
            result = first.getEntity().equals(second.getEntity());
        }
        return result;
    }

}
