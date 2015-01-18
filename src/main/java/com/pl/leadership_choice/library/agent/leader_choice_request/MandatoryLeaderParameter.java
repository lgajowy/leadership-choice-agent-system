package com.pl.leadership_choice.library.agent.leader_choice_request;

/**
 * Created by lukasz on 18.01.15.
 */
public class MandatoryLeaderParameter extends LeaderParameter {

    private Double value;

    private String relation;

    public MandatoryLeaderParameter() {
    }

    public MandatoryLeaderParameter(Double value, Double weight, String relation) {
        super(weight);
        this.value = value;
        this.relation = relation;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
