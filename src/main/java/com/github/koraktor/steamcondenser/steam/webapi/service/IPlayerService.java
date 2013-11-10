/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2010-2013, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.steam.webapi.service;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.koraktor.steamcondenser.exceptions.WebApiException;
import com.github.koraktor.steamcondenser.steam.community.WebApi;
import com.github.koraktor.steamcondenser.steam.community.playerservice.OwnedGames;
import com.github.koraktor.steamcondenser.steam.webapi.builder.PlayerServiceBuilder;

/**
 * A service class which calls the operations supplied in the ISteamApps Web API service.
 *
 * @author Lewis Keen
 * @author Sebastian Staudt
 */
public class IPlayerService {
	private static final String I_PLAYER_SERVICE = "IPlayerService";

	private final PlayerServiceBuilder playerServiceBuilder;
	
	/**
	 * Creates a new instance of the IPlayerService Web API service object
	 * 
	 * @param playerServiceBuilder the player service builder used to create object representations of the operation responses
	 */
	public IPlayerService(PlayerServiceBuilder playerServiceBuilder) {
		this.playerServiceBuilder = playerServiceBuilder;
	}
	
	public OwnedGames getRecentlyPlayedGames(long steamId) throws WebApiException, JSONException {
		return getRecentlyPlayedGames(steamId, 0);
	}

	public OwnedGames getRecentlyPlayedGames(long steamId, int count) throws WebApiException, JSONException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("steamid", Long.toString(steamId));
		params.put("count", Integer.toString(count));

		JSONObject data = WebApi.getJSONResponse(I_PLAYER_SERVICE, "GetRecentlyPlayedGames", 1, params);
		return playerServiceBuilder.buildRecentlyPlayedGames(data);
	}
}
