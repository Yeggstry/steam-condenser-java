package com.github.koraktor.steamcondenser.webapi;

public class WebApiConstants {
	/*
	 * Constants for interfaces
	 */
	public static final String I_PLAYER_SERVICE = "IPlayerService";
	public static final String I_STEAM_APPS = "ISteamApps";
	public static final String I_STEAM_USER_STATS = "ISteamUserStats";
	public static final String I_WEB_API_UTIL = "ISteamWebAPIUtil";

	/*
	 * Constants for operations
	 */
	public static final String I_PLAYER_SERVICE_GET_RECENTLY_PLAYED_GAMES = "GetRecentlyPlayedGames";
	public static final String I_PLAYER_SERVICE_GET_OWNED_GAMES = "GetOwnedGames";
	public static final String I_PLAYER_SERVICE_GET_STEAM_LEVEL = "GetSteamLevel";
	public static final String I_PLAYER_SERVICE_GET_BADGES = "GetBadges";
	public static final String I_PLAYER_SERVICE_GET_COMMUNITY_BADGE_PROGRESS = "GetCommunityBadgeProgress";

	public static final String I_STEAM_APPS_GET_SERVERS_AT_ADDRESS = "GetServersAtAddress";
	public static final String I_STEAM_APPS_UP_TO_DATE_CHECK = "UpToDateCheck";
	public static final String I_STEAM_APPS_GET_APP_LIST = "GetAppList";
	
	public static final String I_STEAM_USER_STATS_GET_USER_STATS_FOR_GAME = "GetUserStatsForGame";
	public static final String I_STEAM_USER_STATS_GET_SCHEMA_FOR_GAME = "GetSchemaForGame";
	public static final String I_STEAM_USER_STATS_GET_PLAYER_ACHIEVEMENTS = "GetPlayerAchievements";
	public static final String I_STEAM_USER_STATS_GET_NUMBER_OF_CURRENT_PLAYERS = "GetNumberOfCurrentPlayers";
	public static final String I_STEAM_USER_STATS_GET_GLOBAL_ACHIEVEMENT_PERCENTAGES_FOR_APP = "GetGlobalAchievementPercentagesForApp";

	public static final String I_WEB_API_UTIL_GET_SERVER_INFO = "GetServerInfo";
	public static final String I_WEB_API_UTIL_GET_SUPPORTED_API_LIST = "GetSupportedAPIList";
	
	/*
	 * Constants for request parameters
	 */
	public static final String REQUEST_PARAM_APPID = "appid";
	public static final String REQUEST_PARAM_STEAM_ID = "steamid";
	public static final String REQUEST_PARAM_COUNT = "count";
	public static final String REQUEST_PARAM_BADGE_ID = "badgeid";
	public static final String REQUEST_PARAM_INCLUDE_APPINFO = "include_appinfo";
	public static final String REQUEST_PARAM_INCLUDE_PLAYED_FREE_GAMES = "include_played_free_games";
	public static final String REQUEST_PARAM_VERSION = "version";
	public static final String REQUEST_PARAM_ADDR = "addr";
	public static final String REQUEST_PARAM_GAME_ID = "gameId";
	public static final String REQUEST_PARAM_LANGUAGE = "l";
	
	/*
	 * Constants for response parameters
	 */
	public static final String RESPONSE_ITEM_RESPONSE = "response";
	public static final String RESPONSE_ITEM_STATS = "stats";
	public static final String RESPONSE_ITEM_GAME_NAME = "gameName";
	public static final String RESPONSE_ITEM_NAME = "name";
	public static final String RESPONSE_ITEM_ACHIEVEMENTS = "achievements";
	public static final String RESPONSE_ITEM_PLAYER_COUNT = "player_count";
	
	/*
	 * Constants for errors
	 */
	public static final String ERR_COULD_NOT_PARSE_JSON_DATA = "Could not parse JSON data.";
}
