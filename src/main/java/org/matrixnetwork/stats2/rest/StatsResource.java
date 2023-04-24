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

@Path("stats")
public class StatsResource {
    private final JSONParser parser = new JSONParser();

    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    @GET
    public Response getStats(@HeaderParam("Authorization") String tokenStr) {
        if (tokenStr == null) {
            return Response.status(400).build();
        }

        String username = Auth.getInstance().verifyToken(tokenStr);

        if (username != null) {
            try {
                MatrixPlayer mp = DataManager.getInstance().getMatrixPlayerByProperty("username", username);
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

    @Produces(MediaType.APPLICATION_JSON)
    @Path("latest")
    @GET
    public Response getLatestStats(@HeaderParam("Authorization") String tokenStr) {
        if (tokenStr == null) {
            return Response.status(400).build();
        }
        DataManager dm = DataManager.getInstance();
        String username = Auth.getInstance().verifyToken(tokenStr);

        if (username != null) {
            try {
                MatrixPlayer mp = dm.getMatrixPlayerByProperty("username", username);
                return Response.ok(PlayerStatsDTO.from(dm.getLastStatisticsOfPlayer(mp.getId()))).build();
            } catch (Exception ex) {
                MatrixStats.getPlugin().getLogger().info(ex.toString());
                MatrixStats.getPlugin().getLogger().info(ex.getMessage());
                return Response.noContent().build();
            }
        } else {
            return Response.status(403).build();
        }
    }

    @Path("/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response meet(@PathParam("username") String username) {
        if (MatrixStats.getPlugin().getServer().getPlayer(username) != null) {
            Player p = MatrixStats.getPlugin().getServer().getPlayer(username);
            MatrixPlayer mP = DataManager.getInstance().getMatrixPlayerByProperty("uuid", p.getUniqueId().toString());

            if (mP == null) {
                Session s = DataManager.getInstance().getSession();
                Transaction t = s.beginTransaction();

                mP = (MatrixPlayer) s.merge(new MatrixPlayer(p.getUniqueId().toString(), p.getName()));
                t.commit();
            }

            return Response.ok(mP).build();
        } else {
            return Response.status(404).build();
        }
    }
}
