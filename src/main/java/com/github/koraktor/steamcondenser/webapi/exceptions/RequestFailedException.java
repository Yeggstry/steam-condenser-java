/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2010-2013, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.webapi.exceptions;

import com.github.koraktor.steamcondenser.exceptions.WebApiException;

/**
 * <p>This exception is raised when there is no positive data in a Steam Web API response.
 * Reasons why this exception can be thrown include:</p>
 * 
 * <ul>
 *  <li>A data error in the call to GetPlayerAchievements e.g. requested app has no stats.</li>
 *  <li>No schema for app in call to GetSchemaForGame</li>
 * </ul>
 * 
 * <p>This exception is designed to provide a more specific {@link WebApiException} so that users
 * of the API will know programmatically what kind of exception has been thrown.</p>
 *
 * @author Lewis Keen
 * @author Sebastian Staudt
 */
public class RequestFailedException extends WebApiException {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new <code>RequestFailedException</code> instance
     *
     * @param message The message to attach to the exception
     */
    public RequestFailedException(String message) {
        super(message);
    }
}
