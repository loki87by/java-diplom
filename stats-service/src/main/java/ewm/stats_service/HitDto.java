package ewm.stats_service;

import lombok.Data;

@Data
public class HitDto {
    String uri;
    String app;
    String ip;
    String timestamp;
}
