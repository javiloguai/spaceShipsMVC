package com.w2m.spaceShips.application.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Objects;

/**
 * @author javiloguai
 * <p>
 * Useful if we want to add some standard validation
 */
public class BasicService {

    private final ApplicationContext applicationContext;

    public BasicService(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Checks if an Object have a at least minimum fields with value
     *
     * @param object        The object to validate
     * @param minimumFields The minimum fields
     * @return the validation result
     */
    public boolean haveMinimumFields(final Object object, final Integer minimumFields) {
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> map = oMapper.convertValue(object, Map.class);
        map.values().removeIf(Objects::isNull);
        return map.size() >= minimumFields;
    }

}
