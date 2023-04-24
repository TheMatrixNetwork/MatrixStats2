package org.matrixnetwork.stats2.rest;

import net.skinsrestorer.api.SkinsRestorerAPI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.matrixnetwork.stats2.MatrixStats;
import org.matrixnetwork.stats2.util.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/skin")
public class SkinResource {
    private final JSONParser parser = new JSONParser();

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getSkin(@HeaderParam("Authorization") String tokenStr) {
        if (tokenStr == null) {
            return Response.status(400).build();
        }

        String username = Auth.getInstance().verifyToken(tokenStr);

        if (username != null) {
            try {
                JSONObject retObj = new JSONObject();
                String skinName = SkinsRestorerAPI.getApi().getSkinName(username);
                retObj.put("skin", skinName == null ? username : skinName);
                return Response.ok(retObj.toJSONString()).build();
            } catch (Exception ex) {
                MatrixStats.getPlugin().getLogger().info(ex.toString());
                MatrixStats.getPlugin().getLogger().info(ex.getMessage());
                return Response.noContent().build();
            }
        } else {
            return Response.status(403).build();
        }
    }

    @OPTIONS
    @Produces(MediaType.APPLICATION_JSON)
    public Response optionsForBookResource() {
        return Response.status(200)
                .header("Allow", "POST")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Content-Length", "0")
                .build();
    }
}
