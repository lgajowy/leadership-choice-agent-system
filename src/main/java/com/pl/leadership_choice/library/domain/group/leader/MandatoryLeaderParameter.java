package com.pl.leadership_choice.library.domain.group.leader;

/**
 * Created by lukasz on 18.01.15.
 */
public class MandatoryLeaderParameter {

    private Double value;

    private Double weight;

    private String relation;

    public MandatoryLeaderParameter() {
    }

    public MandatoryLeaderParameter(Double value, Double weight, String relation) {
        this.value = value;
        this.weight = weight;
        this.relation = relation;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
