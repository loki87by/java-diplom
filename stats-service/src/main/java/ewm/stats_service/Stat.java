package ewm.stats_service;

import lombok.Data;

@Data
public class Stat{
    String app;
    String uri;
    Long hits;

    public Stat(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}
