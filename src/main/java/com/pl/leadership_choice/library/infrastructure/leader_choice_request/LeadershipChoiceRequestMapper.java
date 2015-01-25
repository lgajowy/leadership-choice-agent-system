package com.pl.leadership_choice.library.infrastructure.leader_choice_request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by lukasz on 18.01.15.
 */
public class LeadershipChoiceRequestMapper {
    Logger logger = LoggerFactory.getLogger(LeadershipChoiceRequestMapper.class);

    private String requestJson;

    public LeadershipChoiceRequestMapper(String requestJson) {
        this.requestJson = requestJson;
    }

    public LeadershipChoiceRequest mapRequest(){
        LeadershipChoiceRequest features = null;
        try {
            features = new ObjectMapper().readValue(requestJson, LeadershipChoiceRequest.class);
        } catch (IOException e) {
            logger.error("Couldn't parse leadership choice request.");
            e.printStackTrace();
        }
        return features;
    }
}