/**
 * Per request from sbrw reloaded core
 * A singleton bean responsible for checking for new launcher releases and adds them to whitelist database automatically.
 * Works only for SBRW Launcher. 
 * @author DriFtyZ
 */

package com.soapboxrace.core.bo;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;
import org.apache.commons.lang3.StringUtils;

@Startup
@Singleton
@DependsOn(value = "ParameterBO")
public class LauncherChecksBO {

    @EJB
    private ParameterBO parameterBO;

    @Inject
    private Logger logger;

    @PostConstruct
    public void init() {
        checkForLauncherUpdates();
    }

    @Asynchronous
    @Schedule(hour = "*", minute = "*/30", persistent = false)
    public void checkForLauncherUpdates() {
        if (Boolean.TRUE.equals(parameterBO.getBoolParam("SIGNED_LAUNCHER"))) {
            JsonObject jsonResponse = null;
            try { // This is for making possible exceptions shut up because we don't want the server to hang itself on startup and never recover. 
                URLConnection urlConnection = new URL("https://api.github.com/repos/SoapboxRaceWorld/GameLauncher_NFSW/releases/latest").openConnection();
                urlConnection.addRequestProperty("Accept", "application/vnd.github.v3+json");
                jsonResponse = new JsonParser().parse(new String(urlConnection.getInputStream().readAllBytes())).getAsJsonObject();
            } catch (IOException | JsonParseException e) { return; } // We also don't really care if it fails so

            // If we got the appropriate json response
            if (jsonResponse != null) {
                String parameter = parameterBO.getStrParam("SIGNED_LAUNCHER_HASH");
                String version = jsonResponse.get("tag_name").toString().replace("\"", "");
                String shaHash = StringUtils.substringBetween(jsonResponse.get("body").toString(), "EXE: `", "`");
                // If the found version is not whitelisted add it to the list
                if (shaHash != null && parameter != null) {
                    logger.info("LauncherChecksBO: Found New SBRW Launcher release! {} ({})", version, shaHash);
                    parameterBO.setParameter("SIGNED_LAUNCHER_HASH", parameter + "\n" + version + "=" + shaHash);
                    logger.info("LauncherChecksBO: Whitelisted New SBRW Launcher version {}", version);
                } else { logger.info("LauncherChecksBO: No new SBRW Launcher releases found."); }
            }
        }
    }
}
