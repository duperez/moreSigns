#!/bin/sh
# Runs the game tests and exits 0 only when the game says every required test passed.
# On Minecraft 1.18.2 the game test server ends with System.exit, which takes Gradle down with it and makes
# `./gradlew runGameTestServer` report a failure even when every test passed, so the result is read from the log.
cd "$(dirname "$0")/.." || exit 2
output=$(sh ./gradlew runGameTestServer --console=plain 2>&1)
echo "$output" | grep -E 'required tests (passed|failed)|GAME TESTS COMPLETE|failed at'
echo "$output" | grep -q 'All [0-9]* required tests passed'
