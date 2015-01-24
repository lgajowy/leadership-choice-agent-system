package com.pl.leadership_choice.library.group;

import com.pl.leadership_choice.library.agent.leader_choice_request.LeaderParameter;
import com.pl.leadership_choice.library.agent.leader_choice_request.MandatoryLeaderParameter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lukasz on 24.01.15.
 */
public class GroupRegistrar {

    Map<String, Group> groups;

    public GroupRegistrar() {
        this.groups = new HashMap<>();
    }

    public Map<String, Group> getGroups() {
        return groups;
    }

    public void registerGroup(String groupId, Group group) {
        groups.put(groupId, group);
    }

    public Set<String> getMembersForId(String id) {
        assertIdPresence(id);
        return groups.get(id).getMembers();
    }

    public Map<String, MandatoryLeaderParameter> getMandatoryLeaderParametersForId(String id) {
        assertIdPresence(id);
        return groups.get(id).getGroupLeadersMandatoryParameters();
    }

    public Map<String, LeaderParameter> getOptionalLeaderParametersForId(String id) {
        assertIdPresence(id);

        return groups.get(id).getGroupLeadersOptionalParameters();
    }

    private void assertIdPresence(String id) {
        if (!groups.containsKey(id)) {
            throw new RuntimeException("Group with given id has not been registered yet!");
        }
    }
}
