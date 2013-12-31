package com.github.koraktor.steamcondenser.webapi;

/**
 * <p>A list of supported languages for WebApi operations which accept a language as an optional parameter.</p>
 * <p>Those operations which use the {@link WebApiConstants#REQUEST_PARAM_LANGUAGE} constant accept
 * a language from this enumeration.</p>
 * 
 * <p>Operations which accept a language as an optional parameter:</p>
 * <ul>
 *  <li>{@link com.github.koraktor.steamcondenser.webapi.service.ISteamUserStats#getPlayerAchievements}</li>
 *  <li>{@link com.github.koraktor.steamcondenser.webapi.service.ISteamUserStats#getSchemaForGame}</li>
 * </ul>
 * 
 * <p>The list of supported languages has been compiled from the 
 * <a href="http://translation.steampowered.com/home1st.php">Steam Translation Service</a>.</p>
 * 
 * <p>Note that if there is no translation for a particular string the Web API falls back to english.</p>
 * 
 * @author Lewis Keen
 * @author Sebastian Staudt
 */
public enum WebApiLanguage {
    ENGLISH("english"),
    CZECH("czech"),
    DANISH("danish"),
    DUTCH("dutch"),
    FINNISH("finnish"),
    FRENCH("french"),
    GERMAN("german"),
    HUNGARIAN("hungarian"),
    ITALIAN("italian"),
    JAPANESE("japanese"),
    KOREAN("korean"),
    NORWEGIAN("norwegian"),
    POLISH("polish"),
    PORTUGUESE("portuguese"),
    ROMANIAN("romanian"),
    RUSSIAN("russian"),
    SPANISH("spanish"),
    SWEDISH("swedish"),
    SIMPLIFIED_CHINESE("schinese"),
    TRADITIONAL_CHINESE("tchinese"),
    THAI("thai"),
    BRAZILIAN("brazilian"),
    BULGARIAN("bulgarian"),
    GREEK("greek"),
    TURKISH("turkish"),
    UKRAINIAN("ukrainian"),
    VIETNAMESE("vietnamese");
    
    private final String urlLanguageString;

    /**
     * Constructs an enum element
     * 
     * @param urlLanguageString the string to be used in WebApi URLs to indicate the required language
     */
    WebApiLanguage(String urlLanguageString) {
        this.urlLanguageString = urlLanguageString;
    }

    /**
     * Returns the string to be used in WebApi URLs to indicate the required language
     * 
     * @return the string to be used in WebApi URLs to indicate the required language
     */
    public String getUrlLanguageString() {
        return urlLanguageString;
    }
}
