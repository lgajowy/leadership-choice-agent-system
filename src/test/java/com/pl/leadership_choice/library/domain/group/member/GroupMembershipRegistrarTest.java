package com.pl.leadership_choice.library.domain.group.member;


import com.pl.leadership_choice.library.domain.group.leader.LeaderRequirements;
import com.pl.leadership_choice.library.domain.group.leader.MandatoryLeaderParameter;
import com.pl.leadership_choice.library.domain.group.leader.OptionalLeaderParameter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupMembershipRegistrarTest {

    private final String expectedAgentId = "agentId";
    private final String expectedGroupId = "groupId";
    private GroupMembershipRegistrar groupMembershipRegistrar;

    private Map<String, MandatoryLeaderParameter> mandatoryLeaderParameters = new HashMap<>();
    private Map<String, OptionalLeaderParameter> optionalLeaderParameters = new HashMap<>();
    private Map<String, Double> agentParameters = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        groupMembershipRegistrar = new GroupMembershipRegistrar();
    }

    @Test
    public void shouldRegisterNewMembership() throws Exception {
        groupMembershipRegistrar.registerAgent(expectedAgentId, expectedGroupId, new LeaderRequirements(mandatoryLeaderParameters, null), agentParameters);

        assertThat(groupMembershipRegistrar.getGroupMemberships()).isNotEmpty();
    }

    @Test
    public void shouldCalculateScoreWhileRegistering() throws Exception {
        mandatoryLeaderParameters.put("exampleMandatoryParameter", new MandatoryLeaderParameter(10D, 100D, "isEqualTo"));
        agentParameters.put("exampleMandatoryParameter", 10D);

        groupMembershipRegistrar.registerAgent(expectedAgentId, expectedGroupId, new LeaderRequirements(mandatoryLeaderParameters, optionalLeaderParameters), agentParameters);

        assertThat(groupMembershipRegistrar.getGroupMemberships().get(expectedGroupId).getPredisposition().getScore()).isEqualTo(10D);
    }
}