package com.pl.leadership_choice.library.behaviours.messages;

/**
 * Created by lukasz on 28.01.15.
 */
public class LeaderResponse {

    private String groupId;

    private String leaderId;

    public LeaderResponse(String groupId, String leaderId) {
        this.groupId = groupId;
        this.leaderId = leaderId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }
}
