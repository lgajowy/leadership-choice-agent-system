package com.pl.leadership_choice.library.infrastructure.leader_choice_request;

import com.pl.leadership_choice.library.domain.group.leader.MandatoryLeaderParameter;
import com.pl.leadership_choice.library.domain.group.leader.OptionalLeaderParameter;

import java.util.HashSet;
import java.util.Map;

/**
 * Created by lukasz on 17.01.15.
 */
public class LeadershipChoiceRequest {

    private String groupId;

    private HashSet<String> groupMembers;

    private Map<String, MandatoryLeaderParameter> mandatoryFeatures;

    private Map<String, OptionalLeaderParameter> optionalFeatures;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public HashSet<String> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(HashSet<String> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public Map<String, MandatoryLeaderParameter> getMandatoryFeatures() {
        return mandatoryFeatures;
    }

    public void setMandatoryFeatures(Map<String, MandatoryLeaderParameter> mandatoryFeatures) {
        this.mandatoryFeatures = mandatoryFeatures;
    }

    public Map<String, OptionalLeaderParameter> getOptionalFeatures() {
        return optionalFeatures;
    }

    public void setOptionalFeatures(Map<String, OptionalLeaderParameter> optionalFeatures) {
        this.optionalFeatures = optionalFeatures;
    }
}
