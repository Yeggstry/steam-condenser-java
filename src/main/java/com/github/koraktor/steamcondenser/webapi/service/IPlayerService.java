/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2010-2013, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.webapi.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.koraktor.steamcondenser.community.WebApi;
import com.github.koraktor.steamcondenser.exceptions.WebApiException;
import com.github.koraktor.steamcondenser.webapi.WebApiConstants;
import com.github.koraktor.steamcondenser.webapi.builder.PlayerServiceBuilder;
import com.github.koraktor.steamcondenser.webapi.playerservice.OwnedGames;
import com.github.koraktor.steamcondenser.webapi.playerservice.PlayerBadgeDetails;

/**
 * A service class which calls the operations supplied in the ISteamApps Web API service.
 *
 * @author Lewis Keen
 * @author Sebastian Staudt
 */
public class IPlayerService {
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
		params.put(WebApiConstants.REQUEST_PARAM_STEAM_ID, Long.toString(steamId));
		params.put(WebApiConstants.REQUEST_PARAM_COUNT, Integer.toString(count));

		JSONObject data = WebApi.getJSONResponse(WebApiConstants.I_PLAYER_SERVICE, WebApiConstants.I_PLAYER_SERVICE_GET_RECENTLY_PLAYED_GAMES, 1, params);
		return playerServiceBuilder.buildRecentlyPlayedGames(data);
	}

	public OwnedGames getOwnedGames(long steamId) throws WebApiException, JSONException {
		JSONObject data = callGetOwnedGamesMethod(steamId, false, false);
		return playerServiceBuilder.buildOwnedGames(data);
	}
	
	//support for the "appids_filter" parameters has not been included, 
	public OwnedGames getOwnedGames(long steamId, boolean includeAppInfo, boolean includePlayedFreeGames) throws WebApiException, JSONException {
		JSONObject data = callGetOwnedGamesMethod(steamId, includeAppInfo, includePlayedFreeGames);
		return playerServiceBuilder.buildOwnedGamesWithAppInfo(data);
	}
	
	private JSONObject callGetOwnedGamesMethod(long steamId, boolean includeAppInfo, boolean includePlayedFreeGames) throws WebApiException, JSONException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(WebApiConstants.REQUEST_PARAM_STEAM_ID, Long.toString(steamId));
		params.put(WebApiConstants.REQUEST_PARAM_INCLUDE_APPINFO, new Integer(includeAppInfo ? 1 : 0));
		params.put(WebApiConstants.REQUEST_PARAM_INCLUDE_PLAYED_FREE_GAMES, new Integer(includePlayedFreeGames ? 1 : 0));		

		return WebApi.getJSONResponse(WebApiConstants.I_PLAYER_SERVICE, WebApiConstants.I_PLAYER_SERVICE_GET_OWNED_GAMES, 1, params);
	}
	
	public int getSteamLevel(long steamId) throws WebApiException, JSONException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(WebApiConstants.REQUEST_PARAM_STEAM_ID, Long.toString(steamId));
		JSONObject data = WebApi.getJSONResponse(WebApiConstants.I_PLAYER_SERVICE, WebApiConstants.I_PLAYER_SERVICE_GET_STEAM_LEVEL, 1, params);
		return data.getJSONObject(WebApiConstants.RESPONSE_ITEM_RESPONSE).getInt("player_level");
	}

	public PlayerBadgeDetails getBadges(long steamId) throws WebApiException, JSONException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(WebApiConstants.REQUEST_PARAM_STEAM_ID, Long.toString(steamId));
		JSONObject data = WebApi.getJSONResponse(WebApiConstants.I_PLAYER_SERVICE, WebApiConstants.I_PLAYER_SERVICE_GET_BADGES, 1, params);
		return playerServiceBuilder.buildBadges(data);
	}

	public Map<Long, Boolean> getCommunityBadgeProgress(long steamId) throws WebApiException, JSONException {
		return getCommunityBadgeProgress(steamId, -1);
	}

	public Map<Long, Boolean> getCommunityBadgeProgress(long steamId, long badgeId) throws WebApiException, JSONException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(WebApiConstants.REQUEST_PARAM_STEAM_ID, Long.toString(steamId));
		if(badgeId > -1) {
			params.put(WebApiConstants.REQUEST_PARAM_BADGE_ID, Long.toString(badgeId));
		}

		JSONObject data = WebApi.getJSONResponse(WebApiConstants.I_PLAYER_SERVICE, WebApiConstants.I_PLAYER_SERVICE_GET_COMMUNITY_BADGE_PROGRESS, 1, params);
		return playerServiceBuilder.buildCommunityBadgesProgress(data);
	}
}
