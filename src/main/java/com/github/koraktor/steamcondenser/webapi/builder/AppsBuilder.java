/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2010-2013, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.webapi.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.koraktor.steamcondenser.exceptions.WebApiException;
import com.github.koraktor.steamcondenser.webapi.WebApiConstants;
import com.github.koraktor.steamcondenser.webapi.apps.ServerAtAddress;
import com.github.koraktor.steamcondenser.webapi.apps.UpToDateCheck;
import com.github.koraktor.steamcondenser.webapi.exceptions.ParseException;
import com.github.koraktor.steamcondenser.webapi.exceptions.RequestFailedException;

/**
 * Builder class to create object representations of the ISteamApps Web API responses.
 * 
 * @author Lewis Keen
 * @author Sebastian Staudt
 */
public class AppsBuilder {
    /**
     * Build a map of apps available in Steam, with the key as the appId and the value as the name of the app
     * 
     * @param data The response from the GetAppList request, in JSON form.
     * @return a map of apps, with the key as the appId and the value as the name of the app
     * @throws ParseException if the JSON cannot be parsed as expected.
     */
    public Map<Long, String> buildAppList(JSONObject data) throws ParseException {
        try {
            Map<Long, String> appList = new TreeMap<Long, String>();
            JSONArray appsData = data.getJSONObject("applist").getJSONArray("apps");
            for(int i = 0; i < appsData.length(); i ++) {
                JSONObject achievementData = appsData.getJSONObject(i);
                appList.put(achievementData.getLong("appid"), achievementData.getString(WebApiConstants.RESPONSE_ITEM_NAME));
            }
            return appList;
        } catch(JSONException e) {
            throw new ParseException(WebApiConstants.ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
    }

    /**
     * Build an object representation of the version check for a certain app.
     * 
     * @param appId The unique Steam Application ID of the game (e.g.
     *        <code>440</code> for Team Fortress 2). See
     *        http://developer.valvesoftware.com/wiki/Steam_Application_IDs for
     *        all application IDs
     * @param versionNumberToCheck the version number to check for this app.
     * @param data The response from the UpToDateCheck request, in JSON form.
     * @return An object representation of the version check for a certain app.
     * @throws ParseException if the JSON cannot be parsed as expected.
     */
    public UpToDateCheck buildUpToDateCheck(int appId, int versionNumberToCheck, JSONObject data) throws ParseException {
        try {
            boolean upToDate, versionIsListable;
            Integer requiredVersion = null;
            String message = null;

            JSONObject response = data.getJSONObject(WebApiConstants.RESPONSE_ITEM_RESPONSE);
            upToDate = response.getBoolean("up_to_date");
            versionIsListable = response.getBoolean("version_is_listable");
            if(response.has("required_version")) {
                requiredVersion = response.getInt("required_version");
            }
            if(response.has("required_version")) {
                message = response.getString("message");
            }
            return new UpToDateCheck(appId, upToDate, versionIsListable, requiredVersion, message);
        } catch(JSONException e) {
            throw new ParseException(WebApiConstants.ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
    }

    /**
     * Build a list of servers at a specified IP address.
     * 
     * @param ip the requested IP address.
     * @param data The response from the GetServersAtAddress request, in JSON form.
     * @return A list of servers at a specified IP address.
     * @throws RequestFailedException if an error has been returned in the response. In this case, the "success" value in the response is false.
     * @throws ParseException if the JSON cannot be parsed as expected.
     */
    public List<ServerAtAddress> buildServersAtAddress(String ip, JSONObject data) throws WebApiException {
        try {
            JSONObject response = data.getJSONObject(WebApiConstants.RESPONSE_ITEM_RESPONSE);
            if(!response.getBoolean("success")) {
                throw new RequestFailedException(String.format("Invalid IP address: %s",ip));
            }

            List<ServerAtAddress> serversAtAddress = new ArrayList<ServerAtAddress>();
            JSONArray servers = response.getJSONArray("servers");
            for (int i = 0; i < servers.length(); i++) {
                JSONObject server = servers.getJSONObject(i);

                String address = server.getString("addr");
                int gmsIndex = server.getInt("gmsindex");
                int appId = server.getInt("appid");
                String gameDir = server.getString("gamedir");
                int region = server.getInt("region");
                boolean secure = server.getBoolean("secure");
                boolean lan = server.getBoolean("lan");
                int gamePort = server.getInt("gameport");
                int specPort = server.getInt("specport");

                ServerAtAddress serverAtAddress = new ServerAtAddress(address, gmsIndex, appId, gameDir, region, secure, lan, gamePort, specPort);
                serversAtAddress.add(serverAtAddress);
            }
            return serversAtAddress;
        } catch(JSONException e) {
            throw new ParseException(WebApiConstants.ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
    }
}
