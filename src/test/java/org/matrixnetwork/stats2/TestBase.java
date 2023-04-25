package org.matrixnetwork.stats2;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Objects;

public abstract class TestBase {

    protected ServerMock server;
    protected MatrixStats plugin;

    @BeforeEach
    public void setUp() {
        server = MockBukkit.mock();
        MatrixStats.TEST = true;
        plugin = MockBukkit.load(MatrixStats.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }
}