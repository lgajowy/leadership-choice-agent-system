package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adam on 26.01.15.
 */
public class RejectProposalBehaviour extends SimpleBehaviour {
    Logger logger = LoggerFactory.getLogger(RejectProposalBehaviour.class);
    private ACLMessage msg;
    private AID receiver;
    private Candidacy rejectedCandidacy;
    private boolean done;

    public RejectProposalBehaviour(Candidacy candidacy) {
        super();
        this.receiver = new AID(candidacy.getPretenderId(), AID.ISGUID);
        this.rejectedCandidacy = candidacy;
        this.done = false;
    }

    public void action() {
        //logger.info(this.getClass().getName() + " START");

        String content = JsonMapper.createJsonStringFromObject(rejectedCandidacy);

        this.msg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
        this.msg.addReceiver(receiver);
        this.msg.setContent(content);

        logger.info("REJECT_PROPOSAL to " + receiver.getName());
        myAgent.send(msg);

        this.done = true;
    }

    public boolean done() {
        return this.done;
    }
}
