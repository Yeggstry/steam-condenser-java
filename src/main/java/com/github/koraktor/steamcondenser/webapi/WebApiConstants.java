package com.github.koraktor.steamcondenser.webapi;

public class WebApiConstants {
    private WebApiConstants() {
    }

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
     * Constants which can be request parameters or response items
     */
    public static final String APP_ID = "appid";
    public static final String BADGE_ID = "badgeid";
    
    /*
     * Constants for request parameters
     */
    public static final String REQUEST_PARAM_STEAM_ID = "steamid";
    public static final String REQUEST_PARAM_COUNT = "count";
    public static final String REQUEST_PARAM_INCLUDE_APPINFO = "include_appinfo";
    public static final String REQUEST_PARAM_INCLUDE_PLAYED_FREE_GAMES = "include_played_free_games";
    public static final String REQUEST_PARAM_VERSION = "version";
    public static final String REQUEST_PARAM_ADDR = "addr";
    public static final String REQUEST_PARAM_GAME_ID = "gameId";
    public static final String REQUEST_PARAM_LANGUAGE = "l";

    /*
     * Constants for response items
     */
    public static final String RESPONSE_ITEM_RESPONSE = "response";
    public static final String RESPONSE_ITEM_SUCCESS = "success";
    public static final String RESPONSE_ITEM_ERROR = "error";
    public static final String RESPONSE_ITEM_API_NAME = "apiname";
    public static final String RESPONSE_ITEM_NAME = "name";
    public static final String RESPONSE_ITEM_VALUE = "value";
    public static final String RESPONSE_ITEM_DEFAULT_VALUE = "defaultvalue";
    public static final String RESPONSE_ITEM_DISPLAY_NAME = "displayName";
    public static final String RESPONSE_ITEM_DESCRIPTION = "description";
    public static final String RESPONSE_ITEM_MESSAGE = "message";
    public static final String RESPONSE_ITEM_ICON = "icon";
    public static final String RESPONSE_ITEM_ICON_GRAY = "icongray";
    public static final String RESPONSE_ITEM_HIDDEN = "hidden";
    public static final String RESPONSE_ITEM_STATS = "stats";
    
    public static final String RESPONSE_ITEM_GAME = "game";
    public static final String RESPONSE_ITEM_GAMES = "games";
    public static final String RESPONSE_ITEM_GAME_NAME = "gameName";
    public static final String RESPONSE_ITEM_GAME_VERSION = "gameVersion";
    public static final String RESPONSE_ITEM_GAME_COUNT = "game_count";
    public static final String RESPONSE_ITEM_TOTAL_COUNT = "total_count";
    public static final String RESPONSE_ITEM_PLAYTIME_TWO_WEEKS = "playtime_2weeks";
    public static final String RESPONSE_ITEM_PLAYTIME_FOREVER = "playtime_forever";
    
    public static final String RESPONSE_ITEM_AVAILABLE_GAME_STATS = "availableGameStats";
    public static final String RESPONSE_ITEM_ACHIEVEMENT_PERCENTAGES = "achievementpercentages";
    public static final String RESPONSE_ITEM_ACHIEVEMENTS = "achievements";
    public static final String RESPONSE_ITEM_ACHIEVED = "achieved";
    public static final String RESPONSE_ITEM_PLAYER_COUNT = "player_count";
    public static final String RESPONSE_ITEM_PLAYER_STATS = "playerstats";

    public static final String RESPONSE_ITEM_IMG_ICON_URL = "img_icon_url";
    public static final String RESPONSE_ITEM_IMG_LOGO_URL = "img_logo_url";
    
    public static final String RESPONSE_ITEM_PLAYER_LEVEL = "player_level";
    public static final String RESPONSE_ITEM_PLAYER_XP = "player_xp";
    public static final String RESPONSE_ITEM_PLAYER_XP_NEEDED_TO_LEVEL_UP = "player_xp_needed_to_level_up";
    public static final String RESPONSE_ITEM_PLAYER_XP_NEEDED_CURRENT_LEVEL = "player_xp_needed_current_level";
    public static final String RESPONSE_ITEM_BADGES = "badges";
    public static final String RESPONSE_ITEM_BADGE_LEVEL = "level";
    public static final String RESPONSE_ITEM_BADGE_COMPLETION_TIME = "completion_time";
    public static final String RESPONSE_ITEM_BADGE_XP = "xp";
    public static final String RESPONSE_ITEM_BADGE_SCARCITY = "scarcity";
    public static final String RESPONSE_ITEM_BADGE_COMMUNITY_ITEM_ID = "communityitemid";
    public static final String RESPONSE_ITEM_BADGE_BORDER_COLOR = "border_color";
    
    public static final String RESPONSE_ITEM_COMUNITY_BADGE_QUESTS = "quests";
    public static final String RESPONSE_ITEM_COMUNITY_BADGE_QUEST_ID = "questid";
    public static final String RESPONSE_ITEM_COMUNITY_BADGE_QUEST_COMPLETED = "completed";
    
    public static final String RESPONSE_ITEM_APP_LIST = "applist";
    public static final String RESPONSE_ITEM_APPS = "apps";

    public static final String RESPONSE_ITEM_UP_TO_DATE = "up_to_date";
    public static final String RESPONSE_ITEM_VERSION_IS_LISTABLE = "version_is_listable";
    public static final String RESPONSE_ITEM_REQUIRED_VERSION = "required_version";
        
    public static final String RESPONSE_ITEM_SERVERS = "servers";
    public static final String RESPONSE_ITEM_SERVER_ADDRESS = "addr";
    public static final String RESPONSE_ITEM_SERVER_GMS_INDEX = "gmsindex";
    public static final String RESPONSE_ITEM_SERVER_GAME_DIR = "gamedir";
    public static final String RESPONSE_ITEM_SERVER_REGION = "region";
    public static final String RESPONSE_ITEM_SERVER_SECURE = "secure";
    public static final String RESPONSE_ITEM_SERVER_LAN = "lan";
    public static final String RESPONSE_ITEM_SERVER_GAME_PORT = "gameport";
    public static final String RESPONSE_ITEM_SERVER_SPECTATOR_PORT = "specport";
    
    /*
     * Constants for errors
     */
    public static final String ERR_COULD_NOT_PARSE_JSON_DATA = "Could not parse JSON data.";
}
