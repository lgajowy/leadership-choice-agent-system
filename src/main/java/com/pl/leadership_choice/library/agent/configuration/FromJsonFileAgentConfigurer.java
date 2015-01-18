package com.pl.leadership_choice.library.agent.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukasz on 18.01.15.
 */
public class FromJsonFileAgentConfigurer implements AgentConfigurer {

    Logger logger = LoggerFactory.getLogger(FromJsonFileAgentConfigurer.class);

    private File jsonConfigFile;

    public FromJsonFileAgentConfigurer(File jsonConfigurationFile) {
        jsonConfigFile = jsonConfigurationFile;
    }

    @Override
    public Map configureAgent() {
        HashMap features = null;
        try {
            features = new ObjectMapper().readValue(jsonConfigFile, HashMap.class);
        } catch (IOException e) {
            logger.error("Couldn't parse comfiguration file!");
            e.printStackTrace();
        }
        return features;
    }
}
