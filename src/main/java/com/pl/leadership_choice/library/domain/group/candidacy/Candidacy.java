package com.pl.leadership_choice.library.domain.group.candidacy;

import com.google.common.base.Optional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lukasz on 25.01.15.
 */
public class Candidacy implements Comparable<Candidacy> {

    private String pretenderId;

    private String groupId;

    private Double pretenderScore;

    private Set<String> pretenderSubordinates;

    public Candidacy() {

    }

    public Candidacy(String pretenderId, String groupId, Double pretenderScore, Set<String> pretenderSubordinates) {
        this.pretenderId = pretenderId;
        this.groupId = groupId;
        this.pretenderScore = pretenderScore;
        this.pretenderSubordinates = Optional.fromNullable(pretenderSubordinates).or(new HashSet<String>());
    }

    public String getPretenderId() {
        return pretenderId;
    }

    public void setPretenderId(String pretenderId) {
        this.pretenderId = pretenderId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    @Override
    public int compareTo(Candidacy other) {
        if(!this.getGroupId().equals(other.getGroupId())) {
            throw new RuntimeException("Group ids differ in compared Candidacies");
        }

        if (this.getPretenderScore() > other.getPretenderScore())
            return 1;
        else if (this.getPretenderScore() < other.getPretenderScore())
            return -1;
        else
            return 0;
    }
}
