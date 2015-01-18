package com.pl.leadership_choice.library.agent.leader_choice_request;

/**
 * Created by lukasz on 17.01.15.
 */
public class LeaderParameter {

    private Double weight;

    public LeaderParameter() {
    }

    public LeaderParameter(Double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
