package org.matrixnetwork.stats2.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AuthTest {
    @Test
    void testBasicUsage() {
        Auth auth = Auth.getInstance();

        Auth.getInstance().TOKEN_EXPIRATION_TIME = 1;

        String token  = auth.generateToken("UUID");

        assertThat(auth.verifyToken(token)).isEqualTo("UUID");

        try {
            Thread.sleep((Auth.getInstance().TOKEN_EXPIRATION_TIME+1)*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(auth.verifyToken(token)).isEqualTo(null);
    }
}