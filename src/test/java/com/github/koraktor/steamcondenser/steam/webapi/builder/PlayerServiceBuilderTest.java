/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2012, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.steam.webapi.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.github.koraktor.steamcondenser.steam.community.playerservice.OwnedGameWithAppInfo;
import com.github.koraktor.steamcondenser.steam.community.playerservice.OwnedGames;
import com.github.koraktor.steamcondenser.steam.webapi.exceptions.ParseException;

/**
 * @author Lewis Keen
 * @author Sebastian Staudt
 */
public class PlayerServiceBuilderTest {
	private PlayerServiceBuilder playerServiceBuilder;

	@Before
	public void setup() {
		playerServiceBuilder = new PlayerServiceBuilder();
	}

	private String loadFileAsString(String path) throws IOException {
		URL resource = this.getClass().getResource(path);
		File resourceFile = new File(resource.getFile());
		return FileUtils.readFileToString(resourceFile, "UTF-8");
	}

	@Test
	public void testRecentlyPlayedGames() throws JSONException, IOException, ParseException {
		JSONObject recentlyPlayedGamesDocument = new JSONObject(loadFileAsString("IPlayerService/GetRecentlyPlayedGames.v1.json"));
		
		OwnedGames recentlyPlayedGames = playerServiceBuilder.buildRecentlyPlayedGames(recentlyPlayedGamesDocument);
		
		assertEquals(11, recentlyPlayedGames.getGameCount());

		assertTrue(recentlyPlayedGames.doesPlayerOwnedGamesIncludeAppInfo());
		List<OwnedGameWithAppInfo> recentlyPlayedGamesList = recentlyPlayedGames.getPlayerOwnedGamesWithAppInfo();
		assertEquals(9, recentlyPlayedGamesList.size());
		
		OwnedGameWithAppInfo payday2RecentlyPlayedGame = recentlyPlayedGamesList.get(0);

		assertEquals(218620, payday2RecentlyPlayedGame.getAppId());
		assertEquals("PAYDAY 2", payday2RecentlyPlayedGame.getName());
		assertEquals(736, payday2RecentlyPlayedGame.getPlaytime2Weeks());
		assertEquals(5586, payday2RecentlyPlayedGame.getPlaytimeForever());
		assertEquals("a6abc0d0c1e79c0b5b0f5c8ab81ce9076a542414", payday2RecentlyPlayedGame.getIconUrl());
		assertEquals("4467a70648f49a6b309b41b81b4531f9a20ed99d", payday2RecentlyPlayedGame.getLogoUrl());
		
		OwnedGameWithAppInfo l4d2RecentlyPlayedGame = recentlyPlayedGamesList.get(4);
		assertEquals(550, l4d2RecentlyPlayedGame.getAppId());
		assertEquals("Left 4 Dead 2", l4d2RecentlyPlayedGame.getName());
		assertEquals(186, l4d2RecentlyPlayedGame.getPlaytime2Weeks());
		assertEquals(52504, l4d2RecentlyPlayedGame.getPlaytimeForever());
		assertEquals("7d5a243f9500d2f8467312822f8af2a2928777ed", l4d2RecentlyPlayedGame.getIconUrl());
		assertEquals("205863cc21e751a576d6fff851984b3170684142", l4d2RecentlyPlayedGame.getLogoUrl());
	}

	@Test
	public void testRecentlyPlayedGamesInvalidJSON() throws JSONException {
		JSONObject recentlyPlayedGamesDocument = new JSONObject("{ }");

		try {
			playerServiceBuilder.buildRecentlyPlayedGames(recentlyPlayedGamesDocument);
			fail("Exception should be thrown when calling build recently played games with invalid JSON.");
		} catch (Exception e) {
			assertEquals("Could not parse JSON data.", e.getMessage());
		}
	}
}
