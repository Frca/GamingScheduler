package com.frca.purtges;

import com.frca.purtges.Const.Ids;
import com.frca.purtges.helpers.Method;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.Key;

import java.io.IOException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

@Api(
    name = "teamtimerendpoint",
    namespace = @ApiNamespace(ownerDomain = "frca.com", ownerName = "frca.com", packagePath = Ids.PACKAGE_NAME),
    clientIds = {Ids.WEB_CLIENT_ID, Ids.ANDROID_CLIENT_ID},
    audiences = {Ids.ANDROID_AUDIENCE}
)
public class TeamTimerEndpoint {

    @ApiMethod(name = "getTeamTimer")
    public TeamTimer getTeamTimer(Key id) throws IOException  {
        EntityManager mgr = getEntityManager();
        TeamTimer teamTimer = null;
        try {
            id = Method.checkKey(id);
            teamTimer = mgr.find(TeamTimer.class, id);
        } finally {
            mgr.close();
        }
        return teamTimer;
    }

    @ApiMethod(name = "insertTeamTimer")
    public TeamTimer insertTeamTimer(TeamTimer teamTimer) throws IOException {
        EntityManager mgr = getEntityManager();
        try {
            teamTimer = Method.checkKeyEntity(teamTimer);

            if (containsTeamTimer(teamTimer)) {
                throw new EntityExistsException("Object already exists");
            }
            mgr.persist(teamTimer);
        } finally {
            mgr.close();
        }
        return teamTimer;
    }

    @ApiMethod(name = "updateTeamTimer")
    public TeamTimer updateTeamTimer(TeamTimer teamTimer) throws IOException {
        EntityManager mgr = getEntityManager();
        try {
            teamTimer = Method.checkKeyEntity(teamTimer);

            if (!containsTeamTimer(teamTimer)) {
                throw new EntityNotFoundException("Object does not exist");
            }
            mgr.persist(teamTimer);
        } finally {
            mgr.close();
        }
        return teamTimer;
    }

    @ApiMethod(name = "removeTeamTimer")
    public TeamTimer removeTeamTimer(Key id) throws IOException {
        EntityManager mgr = getEntityManager();
        TeamTimer teamTimer = null;
        try {
            id = Method.checkKey(id);
            teamTimer = mgr.find(TeamTimer.class, id);
            mgr.remove(teamTimer);
        } finally {
            mgr.close();
        }
        return teamTimer;
    }

    private boolean containsTeamTimer(TeamTimer teamTimer) {
        EntityManager mgr = getEntityManager();
        boolean contains = true;
        try {
            TeamTimer item = mgr.find(TeamTimer.class, teamTimer.getId());
            if (item == null) {
                contains = false;
            }
        } finally {
            mgr.close();
        }
        return contains;
    }

    private static EntityManager getEntityManager() {
        return EMF.get().createEntityManager();
    }

}
