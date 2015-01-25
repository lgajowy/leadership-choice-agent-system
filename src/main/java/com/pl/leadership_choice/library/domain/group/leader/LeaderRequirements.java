package com.pl.leadership_choice.library.domain.group.leader;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukasz on 25.01.15.
 */
public class LeaderRequirements {

    private Map<String, MandatoryLeaderParameter> groupLeadersMandatoryParameters;

    private Map<String, OptionalLeaderParameter> groupLeadersOptionalParameters;

    public LeaderRequirements(Map<String, MandatoryLeaderParameter> groupLeadersMandatoryParameters, Map<String, OptionalLeaderParameter> groupLeadersOptionalParameters) {
        this.groupLeadersMandatoryParameters = Optional.fromNullable(groupLeadersMandatoryParameters).or(new HashMap<String, MandatoryLeaderParameter>());
        this.groupLeadersOptionalParameters = Optional.fromNullable(groupLeadersOptionalParameters).or(new HashMap<String, OptionalLeaderParameter>());
    }

    public Map<String, MandatoryLeaderParameter> getGroupLeadersMandatoryParameters() {
        return groupLeadersMandatoryParameters;
    }

    public Map<String, OptionalLeaderParameter> getGroupLeadersOptionalParameters() {
        return groupLeadersOptionalParameters;
    }
}
