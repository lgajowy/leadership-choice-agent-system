package com.pl.leadership_choice.library.domain.group.member;

import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import jade.core.AID;

/**
 * Created by lukasz on 25.01.15.
 */
public class GroupMembership {

    private String id;

    private Predisposition predisposition;

    private Candidacy memberCandidacy = null;

    public GroupMembership(String id, Predisposition predisposition) {
        this.id = id;
        this.predisposition = predisposition;
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

    public Candidacy getMemberCandidacy() {
        return memberCandidacy;
    }

    public void setMemberCandidacy(Candidacy memberCandidacy) {
        this.memberCandidacy = memberCandidacy;
    }
}
