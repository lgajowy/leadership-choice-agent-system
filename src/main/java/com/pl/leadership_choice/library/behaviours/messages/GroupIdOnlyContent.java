package com.pl.leadership_choice.library.behaviours.messages;

/**
 * Created by lukasz on 28.01.15.
 */
public class GroupIdOnlyContent {

    private String groupId;

    public GroupIdOnlyContent() {
    }

    public GroupIdOnlyContent(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
