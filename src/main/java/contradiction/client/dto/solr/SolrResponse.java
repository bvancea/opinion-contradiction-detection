package contradiction.client.dto.solr;

import api.model.Opinion;
import com.sun.tools.javac.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/27/13
 * Time: 9:59 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolrResponse {

    private ResponseHeader responseHeader;
    private Response response;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}




