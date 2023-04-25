package org.matrixnetwork.stats2.rest;

import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.parser.JSONParser;
import org.matrixnetwork.stats2.MatrixStats;
import org.matrixnetwork.stats2.entity.MatrixPlayer;
import org.matrixnetwork.stats2.manager.DataManager;
import org.matrixnetwork.stats2.pojo.PlayerStatsDTO;
import org.matrixnetwork.stats2.util.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/stats")
public class StatsResource {
    private final JSONParser parser = new JSONParser();
    DataManager dataManager = DataManager.getInstance();

    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    @GET
    public Response getStats(@HeaderParam("Authorization") String tokenStr) {
        if (tokenStr == null) {
            return Response.status(401).build();
        }

        String username = Auth.getInstance().verifyToken(tokenStr);

        try(Session s = dataManager.getSession()) {
            if (username != null) {
                try {
                    MatrixPlayer mp = DataManager.getInstance().getMatrixPlayerByProperty("username", username, s);
                    return Response.ok(mp.getStats().stream()
                            .map(PlayerStatsDTO::from)
                            .collect(Collectors.toList())).build();
                } catch (Exception ex) {
                    MatrixStats.getPlugin().getLogger().info(ex.toString());
                    MatrixStats.getPlugin().getLogger().info(ex.getMessage());
                    return Response.noContent().build();
                }
            } else {
                return Response.status(403).build();
            }
        }
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Path("latest")
    @GET
    public Response getLatestStats(@HeaderParam("Authorization") String tokenStr) {
        if (tokenStr == null) {
            return Response.status(401).build();
        }
        String username = Auth.getInstance().verifyToken(tokenStr);

        try(Session s = dataManager.getSession()) {
            if (username != null) {
                try {
                    MatrixPlayer mp = dataManager.getMatrixPlayerByProperty("username", username, s);
                    return Response.ok(PlayerStatsDTO.from(dataManager.getLastStatisticsOfPlayer(mp.getId(), s))).build();
                } catch (Exception ex) {
                    MatrixStats.getPlugin().getLogger().info(ex.toString());
                    MatrixStats.getPlugin().getLogger().info(ex.getMessage());
                    return Response.noContent().build();
                }
            } else {
                return Response.status(403).build();
            }
        }
    }

    @Path("/matrixdex/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatsOfKilledPlayer(@PathParam("username") String targetUsername, @HeaderParam("Authorization") String tokenStr) {
        if (tokenStr == null) {
            return Response.status(401).build();
        }
        String issuerUser = Auth.getInstance().verifyToken(tokenStr);

        try(Session s = dataManager.getSession()) {
            if (issuerUser != null) {
                try {
                    MatrixPlayer issuerPlayer = dataManager.getMatrixPlayerByProperty("username", issuerUser, s);
                    MatrixPlayer targetPlayer = dataManager.getMatrixPlayerByProperty("username", targetUsername, s);
                    String targetUUID = targetPlayer.getUUID();

                    if(issuerPlayer.getKills().stream().anyMatch(kill -> kill.getKilledUUID().equals(targetUUID))) {
                        return Response.ok(PlayerStatsDTO.from(
                                dataManager.getLastStatisticsOfPlayer(targetPlayer.getId(), s)
                        )).build();
                    }
                    else {
                        return Response.status(403).build();
                    }

                } catch (Exception ex) {
                    MatrixStats.getPlugin().getLogger().info(ex.toString());
                    MatrixStats.getPlugin().getLogger().info(ex.getMessage());
                    return Response.noContent().build();
                }
            } else {
                return Response.status(403).build();
            }
        }
    }
}
