/*
 * Copyright 2017 Juraci Paixão Kröhling
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.apm.data.reader;

import java.util.logging.Logger;

import org.hawkular.apm.data.publisher.control.Configuration;
import org.hawkular.apm.data.publisher.control.ConfigurationResources;
import org.hawkular.apm.data.reader.boundary.DataReaderServer;
import org.hawkular.apm.data.reader.boundary.HealthCheckServer;

/**
 * @author Juraci Paixão Kröhling
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Hawkular APM Data Reader is starting");
        Configuration configuration = ConfigurationResources.build(args).getConfiguration();
        try {
            DataReaderServer.start(configuration.getBind(), configuration.getPort());
            HealthCheckServer.start(configuration.getHealthcheckBind(), configuration.getHealthcheckPort());
            logger.info(() -> String.format("Hawkular APM Data Reader started at %s:%s", configuration.getBind(), configuration.getPort()));
            logger.info(() -> String.format("Hawkular APM Data Reader Health Check started at %s:%s", configuration.getHealthcheckBind(), configuration.getHealthcheckPort()));
        } catch (Throwable t) {
            logger.severe(() -> String.format("Could not start the Hawkular APM Data Reader. Reason: %s", t.getMessage()));
            System.exit(127);
        }
    }
}
