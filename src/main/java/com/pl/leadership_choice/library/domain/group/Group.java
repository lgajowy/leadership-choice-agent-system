package com.pl.leadership_choice.library.domain.group;

import com.pl.leadership_choice.library.domain.group.leader.OptionalLeaderParameter;
import com.pl.leadership_choice.library.domain.group.leader.MandatoryLeaderParameter;
import com.pl.leadership_choice.library.domain.group.leader.LeaderRequirements;

import java.util.Map;
import java.util.Set;

/**
 * Created by lukasz on 24.01.15.
 */
public class Group {

    private Set<String> members;

    private LeaderRequirements groupLeaderRequirements;


    public Group(Set<String> members, Map<String, MandatoryLeaderParameter> groupLeadersMandatoryParameters, Map<String, OptionalLeaderParameter> groupLeadersOptionalParameters) {
        this.members = members;
        groupLeaderRequirements = new LeaderRequirements(groupLeadersMandatoryParameters, groupLeadersOptionalParameters);
    }

    public Set<String> getMembers() {
        return members;
    }

    public LeaderRequirements getGroupLeaderRequirements() {
        return groupLeaderRequirements;
    }
}
