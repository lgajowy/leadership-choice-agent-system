package com.pl.leadership_choice.library.behaviours.messages;

import java.util.Set;

/**
 * Created by lukasz on 29.01.15.
 */
public class GroupResolvingRequest {

    private Set<String> groupMembers;

    private String groupId;

    public GroupResolvingRequest() {
    }

    public Set<String> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(Set<String> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
