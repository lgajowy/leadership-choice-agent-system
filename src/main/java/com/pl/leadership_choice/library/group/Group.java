package com.pl.leadership_choice.library.group;

import com.pl.leadership_choice.library.agent.leader_choice_request.LeaderParameter;
import com.pl.leadership_choice.library.agent.leader_choice_request.MandatoryLeaderParameter;

import java.util.Map;
import java.util.Set;

/**
 * Created by lukasz on 24.01.15.
 */
public class Group {

    private Set<String> members;

    private Map<String, MandatoryLeaderParameter> groupLeadersMandatoryParameters;

    private Map<String, LeaderParameter> groupLeadersOptionalParameters;

    public Group(Set<String> members, Map<String, MandatoryLeaderParameter> groupLeadersMandatoryParameters, Map<String, LeaderParameter> groupLeadersOptionalParameters) {
        this.members = members;
        this.groupLeadersMandatoryParameters = groupLeadersMandatoryParameters;
        this.groupLeadersOptionalParameters = groupLeadersOptionalParameters;
    }


    public Set<String> getMembers() {
        return members;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }

    public Map<String, MandatoryLeaderParameter> getGroupLeadersMandatoryParameters() {
        return groupLeadersMandatoryParameters;
    }

    public void setGroupLeadersMandatoryParameters(Map<String, MandatoryLeaderParameter> groupLeadersMandatoryParameters) {
        this.groupLeadersMandatoryParameters = groupLeadersMandatoryParameters;
    }

    public Map<String, LeaderParameter> getGroupLeadersOptionalParameters() {
        return groupLeadersOptionalParameters;
    }

    public void setGroupLeadersOptionalParameters(Map<String, LeaderParameter> groupLeadersOptionalParameters) {
        this.groupLeadersOptionalParameters = groupLeadersOptionalParameters;
    }
}
