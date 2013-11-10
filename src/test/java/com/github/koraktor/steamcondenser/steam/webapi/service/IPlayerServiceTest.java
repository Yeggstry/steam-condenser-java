/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2012, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.steam.webapi.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.koraktor.steamcondenser.exceptions.WebApiException;
import com.github.koraktor.steamcondenser.steam.community.WebApi;
import com.github.koraktor.steamcondenser.steam.community.playerservice.OwnedGames;
import com.github.koraktor.steamcondenser.steam.webapi.builder.PlayerServiceBuilder;

/**
 * @author Lewis Keen
 * @author Sebastian Staudt
 */
@PrepareForTest(WebApi.class)
@RunWith(PowerMockRunner.class)
public class IPlayerServiceTest {
	private static final String I_PLAYER_SERVICE = "IPlayerService";
	private static final long STEAM_ID = 12345;
	private IPlayerService iplayerService;
	private PlayerServiceBuilder playerServiceBuilder;
	
	@Before
	public void setup() {
        playerServiceBuilder = mock(PlayerServiceBuilder.class);
		iplayerService = new IPlayerService(playerServiceBuilder);
        mockStatic(WebApi.class);
	}

	/* Tests for GetRecentlyPlayedGames */
	@Test
	public void testGetRecentlyPlayedGames() throws WebApiException, JSONException, IOException {
		JSONObject recentlyPlayedGamesDocument = new JSONObject("{ \"object\" : \"mockJSONObject\"}");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("steamid", Long.toString(STEAM_ID));
		params.put("count", Integer.toString(9));

		when(WebApi.getJSONResponse(I_PLAYER_SERVICE, "GetRecentlyPlayedGames", 1, params)).thenReturn(recentlyPlayedGamesDocument);

		OwnedGames recentlyPlayedGames = mock(OwnedGames.class);
		when(playerServiceBuilder.buildRecentlyPlayedGames(recentlyPlayedGamesDocument)).thenReturn(recentlyPlayedGames);

		iplayerService.getRecentlyPlayedGames(STEAM_ID, 9);
		
		verify(playerServiceBuilder).buildRecentlyPlayedGames(recentlyPlayedGamesDocument);
	}

	@Test
	public void testGetRecentlyPlayedGamesNoCount() throws WebApiException, JSONException, IOException {
		JSONObject recentlyPlayedGamesDocument = new JSONObject("{ \"object\" : \"mockJSONObject\"}");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("steamid", Long.toString(STEAM_ID));
		params.put("count", Integer.toString(0));

		when(WebApi.getJSONResponse(I_PLAYER_SERVICE, "GetRecentlyPlayedGames", 1, params)).thenReturn(recentlyPlayedGamesDocument);

		OwnedGames recentlyPlayedGames = mock(OwnedGames.class);
		when(playerServiceBuilder.buildRecentlyPlayedGames(recentlyPlayedGamesDocument)).thenReturn(recentlyPlayedGames);

		iplayerService.getRecentlyPlayedGames(STEAM_ID);
		
		verify(playerServiceBuilder).buildRecentlyPlayedGames(recentlyPlayedGamesDocument);
	}
}
