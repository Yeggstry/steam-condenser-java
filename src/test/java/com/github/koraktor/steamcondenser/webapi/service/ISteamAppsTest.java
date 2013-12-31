/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2012, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.webapi.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.koraktor.steamcondenser.community.WebApi;
import com.github.koraktor.steamcondenser.exceptions.WebApiException;
import com.github.koraktor.steamcondenser.webapi.WebApiConstants;
import com.github.koraktor.steamcondenser.webapi.apps.ServerAtAddress;
import com.github.koraktor.steamcondenser.webapi.apps.UpToDateCheck;
import com.github.koraktor.steamcondenser.webapi.builder.AppsBuilder;
import com.github.koraktor.steamcondenser.webapi.exceptions.ParseException;
import com.github.koraktor.steamcondenser.webapi.service.ISteamApps;

/**
 * @author Lewis Keen
 * @author Sebastian Staudt
 */
@PrepareForTest(WebApi.class)
@RunWith(PowerMockRunner.class)
public class ISteamAppsTest {
    private static final String TEST_IP = "85.236.100.104";
    private ISteamApps iSteamApps;
    private AppsBuilder appsBuilder;

    @Before
    public void setup() {
        appsBuilder = mock(AppsBuilder.class);
        iSteamApps = new ISteamApps(appsBuilder);
        mockStatic(WebApi.class);
    }

    @Test
    public void testGetAppList() throws JSONException, WebApiException, ParseException  {
        JSONObject appListDocument = new JSONObject("{ \"object\" : \"mockJSONObject\"}");

        when(WebApi.getJSONResponse(WebApiConstants.I_STEAM_APPS, WebApiConstants.I_STEAM_APPS_GET_APP_LIST, 2, null)).thenReturn(appListDocument);

        Map<Long, String> appList = new TreeMap<Long, String>();
        when(appsBuilder.buildAppList(appListDocument)).thenReturn(appList);

        iSteamApps.getAppList();

        verify(appsBuilder).buildAppList(appListDocument);
    }

    @Test
    public void testGetAppListJSONException() throws WebApiException, JSONException {
        when(WebApi.getJSONResponse(WebApiConstants.I_STEAM_APPS, WebApiConstants.I_STEAM_APPS_GET_APP_LIST, 2, null)).thenThrow(new JSONException("Error with supplied JSON."));

        try {
            iSteamApps.getAppList();
        }catch(WebApiException ex) {
            assertEquals("Could not parse JSON data.", ex.getMessage());
            assertEquals("Error with supplied JSON.", ex.getCause().getMessage());
        }
    }

    @Test
    public void testUpToDateCheck() throws JSONException, WebApiException, ParseException  {
        JSONObject upToDateCheckDocument = new JSONObject("{ \"object\" : \"mockJSONObject\"}");

        Map<String, Object> params = new HashMap<String, Object>(); 
        params.put("appid", Integer.toString(440));
        params.put("version", Integer.toString(1253));
        when(WebApi.getJSONResponse(WebApiConstants.I_STEAM_APPS, WebApiConstants.I_STEAM_APPS_UP_TO_DATE_CHECK, 1, params)).thenReturn(upToDateCheckDocument);

        UpToDateCheck upToDateCheck = mock(UpToDateCheck.class);
        when(appsBuilder.buildUpToDateCheck(440, 1253, upToDateCheckDocument)).thenReturn(upToDateCheck);

        iSteamApps.upToDateCheck(440, 1253);

        verify(appsBuilder).buildUpToDateCheck(440, 1253, upToDateCheckDocument);
    }

    @Test
    public void testUpToDateCheckJSONException() throws JSONException, WebApiException, ParseException  {
        Map<String, Object> params = new HashMap<String, Object>(); 
        params.put("appid", Integer.toString(440));
        params.put("version", Integer.toString(1253));
        when(WebApi.getJSONResponse(WebApiConstants.I_STEAM_APPS, WebApiConstants.I_STEAM_APPS_UP_TO_DATE_CHECK, 1, params)).thenThrow(new JSONException("Error with supplied JSON."));

        try {
            iSteamApps.upToDateCheck(440, 1253);
        }catch(WebApiException ex) {
            assertEquals("Could not parse JSON data.", ex.getMessage());
            assertEquals("Error with supplied JSON.", ex.getCause().getMessage());
        }
    }

    @Test
    public void testGetServersAtAddress() throws JSONException, WebApiException, ParseException  {
        JSONObject getServersAtAddressDocument = new JSONObject("{ \"object\" : \"mockJSONObject\"}");

        Map<String, Object> params = new HashMap<String, Object>(); 
        params.put("addr", TEST_IP);
        when(WebApi.getJSONResponse(WebApiConstants.I_STEAM_APPS, WebApiConstants.I_STEAM_APPS_GET_SERVERS_AT_ADDRESS, 1, params)).thenReturn(getServersAtAddressDocument);

        List<ServerAtAddress> serversAtAddress = new ArrayList<ServerAtAddress>();
        when(appsBuilder.buildServersAtAddress(TEST_IP, getServersAtAddressDocument)).thenReturn(serversAtAddress);

        iSteamApps.getServersAtAddress(TEST_IP);

        verify(appsBuilder).buildServersAtAddress(TEST_IP, getServersAtAddressDocument);
    }

    @Test
    public void testGetServersAtAddressJSONException() throws JSONException, WebApiException, ParseException  {
        Map<String, Object> params = new HashMap<String, Object>(); 
        params.put("addr", TEST_IP);
        when(WebApi.getJSONResponse(WebApiConstants.I_STEAM_APPS, WebApiConstants.I_STEAM_APPS_GET_SERVERS_AT_ADDRESS, 1, params)).thenThrow(new JSONException("Error with supplied JSON."));

        try {
            iSteamApps.getServersAtAddress(TEST_IP);
        }catch(WebApiException ex) {
            assertEquals("Could not parse JSON data.", ex.getMessage());
            assertEquals("Error with supplied JSON.", ex.getCause().getMessage());
        }
    }
}