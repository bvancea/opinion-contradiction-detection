package contradiction.client.dto.solr;

import api.model.Opinion;
import com.sun.tools.javac.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    long numFound;
    long start;

    List<Opinion> docs;

    public long getNumFound() {
        return numFound;
    }

    public void setNumFound(long numFound) {
        this.numFound = numFound;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public List<Opinion> getDocs() {
        return docs;
    }

    public void setDocs(List<Opinion> docs) {
        this.docs = docs;
    }
}
