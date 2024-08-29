package ewm.stats_service;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class Mapper {

    public Timestamp stringToTimestamp(String arg) {

        if (arg.length() <= 11) {
            arg = arg.trim() + " 00:00:00";
        }
        return Timestamp.valueOf(arg);
    }

    public Hit dtoToHit(HitDto dto) {
        Hit hit = new Hit();
        hit.setApp(dto.getApp());
        hit.setUri(dto.getUri());
        hit.setIp(dto.getIp());
        hit.setTimestamp(stringToTimestamp(dto.getTimestamp()));
        return hit;
    }
}
