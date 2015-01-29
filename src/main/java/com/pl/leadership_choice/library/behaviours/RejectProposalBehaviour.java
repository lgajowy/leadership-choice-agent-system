package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adam on 26.01.15.
 */
public class RejectProposalBehaviour extends OneShotBehaviour {
    Logger logger = LoggerFactory.getLogger(RejectProposalBehaviour.class);
    private ACLMessage msg;
    private AID receiver;
    private Candidacy rejectedCandidacy;

    public RejectProposalBehaviour(Candidacy candidacy) {
        super();
        this.receiver = new AID(candidacy.getPretenderId(), AID.ISGUID);
        this.rejectedCandidacy = candidacy;
    }

    public void action() {
        String content = JsonMapper.createJsonStringFromObject(rejectedCandidacy);
        this.msg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
        this.msg.addReceiver(receiver);
        this.msg.setContent(content);

        logger.info("REJECT_PROPOSAL to " + receiver.getName());
        myAgent.send(msg);
    }
}
