package com.pl.leadership_choice.library.domain.group.member;

import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.domain.group.leader.LeaderRequirements;
import com.pl.leadership_choice.library.infrastructure.calculator.PredispositionCalculator;
import jade.core.AID;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukasz on 25.01.15.
 */
public class GroupMembershipRegistrar {

    private Map<String, GroupMember> groupMemberships = new HashMap<>();

    public GroupMember registerAgent(String agentId, String groupId, LeaderRequirements leaderRequirements, Map agentProperties) {
        Predisposition registeredAgentsPredisposition = calculatePredisposition(leaderRequirements, agentProperties);

        GroupMember groupMember = new GroupMember(agentId, registeredAgentsPredisposition);
        if (registeredAgentsPredisposition.getCanBecomeLeader()) {
            groupMember.setMemberCandidacy(prepareCandidacy(agentId, groupId, registeredAgentsPredisposition.getScore()));
        }
        groupMemberships.put(groupId, groupMember);
        return groupMember;
    }

    public Candidacy prepareCandidacy(String agentId, String groupId, Double candidateScore) {
        return new Candidacy(agentId, groupId, candidateScore, null);
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
