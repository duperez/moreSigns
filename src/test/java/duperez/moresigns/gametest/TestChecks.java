package duperez.moresigns.gametest;

import net.minecraft.gametest.framework.GameTestHelper;

/** This version's game test helper has no assertTrue, only fail. */
final class TestChecks {
    private TestChecks() {
    }

    static void check(GameTestHelper helper, boolean condition, String message) {
        if (!condition) {
            helper.fail(message);
        }
    }
}
