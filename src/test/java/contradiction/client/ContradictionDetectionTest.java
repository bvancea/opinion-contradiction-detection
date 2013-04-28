package contradiction.client;

import api.model.Contradiction;
import api.model.Opinion;
import java.util.List;
import contradiction.service.ContradictionDetectionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/28/13
 * Time: 1:38 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ContradictionDetectionTest {

    @Autowired
    private ContradictionDetectionService contradictionDetectionService;

    @Test
    public void testContradictionDetection() {
        Opinion opinion = new Opinion();
        opinion.setId("test_ID");
        opinion.setEntity("sidecar");
        opinion.setHolder("mary");
        opinion.setSentimentOrientation(new Float(0.5));

        List<Contradiction> contradictions = contradictionDetectionService.findContradictionsForOpinion(opinion);

        Assert.notNull(contradictions);
    }

}
