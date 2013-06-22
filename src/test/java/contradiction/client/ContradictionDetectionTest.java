package contradiction.client;

import api.model.Contradiction;
import api.model.Opinion;

import java.io.FileWriter;
import java.util.*;

import contradiction.constants.SentimentOrientationConstants;
import contradiction.service.ContradictionDetectionService;
import contradiction.service.SearchService;
import contradiction.util.ParserUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

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

    @Autowired
    private SearchService searchService;

    @Autowired
    private SolrClient solrClient;


    private final static int ITERATIONS = 1000;
    private final static String FILENAME = "/opt/solr/experiments/contradictions.xls";
    private final static String SEARCH_FILENAME = "/opt/solr/experiments/search.xls";

    List<String> names = new ArrayList<String>();
    List<String> targets = new ArrayList<String>();
    List<String> sentiments = new ArrayList<String>();
    List<Double> orientations = new ArrayList<Double>();

    @PostConstruct
    private void initializeStuff() {

        ParserUtil.parseWords("/opt/solr/words/names.txt", names);
        ParserUtil.parseWords("/opt/solr/words/nouns.txt", targets);
        ParserUtil.parseSentimentWords("/opt/solr/words/sentiments.txt", sentiments, orientations);

    }

    @Test
     public void testContradictionDetection() {
        Opinion opinion = new Opinion();
        opinion.setId("test_ID");
        opinion.setEntity("coffee");
        opinion.setHolder("john");
        opinion.setSentimentOrientation(new Float(0.5));

        long start = System.currentTimeMillis();
        List<Contradiction> contradictions = contradictionDetectionService.findContradictionsForOpinion(opinion);

        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println(time);
        System.out.println(contradictions.size());
        Assert.notNull(contradictions);
    }

    @Test
    public void testContradictionDetectionIterations() throws Exception {

        long average = 0;

        long max = -1;

        FileWriter writer = new FileWriter(FILENAME);
        writer.append("Iterations,Holder, Target,Time, Results\n");
        Opinion query = new Opinion();

        query.setSentimentOrientation(0.9f);
        Random random = new Random();
        int j = 0;

        for (int i = 0; i < ITERATIONS; i++) {

            query.setId("iteration_" + i);
            j = random.nextInt(names.size());
            String holder;
            String[] temp = names.get(j).split(" ");
            holder = (temp.length > 0 ) ? temp[0] : names.get(j);
            query.setHolder(holder);

            j = random.nextInt(targets.size());
            String target = targets.get(j);
            query.setEntity(target);

            long start = System.currentTimeMillis();

            long size = 0;

            boolean exception = false;
            try {
                List<Contradiction> contradictions = contradictionDetectionService.findContradictionsForOpinion(query);
                size = contradictions.size();
            } catch (Exception e) {
                System.out.println(query +  "\n" + e);
                i--;
                exception = true;
                throw new Exception(e);
            }

            if (!exception) {
                long end = System.currentTimeMillis();
                long time = end - start;

                writer.append(i + "," + holder + "," + target + "," + time + "," +  size +"\n" );
                max = (time > max) ? time : max;
                average += time;
            }
        }

        writer.close();
        average /= ITERATIONS;

        System.out.println("Iterations: " + ITERATIONS);
        System.out.println("Average: " + average);
        System.out.println("Max time: " + max);

    }

    @Test
    public void testRetrieveSimilarOpinions() throws Exception {
        long average = 0;

        long max = -1;

        FileWriter writer = new FileWriter(SEARCH_FILENAME);

        writer.append("Iterations,Target, Sentiment Orientation,Time, Results\n");

        Random random = new Random();
        int j = 0;

        for (int i = 0; i < ITERATIONS; i++) {

            long start = System.currentTimeMillis();

            long size = 0;

            j = random.nextInt(targets.size());
            String target = targets.get(j);

            j = random.nextInt(SentimentOrientationConstants.setimentOrientations.length);
            Double sentimentOrientation = SentimentOrientationConstants.setimentOrientations[j];

            List<Opinion> result = searchService.findOpinionsForTargetAndSentimentOrientation(target,sentimentOrientation);

            long end = System.currentTimeMillis();
            long time = end - start;

            writer.append(i + "," + target + "," + sentimentOrientation + "," + time + "," +  size +"\n" );
            max = (time > max) ? time : max;
            average += time;
        }

        writer.close();
        average /= ITERATIONS;

        System.out.println("Iterations: " + ITERATIONS);
        System.out.println("Average: " + average);
        System.out.println("Max time: " + max);

    }

    @Test
    public void testRetrieveSimilarOpinionsNoExpansions() throws Exception {
        long average = 0;

        long max = -1;

        FileWriter writer = new FileWriter(SEARCH_FILENAME);

        writer.append("Iterations,Target, Sentiment Orientation,Time\n");

        Random random = new Random();
        int j = 0;

        for (int i = 0; i < ITERATIONS; i++) {

            long start = System.currentTimeMillis();

            long size = 0;

            j = random.nextInt(targets.size());
            String target = targets.get(j);

            j = random.nextInt(SentimentOrientationConstants.setimentOrientations.length);
            Double sentimentOrientation = SentimentOrientationConstants.setimentOrientations[j];

            //List<Opinion> result = searchService.findOpinionsForTargetAndSentimentOrientation(target,sentimentOrientation);

            String string = solrClient.opinionsForTargetAndSentimentOrientation(target, sentimentOrientation);

            long end = System.currentTimeMillis();
            long time = end - start;

            writer.append(i + "," + target + "," + sentimentOrientation + "," + time + "\n" );
            max = (time > max) ? time : max;
            average += time;
        }

        writer.close();
        average /= ITERATIONS;

        System.out.println("Iterations: " + ITERATIONS);
        System.out.println("Average: " + average);
        System.out.println("Max time: " + max);

    }

}
