package org.matrixnetwork.stats2.entity;

import java.time.LocalDateTime;

public class Token {
    private LocalDateTime lastUsed;
    private MatrixPlayer matrixPlayer;

    public Token(LocalDateTime lastUsed, MatrixPlayer matrixPlayer) {
        this.lastUsed = lastUsed;
        this.matrixPlayer = matrixPlayer;
    }

    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
    }

    public MatrixPlayer getMatrixPlayer() {
        return matrixPlayer;
    }

    public void setMatrixPlayer(MatrixPlayer matrixPlayer) {
        this.matrixPlayer = matrixPlayer;
    }
}
