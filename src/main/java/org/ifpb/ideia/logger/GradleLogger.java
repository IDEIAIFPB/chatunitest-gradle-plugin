package org.ifpb.ideia.logger;

import zju.cst.aces.api.Logger;

public class GradleLogger implements Logger {

    private final org.gradle.api.logging.Logger gradleLogger;

    public GradleLogger(org.gradle.api.logging.Logger gradleLogger) {
        this.gradleLogger = gradleLogger;
    }

    @Override
    public void info(String msg) {
        gradleLogger.info(msg);
    }

    @Override
    public void warn(String msg) {
        gradleLogger.warn(msg);
    }

    @Override
    public void error(String msg) {
        gradleLogger.error(msg);
    }

    @Override
    public void debug(String msg) {
        gradleLogger.debug(msg);
    }
}
