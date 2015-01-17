package com.pl.leadership_choice.core;

import java.util.Map;

/**
 * Created by lukasz on 17.01.15.
 */
public class AgentConfiguration {

    private String groupId;

    private Map<String, String> mandatoryFeatures;

    private Map<String, OptionalFeature> optionalFeatures;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Map<String, String> getMandatoryFeatures() {
        return mandatoryFeatures;
    }

    public void setMandatoryFeatures(Map<String, String> mandatoryFeatures) {
        this.mandatoryFeatures = mandatoryFeatures;
    }

    public Map<String, OptionalFeature> getOptionalFeatures() {
        return optionalFeatures;
    }

    public void setOptionalFeatures(Map<String, OptionalFeature> optionalFeatures) {
        this.optionalFeatures = optionalFeatures;
    }
}
