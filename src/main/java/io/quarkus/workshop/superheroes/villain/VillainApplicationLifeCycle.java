package io.quarkus.workshop.superheroes.villain;

import io.quarkus.runtime.ShutdownEvent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;


@ApplicationScoped
public class VillainApplicationLifeCycle {

    private static final Logger LOGGER = Logger.getLogger(VillainApplicationLifeCycle.class);

    void onStart(@Observes ShutdownEvent ev) {
        LOGGER.info(" __     ___ _ _       _             _    ____ ___ ");
        LOGGER.info(" \\ \\   / (_) | | __ _(_)_ __       / \\  |  _ \\_ _|");
        LOGGER.info("  \\ \\ / /| | | |/ _` | | '_ \\     / _ \\ | |_) | | ");
        LOGGER.info("   \\ V / | | | | (_| | | | | |   / ___ \\|  __/| | ");
        LOGGER.info("    \\_/  |_|_|_|\\__,_|_|_| |_|  /_/   \\_\\_|  |___|");

        LOGGER.info("Villain service is runninggk DKDKkaSN");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application VILLAIN is stopping...");
    }
}
