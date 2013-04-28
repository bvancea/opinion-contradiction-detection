package contradiction.client.dto.solr;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseHeader {

    int status;
    int QTime;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQTime() {
        return QTime;
    }

    public void setQTime(int QTime) {
        this.QTime = QTime;
    }
}
