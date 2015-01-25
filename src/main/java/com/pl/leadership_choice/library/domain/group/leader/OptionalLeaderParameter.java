package com.pl.leadership_choice.library.domain.group.leader;

/**
 * Created by lukasz on 17.01.15.
 */
public class OptionalLeaderParameter {

    private Double weight;

    public OptionalLeaderParameter() {
    }

    public OptionalLeaderParameter(Double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
