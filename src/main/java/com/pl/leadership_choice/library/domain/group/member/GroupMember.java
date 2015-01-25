package com.pl.leadership_choice.library.domain.group.member;

/**
 * Created by lukasz on 25.01.15.
 */
public class GroupMember {

    private String id;

    private Predisposition predisposition;

    public GroupMember(String id, Predisposition predisposition) {
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
}
