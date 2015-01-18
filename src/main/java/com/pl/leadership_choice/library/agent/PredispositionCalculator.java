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
        Predisposition predisposition = null;

        for (Map.Entry<String, MandatoryLeaderParameter> entry : mandatoryFeatures.entrySet()) {
            if (!agentFeatures.containsKey(entry.getKey())) {
                predisposition = new Predisposition();
            }
        }

        if (areMandatoryParametersMet()) {

        }
        predisposition = new Predisposition();
        return predisposition;
    }

    public boolean areMandatoryParametersMet() {
        for (Map.Entry<String, MandatoryLeaderParameter> leaderParameter : mandatoryFeatures.entrySet()) {
            if (agentFeatures.containsKey(leaderParameter.getKey())) {
                Double checkedAgentValue = agentFeatures.get(leaderParameter.getKey());
                MandatoryLeaderParameter checkedLeaderParameter = leaderParameter.getValue();

                if (!isParameterMet(checkedAgentValue, checkedLeaderParameter)) {
                    return false;
                }
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
