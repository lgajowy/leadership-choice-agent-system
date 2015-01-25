package com.pl.leadership_choice.library.infrastructure.calculator;

import com.pl.leadership_choice.library.domain.group.leader.MandatoryLeaderParameter;
import com.pl.leadership_choice.library.domain.group.leader.OptionalLeaderParameter;
import com.pl.leadership_choice.library.infrastructure.leader_choice_request.exception.UnrecognizedRelationException;
import com.pl.leadership_choice.library.domain.group.member.Predisposition;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukasz on 18.01.15.
 *
 * 25.01.2015 - adam - i created these constants to keep those values more visible
 */
public class PredispositionCalculator {

    private final static String GREATER_THAN = "greaterThan";
    private final static String EQUAL_TO = "isEqualTo";
    private final static String LESS_THAN = "isLessThan";
    private final static String LESS_OR_EQUAL_TO = "isLessOrEqualTo";
    private final static String GREATER_OR_EQUAL_TO = "isGreaterOrEqualTo";

    private Map<String, MandatoryLeaderParameter> mandatoryFeatures = new HashMap<>();

    private Map<String, OptionalLeaderParameter> optionalFeatures = new HashMap<>();

    public PredispositionCalculator(Map<String, MandatoryLeaderParameter> mandatoryFeatures, Map<String, OptionalLeaderParameter> optionalFeatures) {
        this.mandatoryFeatures = mandatoryFeatures;
        this.optionalFeatures = optionalFeatures;
    }

    public Predisposition calculatePredisposition(Map<String, Double> agentParameters) {
        if (!areAllDesiredParametersPresent(agentParameters)) {
            return new Predisposition(0D, false);
        } else if (!areMandatoryParametersMet(agentParameters)) {
            return new Predisposition(0D, false);
        } else {
            return new Predisposition(calculateScore(agentParameters), true);
        }
    }

    public String comparePretenders() {
        return "";
    }

    private Double calculateScore(Map<String, Double> agentFeatures) {
        Double counter = 0D;
        Double denominator = 0D;

        for (Map.Entry<String, MandatoryLeaderParameter> leaderParameter : mandatoryFeatures.entrySet()) {
            Double actualAgentFeatureValue = agentFeatures.get(leaderParameter.getKey());
            Double featureWeight = leaderParameter.getValue().getWeight();

            counter += actualAgentFeatureValue * featureWeight;
            denominator += featureWeight;
        }

        if (!optionalFeatures.isEmpty()) {
            for (Map.Entry<String, OptionalLeaderParameter> leaderParameter : optionalFeatures.entrySet()) {
                Double actualAgentFeatureValue = agentFeatures.get(leaderParameter.getKey());
                Double featureWeight = leaderParameter.getValue().getWeight();

                counter += actualAgentFeatureValue * featureWeight;
                denominator += featureWeight;
            }
        }

        if (counter == 0D && denominator == 0D) {
            return 0D;
        }
        return counter / denominator;
    }

    private boolean areAllDesiredParametersPresent(Map<String, Double> agentParameters) {
        for (Map.Entry<String, MandatoryLeaderParameter> entry : mandatoryFeatures.entrySet()) {
            if (!agentParameters.containsKey(entry.getKey())) {
                return false;
            }
        }
        return true;
    }

    private boolean areMandatoryParametersMet(Map<String, Double> agentParameters) {
        for (Map.Entry<String, MandatoryLeaderParameter> leaderParameter : mandatoryFeatures.entrySet()) {
            if (agentParameters.containsKey(leaderParameter.getKey())) {
                Double checkedAgentValue = agentParameters.get(leaderParameter.getKey());
                MandatoryLeaderParameter checkedLeaderParameter = leaderParameter.getValue();

                if (!isParameterMet(checkedAgentValue, checkedLeaderParameter)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean isParameterMet(Double checkedAgentValue, MandatoryLeaderParameter checkedLeaderParameter) {
        if ((checkedLeaderParameter.getRelation()).equals(this.EQUAL_TO)) {
            if (checkedAgentValue.equals(checkedLeaderParameter.getValue())) {
                return true;
            }
        } else if ((checkedLeaderParameter.getRelation()).equals(this.GREATER_THAN)) {
            if (checkedAgentValue > checkedLeaderParameter.getValue()) {
                return true;
            }
        } else if ((checkedLeaderParameter.getRelation()).equals(this.LESS_THAN)) {
            if (checkedAgentValue < checkedLeaderParameter.getValue()) {
                return true;
            }
        } else if ((checkedLeaderParameter.getRelation()).equals(this.LESS_OR_EQUAL_TO)) {
            if (checkedAgentValue <= checkedLeaderParameter.getValue()) {
                return true;
            }
        } else if ((checkedLeaderParameter.getRelation()).equals(this.GREATER_OR_EQUAL_TO)) {
            if (checkedAgentValue >= checkedLeaderParameter.getValue()) {
                return true;
            }
        } else {
            throw new UnrecognizedRelationException("Cannot determine what relation it is.");
        }
        return false;
    }
}
