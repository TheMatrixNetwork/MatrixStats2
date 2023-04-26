package org.matrixnetwork.stats2.rest;

import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.parser.JSONParser;
import org.matrixnetwork.stats2.MatrixStats;
import org.matrixnetwork.stats2.entity.MatrixPlayer;
import org.matrixnetwork.stats2.entity.PlayerKill;
import org.matrixnetwork.stats2.manager.DataManager;
import org.matrixnetwork.stats2.pojo.PlayerKillDTO;
import org.matrixnetwork.stats2.pojo.PlayerStatsDTO;
import org.matrixnetwork.stats2.util.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/kills")
public class PlayerKillResource {
    private DataManager dataManager = DataManager.getInstance();

    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    @GET
    public Response getStats(@HeaderParam("Authorization") String tokenStr) {
        if (tokenStr == null) {
            return Response.status(400).build();
        }

        String username = Auth.getInstance().verifyToken(tokenStr);
        if (username != null) {
            try(Session s = dataManager.getSession()) {
                MatrixPlayer mp = dataManager.getMatrixPlayerByProperty("username", username, s);
                return Response.ok(mp.getKills().stream()
                        .map(PlayerKillDTO::from)
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
