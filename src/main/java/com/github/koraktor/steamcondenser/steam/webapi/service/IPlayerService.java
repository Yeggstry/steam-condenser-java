/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2010-2013, Sebastian Staudt
 */

package com.github.koraktor.steamcondenser.steam.webapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.koraktor.steamcondenser.exceptions.WebApiException;
import com.github.koraktor.steamcondenser.steam.community.WebApi;
import com.github.koraktor.steamcondenser.steam.community.apps.ServerAtAddress;
import com.github.koraktor.steamcondenser.steam.community.apps.UpToDateCheck;
import com.github.koraktor.steamcondenser.steam.webapi.builder.PlayerServiceBuilder;

/**
 * A service class which calls the operations supplied in the ISteamApps Web API service.
 *
 * @author Lewis Keen
 * @author Sebastian Staudt
 */
public class IPlayerService {
	private static final String I_PLAYER_SERVICE = "IPlayerService";

	private final PlayerServiceBuilder playerServiceBuilder;
	
	/**
	 * Creates a new instance of the IPlayerService Web API service object
	 * 
	 * @param playerServiceBuilder the player service builder used to create object representations of the operation responses
	 */
	public IPlayerService(PlayerServiceBuilder playerServiceBuilder) {
		this.playerServiceBuilder = playerServiceBuilder;
	}
	
}
