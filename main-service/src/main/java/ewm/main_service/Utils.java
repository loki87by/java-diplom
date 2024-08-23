package ewm.main_service;

import ewm.Errors.ValidationException;
import org.springframework.stereotype.Component;

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
            throw new ValidationException("В качестве id вы ввели аргумент типа '" + valueType + "', вместо ожидаемого 'Long'");
        }
    }
}
