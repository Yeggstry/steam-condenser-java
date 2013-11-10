/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2012, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.steam.webapi.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
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

	private String loadFileAsString(String path) throws IOException {
		URL resource = this.getClass().getResource(path);
		File resourceFile = new File(resource.getFile());
		return FileUtils.readFileToString(resourceFile, "UTF-8");
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
	
	/* Tests for GetOwnedGames */
	@Test
	public void testGetOwnedGames() throws WebApiException, JSONException, IOException {
		JSONObject ownedGamesDocument = new JSONObject("{ \"object\" : \"mockJSONObject\"}");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("steamid", Long.toString(STEAM_ID));
		params.put("include_appinfo", new Integer(0));
		params.put("include_played_free_games", new Integer(0));

		when(WebApi.getJSONResponse(I_PLAYER_SERVICE, "GetOwnedGames", 1, params)).thenReturn(ownedGamesDocument);

		OwnedGames playerOwnedGames = mock(OwnedGames.class);
		when(playerServiceBuilder.buildOwnedGames(ownedGamesDocument)).thenReturn(playerOwnedGames);

		iplayerService.getOwnedGames(STEAM_ID);
		
		verify(playerServiceBuilder).buildOwnedGames(ownedGamesDocument);
	}

	@Test
	public void testGetOwnedGamesWithAppInfo() throws WebApiException, JSONException, IOException {
		JSONObject ownedGamesDocument = new JSONObject("{ \"object\" : \"mockJSONObject\"}");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("steamid", Long.toString(STEAM_ID));
		params.put("include_appinfo", new Integer(1));
		params.put("include_played_free_games", new Integer(1));

		when(WebApi.getJSONResponse(I_PLAYER_SERVICE, "GetOwnedGames", 1, params)).thenReturn(ownedGamesDocument);

		OwnedGames playerOwnedGames = mock(OwnedGames.class);
		when(playerServiceBuilder.buildOwnedGamesWithAppInfo(ownedGamesDocument)).thenReturn(playerOwnedGames);

		iplayerService.getOwnedGames(STEAM_ID, true, true);
		
		verify(playerServiceBuilder).buildOwnedGamesWithAppInfo(ownedGamesDocument);
	}

	/* Tests for GetSteamLevel */
	@Test
	public void testGetSteamLevel() throws WebApiException, JSONException, IOException {
		JSONObject numberOfPlayersDocument = new JSONObject(loadFileAsString("IPlayerService/GetSteamLevel.v1.json"));
		Map<String, Object> params = Collections.<String,Object>singletonMap("steamid", Long.toString(STEAM_ID));

		when(WebApi.getJSONResponse(I_PLAYER_SERVICE, "GetSteamLevel", 1, params)).thenReturn(numberOfPlayersDocument);

		int playerLevel = iplayerService.getSteamLevel(STEAM_ID);
		assertEquals(12, playerLevel);
	}
}
