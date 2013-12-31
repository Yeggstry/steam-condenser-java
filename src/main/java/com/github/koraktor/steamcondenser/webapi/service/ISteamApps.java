/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2010-2013, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.webapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.koraktor.steamcondenser.community.WebApi;
import com.github.koraktor.steamcondenser.exceptions.WebApiException;
import com.github.koraktor.steamcondenser.webapi.WebApiConstants;
import com.github.koraktor.steamcondenser.webapi.apps.ServerAtAddress;
import com.github.koraktor.steamcondenser.webapi.apps.UpToDateCheck;
import com.github.koraktor.steamcondenser.webapi.builder.AppsBuilder;

/**
 * A service class which calls the operations supplied in the ISteamApps Web API service.
 *
 * @author Lewis Keen
 * @author Sebastian Staudt
 */
public class ISteamApps {
    private final AppsBuilder appsBuilder;

    /**
     * Creates a new instance of the ISteamApps Web API service object
     * 
     * @param appsBuilder the apps builder used to create object representations of the operation responses
     */
    public ISteamApps(AppsBuilder appsBuilder) {
        this.appsBuilder = appsBuilder;
    }

    /**
     * Get the list of all apps from Steam.
     * 
     * @return A list of all apps from Steam.
     * @throws WebApiException if there is a general service error (e.g. no key supplied)
     * @throws JSONException if the JSON returned from the service is malformed.
     */
    public Map<Long,String> getAppList() throws WebApiException {
        try {
            JSONObject data = WebApi.getJSONResponse(WebApiConstants.I_STEAM_APPS, WebApiConstants.I_STEAM_APPS_GET_APP_LIST, 2, null);
            return appsBuilder.buildAppList(data);
        } catch(JSONException e) {
            throw new WebApiException(WebApiConstants.ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
    }

    /**
     * Check that an app is up to date.
     * 
     * @param appId The unique Steam Application ID of the game (e.g.
     *        <code>440</code> for Team Fortress 2). See
     *        http://developer.valvesoftware.com/wiki/Steam_Application_IDs for
     *        all application IDs
     * @param versionNumberToCheck The version number to check for the provided app.
     * @return An object representation of the version check for the provided app.
     * @throws WebApiException if there is a general service error (e.g. no key supplied)
     * @throws JSONException if the JSON returned from the service is malformed.
     */
    public UpToDateCheck upToDateCheck(int appId, int versionNumberToCheck) throws WebApiException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(WebApiConstants.APP_ID, Integer.toString(appId));
        params.put(WebApiConstants.REQUEST_PARAM_VERSION, Integer.toString(versionNumberToCheck));

        try {
            JSONObject data = WebApi.getJSONResponse(WebApiConstants.I_STEAM_APPS, WebApiConstants.I_STEAM_APPS_UP_TO_DATE_CHECK, 1, params);
            return appsBuilder.buildUpToDateCheck(appId, versionNumberToCheck, data);
        } catch(JSONException e) {
            throw new WebApiException(WebApiConstants.ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
    }

    /**
     * Get the list of servers hosted in a particular IP address.
     * 
     * @param ip The IP address to query.
     * @return A list of servers at a specified IP address.
     * @throws WebApiException if there is a general service error (e.g. no key supplied)
     * @throws JSONException if the JSON returned from the service is malformed.
     */
    public List<ServerAtAddress> getServersAtAddress(String ip) throws WebApiException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(WebApiConstants.REQUEST_PARAM_ADDR, ip);

        try {
            JSONObject data = WebApi.getJSONResponse(WebApiConstants.I_STEAM_APPS, WebApiConstants.I_STEAM_APPS_GET_SERVERS_AT_ADDRESS, 1, params);
            return appsBuilder.buildServersAtAddress(ip, data);
        } catch(JSONException e) {
            throw new WebApiException(WebApiConstants.ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
    }
}