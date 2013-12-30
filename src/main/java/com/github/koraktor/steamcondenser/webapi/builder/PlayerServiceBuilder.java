/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2010-2013, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.webapi.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.koraktor.steamcondenser.webapi.WebApiConstants;
import com.github.koraktor.steamcondenser.webapi.exceptions.ParseException;
import com.github.koraktor.steamcondenser.webapi.playerservice.AppBadge;
import com.github.koraktor.steamcondenser.webapi.playerservice.Badge;
import com.github.koraktor.steamcondenser.webapi.playerservice.OwnedGame;
import com.github.koraktor.steamcondenser.webapi.playerservice.OwnedGameWithAppInfo;
import com.github.koraktor.steamcondenser.webapi.playerservice.OwnedGames;
import com.github.koraktor.steamcondenser.webapi.playerservice.PlayerBadgeDetails;


/**
 * Builder class to create object representations of the IPlayerService Web API responses.
 * 
 * @author Lewis Keen
 * @author Sebastian Staudt
 */
public class PlayerServiceBuilder {
	public OwnedGames buildRecentlyPlayedGames(JSONObject data) throws ParseException {
        try {
        	JSONObject responseObject = data.getJSONObject(WebApiConstants.RESPONSE_ITEM_RESPONSE);
			int totalCount = responseObject.getInt("total_count");
        	JSONArray gamesData = responseObject.getJSONArray("games");

			List<OwnedGameWithAppInfo> recentlyPlayedGamesList = buildOwnedGameListWithAppInfo(gamesData);
			return new OwnedGames(totalCount, recentlyPlayedGamesList);
        } catch(JSONException e) {
            throw new ParseException(WebApiConstants.ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
	}

	public OwnedGames buildOwnedGames(JSONObject data) throws ParseException {
        try {
        	JSONObject responseObject = data.getJSONObject(WebApiConstants.RESPONSE_ITEM_RESPONSE);
        	int gameCount = responseObject.getInt("game_count");
        	JSONArray gamesData = responseObject.getJSONArray("games");
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
            return new OwnedGames(gameCount, ownedGamesList);
        } catch(JSONException e) {
            throw new ParseException(WebApiConstants.ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
	}

	public OwnedGames buildOwnedGamesWithAppInfo(JSONObject data) throws ParseException {
        try {
        	JSONObject responseObject = data.getJSONObject(WebApiConstants.RESPONSE_ITEM_RESPONSE);
        	int gameCount = responseObject.getInt("game_count");
        	JSONArray gamesData = responseObject.getJSONArray("games");

			List<OwnedGameWithAppInfo> ownedGamesList = buildOwnedGameListWithAppInfo(gamesData);
            return new OwnedGames(gameCount, ownedGamesList);
        } catch(JSONException e) {
            throw new ParseException(WebApiConstants.ERR_COULD_NOT_PARSE_JSON_DATA, e);
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
        	JSONObject responseObject = data.getJSONObject(WebApiConstants.RESPONSE_ITEM_RESPONSE);
        	long playerXp = responseObject.getLong("player_xp");
        	int playerLevel = responseObject.getInt("player_level");
        	long xpNeededToLevelUp = responseObject.getLong("player_xp_needed_to_level_up");
        	long xpNeededForCurrentLevel = responseObject.getLong("player_xp_needed_current_level");
        	
        	PlayerBadgeDetails playerBadgeDetails = new PlayerBadgeDetails(playerXp, playerLevel, xpNeededToLevelUp, xpNeededForCurrentLevel); 
        	
        	List<Badge> badges = new ArrayList<Badge>();
			JSONArray badgesData = responseObject.getJSONArray("badges");
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
            throw new ParseException(WebApiConstants.ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
	}

	public Map<Long, Boolean> buildCommunityBadgesProgress(JSONObject data) throws ParseException {
        try {
        	Map<Long, Boolean> communityBadgeProgress = new HashMap<Long, Boolean>();
        	JSONArray badgeProgressData = data.getJSONObject(WebApiConstants.RESPONSE_ITEM_RESPONSE).getJSONArray("quests");
            for(int i = 0; i < badgeProgressData.length(); i ++) {
                JSONObject questData = badgeProgressData.getJSONObject(i);
                Long questId = questData.getLong("questid");
                Boolean completed = questData.getBoolean("completed");
                communityBadgeProgress.put(questId, completed);
            }
    		return communityBadgeProgress;
        } catch(JSONException e) {
            throw new ParseException(WebApiConstants.ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
	}
}
