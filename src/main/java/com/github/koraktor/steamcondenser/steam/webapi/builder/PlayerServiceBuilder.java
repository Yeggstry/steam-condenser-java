/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2010-2013, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.steam.webapi.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.koraktor.steamcondenser.steam.community.playerservice.AppBadge;
import com.github.koraktor.steamcondenser.steam.community.playerservice.Badge;
import com.github.koraktor.steamcondenser.steam.community.playerservice.OwnedGame;
import com.github.koraktor.steamcondenser.steam.community.playerservice.OwnedGameWithAppInfo;
import com.github.koraktor.steamcondenser.steam.community.playerservice.OwnedGames;
import com.github.koraktor.steamcondenser.steam.community.playerservice.PlayerBadgeDetails;
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

	public OwnedGames buildOwnedGames(JSONObject data) throws ParseException {
        try {
        	int gameCount = data.getJSONObject("response").getInt("game_count");
        	JSONArray gamesData = data.getJSONObject("response").getJSONArray("games");
        	List<OwnedGame> ownedGamesList = new ArrayList<OwnedGame>();
            for(int i = 0; i < gamesData.length(); i ++) {
                JSONObject ownedGameData = gamesData.getJSONObject(i);
                long appId = ownedGameData.getLong("appid");
                long playtime2Weeks;
                if(ownedGameData.has("playtime_2weeks")) {
                	playtime2Weeks = ownedGameData.getLong("playtime_2weeks");
                }else{
                	playtime2Weeks = 0;
                }
                long playtimeForever = ownedGameData.getLong("playtime_forever");
                
                OwnedGame playerOwnedGame = new OwnedGame(appId, playtime2Weeks, playtimeForever);
                ownedGamesList.add(playerOwnedGame);
            }
            OwnedGames playerOwnedGames = new OwnedGames(gameCount, ownedGamesList);
    		return playerOwnedGames;
        } catch(JSONException e) {
            throw new ParseException(ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
	}

	public OwnedGames buildOwnedGamesWithAppInfo(JSONObject data) throws ParseException {
        try {
        	int gameCount = data.getJSONObject("response").getInt("game_count");
        	JSONArray gamesData = data.getJSONObject("response").getJSONArray("games");

			List<OwnedGameWithAppInfo> ownedGamesList = buildOwnedGameListWithAppInfo(gamesData);
            OwnedGames ownedGames = new OwnedGames(gameCount, ownedGamesList);
    		return ownedGames;
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

	public PlayerBadgeDetails buildBadges(JSONObject data) throws ParseException {
        try {
        	JSONObject response = data.getJSONObject("response");
        	long playerXp = response.getLong("player_xp");
        	int playerLevel = response.getInt("player_level");
        	long xpNeededToLevelUp = response.getLong("player_xp_needed_to_level_up");
        	long xpNeededForCurrentLevel = response.getLong("player_xp_needed_current_level");
        	
        	PlayerBadgeDetails playerBadgeDetails = new PlayerBadgeDetails(playerXp, playerLevel, xpNeededToLevelUp, xpNeededForCurrentLevel); 
        	
        	List<Badge> badges = new ArrayList<Badge>();
			JSONArray badgesData = response.getJSONArray("badges");
            for(int i = 0; i < badgesData.length(); i ++) {
                JSONObject badgeData = badgesData.getJSONObject(i);
                
                Badge badge;
                
                long badgeId = badgeData.getLong("badgeid");
                int level = badgeData.getInt("level");
                Date completionTime = new java.util.Date(badgeData.getLong("completion_time")*1000);
                int xp = badgeData.getInt("xp");
                long scarcity = badgeData.getLong("scarcity");
                
                if(badgeData.has("appid")) {
                    long appId = badgeData.getLong("appid");
                    long communityItemId = badgeData.getLong("communityitemid");
                    long borderColor = badgeData.getLong("border_color");
                	
                    badge = new AppBadge(badgeId, level, completionTime, xp, scarcity, appId, communityItemId, borderColor);
                }else{
                    badge = new Badge(badgeId, level, completionTime, xp, scarcity);
                }
                
   				badges.add(badge);
            }
            
            playerBadgeDetails.setBadges(badges);
    		return playerBadgeDetails;
        } catch(JSONException e) {
            throw new ParseException(ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
	}

	public Map<Long, Boolean> buildCommunityBadgesProgress(JSONObject data) throws ParseException {
        try {
        	Map<Long, Boolean> communityBadgeProgress = new HashMap<Long, Boolean>();
        	JSONArray badgeProgressData = data.getJSONObject("response").getJSONArray("quests");
            for(int i = 0; i < badgeProgressData.length(); i ++) {
                JSONObject questData = badgeProgressData.getJSONObject(i);
                Long questId = questData.getLong("questid");
                Boolean completed = questData.getBoolean("completed");
                communityBadgeProgress.put(questId, completed);
            }
    		return communityBadgeProgress;
        } catch(JSONException e) {
            throw new ParseException(ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
	}
}
