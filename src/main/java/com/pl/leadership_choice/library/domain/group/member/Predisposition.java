package com.pl.leadership_choice.library.domain.group.member;

/**
 * Created by lukasz on 18.01.15.
 */
public class Predisposition {

    private Boolean canBecomeLeader;

    private Double score;

    public Predisposition(Double score, Boolean canBecomeLeader) {
        this.canBecomeLeader = canBecomeLeader;
        this.score = score;
    }

    public Double getScore() {
        return score;
    }

    public Boolean getCanBecomeLeader() {
        return canBecomeLeader;
    }
}
