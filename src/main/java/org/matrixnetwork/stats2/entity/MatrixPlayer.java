package org.matrixnetwork.stats2.entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.List;

@Entity
public class MatrixPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Long id;

    String uuid;

    private String username;

    @OneToMany(mappedBy = "matrixPlayer", fetch = FetchType.EAGER)
    private List<PlayerStats> stats;

    //region Constructors
    public MatrixPlayer(String uuid, List<PlayerStats> stats, String username) {
        this.uuid = uuid;
        this.stats = stats;
        this.username = username;
    }

    public MatrixPlayer(String uuid, String username) {
        this(uuid, null, username);
    }

    public MatrixPlayer() {

    }
    //endregion

    //region Getters and Setters
    public String getUUID() {
        return uuid;
    }

    public void setUUID(String id) {
        this.uuid = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<PlayerStats> getStats() {
        return stats;
    }

    public void setStats(List<PlayerStats> stats) {
        this.stats = stats;
    }
    //endregion
}
