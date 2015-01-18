package com.pl.leadership_choice.library.agent;

import com.pl.leadership_choice.library.agent.leader_choice_request.LeaderParameter;
import com.pl.leadership_choice.library.agent.leader_choice_request.MandatoryLeaderParameter;
import com.pl.leadership_choice.library.agent.leader_choice_request.exception.UnrecognizedRelationException;

import java.util.Map;

/**
 * Created by lukasz on 18.01.15.
 */
public class PredispositionCalculator {

    private Map<String, Double> agentFeatures;

    private Map<String, MandatoryLeaderParameter> mandatoryFeatures;

    private Map<String, LeaderParameter> optionalFeatures;

    public PredispositionCalculator(Map<String, Double> agentFeatures, Map<String, MandatoryLeaderParameter> mandatoryFeatures, Map<String, LeaderParameter> optionalFeatures) {
        this.agentFeatures = agentFeatures;
        this.mandatoryFeatures = mandatoryFeatures;
        this.optionalFeatures = optionalFeatures;
    }

    public Predisposition calculatePredisposition() {
        if (!areAllDesiredFeaturesPresent()) {
            return new Predisposition(0D, false);
        } else if (!areMandatoryParametersMet()) {
            return new Predisposition(0D, false);
        } else {
            return new Predisposition(calculateScore(), true);
        }
    }

    private Double calculateScore() {
        Double counter = 0D;
        Double denominator = 0D;

        for (Map.Entry<String, MandatoryLeaderParameter> leaderParameter : mandatoryFeatures.entrySet()) {
            Double actualAgentFeatureValue = agentFeatures.get(leaderParameter.getKey());
            Double featureWeight = leaderParameter.getValue().getWeight();

            counter += actualAgentFeatureValue * featureWeight;
            denominator += featureWeight;
        }

        for (Map.Entry<String, LeaderParameter> leaderParameter : optionalFeatures.entrySet()) {
            Double actualAgentFeatureValue = agentFeatures.get(leaderParameter.getKey());
            Double featureWeight = leaderParameter.getValue().getWeight();

            counter += actualAgentFeatureValue * featureWeight;
            denominator += featureWeight;
        }

        if (counter == 0D && denominator == 0D) {
            return 0D;
        }
        return counter / denominator;
    }

    private boolean areAllDesiredFeaturesPresent() {
        for (Map.Entry<String, MandatoryLeaderParameter> entry : mandatoryFeatures.entrySet()) {
            if (!agentFeatures.containsKey(entry.getKey())) {
                return false;
            }
        }
        return true;
    }

    private boolean areMandatoryParametersMet() {
        for (Map.Entry<String, MandatoryLeaderParameter> leaderParameter : mandatoryFeatures.entrySet()) {
            if (agentFeatures.containsKey(leaderParameter.getKey())) {
                Double checkedAgentValue = agentFeatures.get(leaderParameter.getKey());
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
        if ((checkedLeaderParameter.getRelation()).equals("isEqualTo")) {
            if (checkedAgentValue.equals(checkedLeaderParameter.getValue())) {
                return true;
            }
        } else if ((checkedLeaderParameter.getRelation()).equals("isGreaterThan")) {
            if (checkedAgentValue > checkedLeaderParameter.getValue()) {
                return true;
            }
        } else if ((checkedLeaderParameter.getRelation()).equals("isLessThan")) {
            if (checkedAgentValue < checkedLeaderParameter.getValue()) {
                return true;
            }
        } else if ((checkedLeaderParameter.getRelation()).equals("isLessOrEqualTo")) {
            if (checkedAgentValue <= checkedLeaderParameter.getValue()) {
                return true;
            }
        } else if ((checkedLeaderParameter.getRelation()).equals("isGreaterOrEqualTo")) {
            if (checkedAgentValue >= checkedLeaderParameter.getValue()) {
                return true;
            }
        } else {
            throw new UnrecognizedRelationException("Cannot determine what relation it is.");
        }
        return false;
    }
}
