package com.w2m.spaceShips.application.services;

import org.springframework.context.ApplicationContext;

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

}
