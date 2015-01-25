package com.pl.leadership_choice.library.domain.group.candidacy;

import com.google.common.base.Optional;
import com.pl.leadership_choice.library.domain.group.leader.MandatoryLeaderParameter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lukasz on 25.01.15.
 */
public class Candidacy {

    private String pretenderId;

    private Double pretenderScore;

    private Set<String> pretenderSubordinates;

    public Candidacy(String pretenderId, Double pretenderScore, Set<String> pretenderSubordinates) {
        this.pretenderId = pretenderId;
        this.pretenderScore = pretenderScore;
        this.pretenderSubordinates = Optional.fromNullable(pretenderSubordinates).or(new HashSet<String>());
    }

    public String getPretenderId() {
        return pretenderId;
    }

    public void setPretenderId(String pretenderId) {
        this.pretenderId = pretenderId;
    }

    public Double getPretenderScore() {
        return pretenderScore;
    }

    public void setPretenderScore(Double pretenderScore) {
        this.pretenderScore = pretenderScore;
    }

    public Set<String> getPretenderSubordinates() {
        return pretenderSubordinates;
    }

    public void setPretenderSubordinates(Set<String> pretenderSubordinates) {
        this.pretenderSubordinates = pretenderSubordinates;
    }
}
