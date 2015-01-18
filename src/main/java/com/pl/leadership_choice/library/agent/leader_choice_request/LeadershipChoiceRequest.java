package com.pl.leadership_choice.library.agent.leader_choice_request;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lukasz on 17.01.15.
 */
public class LeadershipChoiceRequest {

    private String groupId;

    private ArrayList groupMembers;

    private Map<String, String> mandatoryFeatures;

    private Map<String, OptionalParameter> optionalFeatures;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public ArrayList getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(ArrayList groupMembers) {
        this.groupMembers = groupMembers;
    }

    public Map<String, String> getMandatoryFeatures() {
        return mandatoryFeatures;
    }

    public void setMandatoryFeatures(Map<String, String> mandatoryFeatures) {
        this.mandatoryFeatures = mandatoryFeatures;
    }

    public Map<String, OptionalParameter> getOptionalFeatures() {
        return optionalFeatures;
    }

    public void setOptionalFeatures(Map<String, OptionalParameter> optionalFeatures) {
        this.optionalFeatures = optionalFeatures;
    }
}
