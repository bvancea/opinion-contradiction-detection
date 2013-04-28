package contradiction.client;

import api.model.Document;
import contradiction.client.dto.hbase.Documents;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/11/13
 * Time: 11:22 PM
 */
public class DocumentClient {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${document.repository.endpoint}")
    private String documentsEndpointURL;

    public Document findById(String docId) {

        String serviceEndpointURL = documentsEndpointURL + "/{id}";

        Map<String, String> urlVariables = new HashMap<String, String>();
        urlVariables.put("id", docId);

        Document result = restTemplate.getForObject(serviceEndpointURL,Document.class, urlVariables);

        return result;
    }

    public List<Document> findAll() {
        String serviceEndpointURL = documentsEndpointURL + "/findall";

        Documents documents = restTemplate.getForObject(serviceEndpointURL,Documents.class, null);

        return documents;
    }
}
