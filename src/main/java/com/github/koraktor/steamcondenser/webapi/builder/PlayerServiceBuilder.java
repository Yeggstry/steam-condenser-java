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
            int totalCount = responseObject.getInt(WebApiConstants.RESPONSE_ITEM_TOTAL_COUNT);
            JSONArray gamesData = responseObject.getJSONArray(WebApiConstants.RESPONSE_ITEM_GAMES);

            List<OwnedGameWithAppInfo> recentlyPlayedGamesList = buildOwnedGameListWithAppInfo(gamesData);
            return new OwnedGames(totalCount, recentlyPlayedGamesList);
        } catch(JSONException e) {
            throw new ParseException(WebApiConstants.ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
    }

    public OwnedGames buildOwnedGames(JSONObject data) throws ParseException {
        try {
            JSONObject responseObject = data.getJSONObject(WebApiConstants.RESPONSE_ITEM_RESPONSE);
            int gameCount = responseObject.getInt(WebApiConstants.RESPONSE_ITEM_GAME_COUNT);
            JSONArray gamesData = responseObject.getJSONArray(WebApiConstants.RESPONSE_ITEM_GAMES);
            List<OwnedGame> ownedGamesList = new ArrayList<OwnedGame>();
            for(int i = 0; i < gamesData.length(); i ++) {
                JSONObject ownedGameData = gamesData.getJSONObject(i);
                long appId = ownedGameData.getLong(WebApiConstants.APP_ID);
                long playtime2Weeks;
                if(ownedGameData.has(WebApiConstants.RESPONSE_ITEM_PLAYTIME_TWO_WEEKS)) {
                    playtime2Weeks = ownedGameData.getLong(WebApiConstants.RESPONSE_ITEM_PLAYTIME_TWO_WEEKS);
                }else{
                    playtime2Weeks = 0;
                }
                long playtimeForever = ownedGameData.getLong(WebApiConstants.RESPONSE_ITEM_PLAYTIME_FOREVER);

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
            int gameCount = responseObject.getInt(WebApiConstants.RESPONSE_ITEM_GAME_COUNT);
            JSONArray gamesData = responseObject.getJSONArray(WebApiConstants.RESPONSE_ITEM_GAMES);

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
            long appId = ownedGameData.getLong(WebApiConstants.APP_ID);
            String gameName = ownedGameData.getString(WebApiConstants.RESPONSE_ITEM_NAME);
            long playtime2Weeks;
            if(ownedGameData.has(WebApiConstants.RESPONSE_ITEM_PLAYTIME_TWO_WEEKS)) {
                playtime2Weeks = ownedGameData.getLong(WebApiConstants.RESPONSE_ITEM_PLAYTIME_TWO_WEEKS);
            }else{
                playtime2Weeks = 0;
            }
            long playtimeForever = ownedGameData.getLong(WebApiConstants.RESPONSE_ITEM_PLAYTIME_FOREVER);
            String iconUrl = ownedGameData.getString(WebApiConstants.RESPONSE_ITEM_IMG_ICON_URL);
            String logoUrl = ownedGameData.getString(WebApiConstants.RESPONSE_ITEM_IMG_LOGO_URL);

            OwnedGameWithAppInfo ownedGameWithAppInfo = new OwnedGameWithAppInfo(appId, playtime2Weeks, playtimeForever, gameName, iconUrl, logoUrl);
            ownedGamesListWithAppInfo.add(ownedGameWithAppInfo);
        }
        return ownedGamesListWithAppInfo;
    }

    public PlayerBadgeDetails buildBadges(JSONObject data) throws ParseException {
        try {
            JSONObject responseObject = data.getJSONObject(WebApiConstants.RESPONSE_ITEM_RESPONSE);
            int playerLevel = responseObject.getInt(WebApiConstants.RESPONSE_ITEM_PLAYER_LEVEL);
            long playerXp = responseObject.getLong(WebApiConstants.RESPONSE_ITEM_PLAYER_XP);
            long xpNeededToLevelUp = responseObject.getLong(WebApiConstants.RESPONSE_ITEM_PLAYER_XP_NEEDED_TO_LEVEL_UP);
            long xpNeededForCurrentLevel = responseObject.getLong(WebApiConstants.RESPONSE_ITEM_PLAYER_XP_NEEDED_CURRENT_LEVEL);

            PlayerBadgeDetails playerBadgeDetails = new PlayerBadgeDetails(playerXp, playerLevel, xpNeededToLevelUp, xpNeededForCurrentLevel); 

            List<Badge> badges = new ArrayList<Badge>();
            JSONArray badgesData = responseObject.getJSONArray(WebApiConstants.RESPONSE_ITEM_BADGES);
            for(int i = 0; i < badgesData.length(); i ++) {
                JSONObject badgeData = badgesData.getJSONObject(i);

                Badge badge;

                long badgeId = badgeData.getLong(WebApiConstants.BADGE_ID);
                int level = badgeData.getInt(WebApiConstants.RESPONSE_ITEM_BADGE_LEVEL);
                Date completionTime = new java.util.Date(badgeData.getLong(WebApiConstants.RESPONSE_ITEM_BADGE_COMPLETION_TIME)*1000);
                int xp = badgeData.getInt(WebApiConstants.RESPONSE_ITEM_BADGE_XP);
                long scarcity = badgeData.getLong(WebApiConstants.RESPONSE_ITEM_BADGE_SCARCITY);

                if(badgeData.has(WebApiConstants.APP_ID)) {
                    long appId = badgeData.getLong(WebApiConstants.APP_ID);
                    long communityItemId = badgeData.getLong(WebApiConstants.RESPONSE_ITEM_BADGE_COMMUNITY_ITEM_ID);
                    long borderColor = badgeData.getLong(WebApiConstants.RESPONSE_ITEM_BADGE_BORDER_COLOR);

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
            JSONArray badgeProgressData = data.getJSONObject(WebApiConstants.RESPONSE_ITEM_RESPONSE).getJSONArray(WebApiConstants.RESPONSE_ITEM_COMUNITY_BADGE_QUESTS);
            for(int i = 0; i < badgeProgressData.length(); i ++) {
                JSONObject questData = badgeProgressData.getJSONObject(i);
                Long questId = questData.getLong(WebApiConstants.RESPONSE_ITEM_COMUNITY_BADGE_QUEST_ID);
                Boolean completed = questData.getBoolean(WebApiConstants.RESPONSE_ITEM_COMUNITY_BADGE_QUEST_COMPLETED);
                communityBadgeProgress.put(questId, completed);
            }
            return communityBadgeProgress;
        } catch(JSONException e) {
            throw new ParseException(WebApiConstants.ERR_COULD_NOT_PARSE_JSON_DATA, e);
        }
    }
}
