package com.pl.leadership_choice.library.agent.leader_choice_request;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class LeadershipChoiceRequestMapperTest {

    private final String givenLeadershipRequest = "{\n" +
            "    \"groupId\": \"10\",\n" +
            "    \"mandatoryFeatures\": {\n" +
            "        \"height\": {\n" +
            "            \"value\": 10,\n" +
            "            \"weight\": 11,\n" +
            "            \"relation\": \"greaterThan\"\n" +
            "        },\n" +
            "        \"niceHair\": {\n" +
            "            \"value\": 10,\n" +
            "            \"weight\": 11\n" +
            "        }\n" +
            "    },\n" +
            "    \"optionalFeatures\": {\n" +
            "        \"fancyGlasses\": {\n" +
            "            \"value\": 10,\n" +
            "            \"weight\": 11\n" +
            "        },\n" +
            "        \"niceHair\": {\n" +
            "            \"value\": 10,\n" +
            "            \"weight\": 11\n" +
            "        }\n" +
            "    },\n" +
            "    \"groupMembers\": [\n" +
            "        \"nazwa_1_agenta\",\n" +
            "        \"nazwa_2_agenta\"\n" +
            "    ]\n" +
            "}";


    @Test
    public void shouldMapGivenRequestToObject(){
        LeadershipChoiceRequest leadershipChoiceRequest = new LeadershipChoiceRequestMapper(givenLeadershipRequest).mapRequest();
        assertThat(leadershipChoiceRequest).isNotNull();
    }
}