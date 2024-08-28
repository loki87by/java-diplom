package ewm.main_service;

import ewm.Errors.ValidationException;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class Utils {

    private boolean isStringLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Long idValidation(Object id) {

        if (id instanceof Long || isStringLong(id.toString())) {
            return Long.parseLong(id.toString());
        } else {
            String valueType = id.getClass().getSimpleName();
            throw new ValidationException("В качестве id вы ввели аргумент типа '" +
                    valueType +
                    "', вместо ожидаемого 'Long'");
        }
    }

    public List<Long> massValidate(List<Object> ids) {
        List<Long> longIds = null;

        if (ids != null && !ids.isEmpty()) {
            longIds = new ArrayList<>();

            for (Object id : ids) {
                Long uId = idValidation(id);
                longIds.add(uId);
            }
        }
        return longIds;
    }

    public Timestamp stringToTimestamp(String arg, boolean toNow) {
        Timestamp ts = null;

        if (arg != null) {

            if (arg.length() <= 11) {

                if (toNow) {
                    arg = arg.trim() + " 00:00:00";
                } else {
                    arg = arg.trim() + " 23:59:59";
                }
            }
            ts = Timestamp.valueOf(arg);
        } else {

            if (toNow) {
                ts = Timestamp.from(Instant.now());
            }
        }
        return ts;
    }
}
