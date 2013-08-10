/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2010-2013, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.steam.webapi.builder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.koraktor.steamcondenser.steam.webapi.exceptions.ParseException;


/**
 * Builder class to create object representations of the IPlayerService Web API responses.
 * 
 * @author Lewis Keen
 * @author Sebastian Staudt
 */
public class PlayerServiceBuilder {
	private static final String ERR_COULD_NOT_PARSE_JSON_DATA = "Could not parse JSON data.";

	public int buildRecentlyPlayedGames(long steamId, JSONObject data) throws ParseException {
        try {
        	JSONArray gamesData = data.getJSONObject("response").getJSONArray("games");
            for(int i = 0; i < gamesData.length(); i ++) {
                JSONObject gameData = gamesData.getJSONObject(i);
            }
    		return 0;
        } catch(JSONException e) {
            throw new ParseException(ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
	}
}
