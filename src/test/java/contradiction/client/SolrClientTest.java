package contradiction.client;

import api.model.Opinion;
import api.repository.OpinionRepository;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/27/13
 * Time: 10:39 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SolrClientTest {

    @Autowired
    SolrClient solrClient;

    @Test
    public void testGetOpinions() {
        String holder = "bogdan";
        String target = "coffee";
        Double sentimentOrientation = 0.5;

        List<Opinion> opinions = solrClient.findOpinionsForHolder(holder);

        Assert.isNull(opinions);
    }
}
