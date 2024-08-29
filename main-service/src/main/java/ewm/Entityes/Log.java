package ewm.Entityes;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
public class Log {
    String app = "ewm-main-service";
    String uri;
    String ip;
    String timestamp;

    public Log(String uri, String ip){
       this.uri = uri;
       this.ip = ip;
        Instant now = Instant.now();
        Timestamp ts = Timestamp.from(now);
        this.timestamp = ts.toString().replace("T", " ").substring(0, 19);
    }
}
