/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2010-2013, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.steam.webapi.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.koraktor.steamcondenser.steam.community.playerservice.OwnedGameWithAppInfo;
import com.github.koraktor.steamcondenser.steam.community.playerservice.OwnedGames;
import com.github.koraktor.steamcondenser.steam.webapi.exceptions.ParseException;


/**
 * Builder class to create object representations of the IPlayerService Web API responses.
 * 
 * @author Lewis Keen
 * @author Sebastian Staudt
 */
public class PlayerServiceBuilder {
	private static final String ERR_COULD_NOT_PARSE_JSON_DATA = "Could not parse JSON data.";

	public OwnedGames buildRecentlyPlayedGames(JSONObject data) throws ParseException {
        try {
        	int totalCount = data.getJSONObject("response").getInt("total_count");
        	JSONArray gamesData = data.getJSONObject("response").getJSONArray("games");

			List<OwnedGameWithAppInfo> recentlyPlayedGamesList = buildOwnedGameListWithAppInfo(gamesData);
			OwnedGames recentlyPlayedGames = new OwnedGames(totalCount, recentlyPlayedGamesList);
    		return recentlyPlayedGames;
        } catch(JSONException e) {
            throw new ParseException(ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
	}

	private List<OwnedGameWithAppInfo> buildOwnedGameListWithAppInfo(JSONArray gamesData) throws JSONException {
    	List<OwnedGameWithAppInfo> ownedGamesListWithAppInfo = new ArrayList<OwnedGameWithAppInfo>();

    	for(int i = 0; i < gamesData.length(); i ++) {
            JSONObject ownedGameData = gamesData.getJSONObject(i);
            long appId = ownedGameData.getLong("appid");
            String gameName = ownedGameData.getString("name");
            long playtime2Weeks;
            if(ownedGameData.has("playtime_2weeks")) {
            	playtime2Weeks = ownedGameData.getLong("playtime_2weeks");
            }else{
            	playtime2Weeks = 0;
            }
            long playtimeForever = ownedGameData.getLong("playtime_forever");
            String iconUrl = ownedGameData.getString("img_icon_url");
            String logoUrl = ownedGameData.getString("img_logo_url");
            
            OwnedGameWithAppInfo ownedGameWithAppInfo = new OwnedGameWithAppInfo(appId, playtime2Weeks, playtimeForever, gameName, iconUrl, logoUrl);
            ownedGamesListWithAppInfo.add(ownedGameWithAppInfo);
        }
		return ownedGamesListWithAppInfo;
	}
}
