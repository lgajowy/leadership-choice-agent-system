package com.pl.leadership_choice.library.domain.group.member;

import jade.core.AID;

/**
 * Created by lukasz on 25.01.15.
 */
public class GroupMember {

    private String id;

    private Predisposition predisposition;

    private AID currentLeaderAID;

    public GroupMember(String id, Predisposition predisposition, AID currentLeaderAID) {
        this.id = id;
        this.predisposition = predisposition;
        this.currentLeaderAID = currentLeaderAID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Predisposition getPredisposition() {
        return predisposition;
    }

    public void setPredisposition(Predisposition predisposition) {
        this.predisposition = predisposition;
    }

    public AID getCurrentLeaderAID() {
        return this.currentLeaderAID;
    }

    public void setCurrentLeaderAID(AID currentLeaderAID) {
        this.currentLeaderAID = currentLeaderAID;
    }


}
