/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2012, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.webapi.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.github.koraktor.steamcondenser.webapi.builder.PlayerServiceBuilder;
import com.github.koraktor.steamcondenser.webapi.exceptions.ParseException;
import com.github.koraktor.steamcondenser.webapi.playerservice.AppBadge;
import com.github.koraktor.steamcondenser.webapi.playerservice.Badge;
import com.github.koraktor.steamcondenser.webapi.playerservice.OwnedGame;
import com.github.koraktor.steamcondenser.webapi.playerservice.OwnedGameWithAppInfo;
import com.github.koraktor.steamcondenser.webapi.playerservice.OwnedGames;
import com.github.koraktor.steamcondenser.webapi.playerservice.PlayerBadgeDetails;

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
	
	@Test
	public void testOwnedGames() throws JSONException, IOException, ParseException {
		JSONObject ownedGamesDocument = new JSONObject(loadFileAsString("IPlayerService/GetOwnedGames.v1.json"));
		
		OwnedGames ownedGames = playerServiceBuilder.buildOwnedGames(ownedGamesDocument);
		
		assertEquals(118, ownedGames.getGameCount());

		assertFalse(ownedGames.doesPlayerOwnedGamesIncludeAppInfo());
		List<OwnedGame> ownedGamesList = ownedGames.getPlayerOwnedGames();
		assertEquals(118, ownedGamesList.size());
		
		OwnedGame ownedGamedNotPlayedWithinLastTwoWeeks = ownedGamesList.get(0);

		assertEquals(220, ownedGamedNotPlayedWithinLastTwoWeeks.getAppId());
		assertEquals(0, ownedGamedNotPlayedWithinLastTwoWeeks.getPlaytime2Weeks());
		assertEquals(139, ownedGamedNotPlayedWithinLastTwoWeeks.getPlaytimeForever());
		
		OwnedGame ownedGamedPlayedWithinLastTwoWeeks = ownedGamesList.get(51);
		assertEquals(31280, ownedGamedPlayedWithinLastTwoWeeks.getAppId());
		assertEquals(46, ownedGamedPlayedWithinLastTwoWeeks.getPlaytime2Weeks());
		assertEquals(2310, ownedGamedPlayedWithinLastTwoWeeks.getPlaytimeForever());
	}

	@Test
	public void testOwnedGamesInvalidJSON() throws JSONException {
		JSONObject recentlyPlayedGamesDocument = new JSONObject("{ }");

		try {
			playerServiceBuilder.buildOwnedGames(recentlyPlayedGamesDocument);
			fail("Exception should be thrown when calling build owned games with invalid JSON.");
		} catch (Exception e) {
			assertEquals("Could not parse JSON data.", e.getMessage());
		}
	}

	@Test
	public void testOwnedGamesWithAppInfo() throws JSONException, IOException, ParseException {
		JSONObject ownedGamesDocument = new JSONObject(loadFileAsString("IPlayerService/GetOwnedGames.include_app_info.v1.json"));
		
		OwnedGames ownedGames = playerServiceBuilder.buildOwnedGamesWithAppInfo(ownedGamesDocument);
		
		assertEquals(123, ownedGames.getGameCount());

		assertTrue(ownedGames.doesPlayerOwnedGamesIncludeAppInfo());
		List<OwnedGameWithAppInfo> ownedGamesList = ownedGames.getPlayerOwnedGamesWithAppInfo();
		assertEquals(123, ownedGamesList.size());
		
		OwnedGameWithAppInfo ownedGamedNotPlayedWithinLastTwoWeeks = ownedGamesList.get(0);

		assertEquals(220, ownedGamedNotPlayedWithinLastTwoWeeks.getAppId());
		assertEquals(0, ownedGamedNotPlayedWithinLastTwoWeeks.getPlaytime2Weeks());
		assertEquals(139, ownedGamedNotPlayedWithinLastTwoWeeks.getPlaytimeForever());
		assertEquals("Half-Life 2", ownedGamedNotPlayedWithinLastTwoWeeks.getName());
		assertEquals("fcfb366051782b8ebf2aa297f3b746395858cb62", ownedGamedNotPlayedWithinLastTwoWeeks.getIconUrl());
		assertEquals("e4ad9cf1b7dc8475c1118625daf9abd4bdcbcad0", ownedGamedNotPlayedWithinLastTwoWeeks.getLogoUrl());

		
		OwnedGameWithAppInfo ownedGamedPlayedWithinLastTwoWeeks = ownedGamesList.get(51);
		assertEquals(31280, ownedGamedPlayedWithinLastTwoWeeks.getAppId());
		assertEquals(46, ownedGamedPlayedWithinLastTwoWeeks.getPlaytime2Weeks());
		assertEquals(2310, ownedGamedPlayedWithinLastTwoWeeks.getPlaytimeForever());
		assertEquals("Poker Night at the Inventory", ownedGamedPlayedWithinLastTwoWeeks.getName());
		assertEquals("7d50bd1f5e7cfe68397e9ca0041836ad18153dfb", ownedGamedPlayedWithinLastTwoWeeks.getIconUrl());
		assertEquals("d962cde096bca06ee10d09880e9f3d6257941161", ownedGamedPlayedWithinLastTwoWeeks.getLogoUrl());
	}

	@Test
	public void testOwnedGamesWithAppInfoInvalidJSON() throws JSONException {
		JSONObject recentlyPlayedGamesDocument = new JSONObject("{ }");

		try {
			playerServiceBuilder.buildOwnedGamesWithAppInfo(recentlyPlayedGamesDocument);
			fail("Exception should be thrown when calling build owned games with invalid JSON.");
		} catch (Exception e) {
			assertEquals("Could not parse JSON data.", e.getMessage());
		}
	}
	
	@Test
	public void testBuildBadges() throws JSONException, IOException, ParseException {
		JSONObject badgesDocument = new JSONObject(loadFileAsString("IPlayerService/GetBadges.v1.json"));
		
		PlayerBadgeDetails playerBadgeDetails = playerServiceBuilder.buildBadges(badgesDocument);
		
		assertEquals(1628, playerBadgeDetails.getCurrentXp());
		assertEquals(13, playerBadgeDetails.getLevel());
		assertEquals(172, playerBadgeDetails.getXpNeededToLevelUp());
		assertEquals(1600, playerBadgeDetails.getXpNeededForCurrentLevel());
		
		assertEquals(11, playerBadgeDetails.getBadges().size());
		Badge generalBadge = playerBadgeDetails.getBadges().get(2);
		assertEquals(8, generalBadge.getBadgeId());
		assertEquals(1, generalBadge.getLevel());
		assertEquals(new Date(1356340708000L), generalBadge.getCompletionTime());
		assertEquals(100, generalBadge.getXp());
		assertEquals(741879, generalBadge.getScarcity());

		AppBadge badgeWithAppDetails = (AppBadge) playerBadgeDetails.getBadges().get(10);
		assertEquals(245070, badgeWithAppDetails.getAppId());
		assertEquals(110365220, badgeWithAppDetails.getCommunityItemId());
		assertEquals(0, badgeWithAppDetails.getBorderColor());
	}
	
	@Test
	public void testBuildBadgesInvalidJSON() throws JSONException {
		JSONObject badgesDocument = new JSONObject("{ }");

		try {
			playerServiceBuilder.buildBadges(badgesDocument);
			fail("Exception should be thrown when calling build community badges progress with invalid JSON.");
		} catch (Exception e) {
			assertEquals("Could not parse JSON data.", e.getMessage());
		}
	}
	
	
	@Test
	public void testBuildCommunityBadgesProgress() throws JSONException, IOException, ParseException {
		JSONObject communityBadgesProgressDocument = new JSONObject(loadFileAsString("IPlayerService/GetCommunityBadgesProgress.v1.json"));
		
		Map<Long, Boolean> communityBadgesProgress = playerServiceBuilder.buildCommunityBadgesProgress(communityBadgesProgressDocument);
		
		assertEquals(26, communityBadgesProgress.size());
		assertTrue(communityBadgesProgress.get(103L));
		assertFalse(communityBadgesProgress.get(106L));
	}
	
	@Test
	public void testBuildCommunityBadgesProgressInvalidJSON() throws JSONException {
		JSONObject communityBadgesProgressDocument = new JSONObject("{ }");

		try {
			playerServiceBuilder.buildCommunityBadgesProgress(communityBadgesProgressDocument);
			fail("Exception should be thrown when calling build community badges progress with invalid JSON.");
		} catch (Exception e) {
			assertEquals("Could not parse JSON data.", e.getMessage());
		}
	}
}
