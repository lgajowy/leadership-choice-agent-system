package com.pl.leadership_choice.library.domain.group.member;

import com.pl.leadership_choice.library.infrastructure.calculator.PredispositionCalculator;
import com.pl.leadership_choice.library.domain.group.leader.LeaderRequirements;
import jade.core.AID;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukasz on 25.01.15.
 */
public class GroupMembershipRegistrar {

    private Map<String, GroupMember> groupMemberships = new HashMap<>();

    public GroupMember registerAgent(String groupId,
                                     LeaderRequirements leaderRequirements,
                                     Map agentProperties,
                                     AID agentsAID) {


        Predisposition registeredAgentsPredisposition = calculatePredisposition(leaderRequirements, agentProperties);

        //TODO: AL: On the beginning he's his own leader. Is this correct?
        GroupMember groupMember = new GroupMember(groupId, registeredAgentsPredisposition, agentsAID);
        groupMemberships.put(groupId, groupMember);
        return groupMember;
    }

    private Predisposition calculatePredisposition(LeaderRequirements leaderRequirements, Map agentProperties) {
        PredispositionCalculator calculator = new PredispositionCalculator(leaderRequirements.getGroupLeadersMandatoryParameters(),
                leaderRequirements.getGroupLeadersOptionalParameters());

        return calculator.calculatePredisposition(agentProperties);
    }

    public Map<String, GroupMember> getGroupMemberships() {
        return groupMemberships;
    }
}
