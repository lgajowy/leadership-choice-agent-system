package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.JsonMapper;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adam on 26.01.15.
 */
public class RejectProposalBehaviour extends SimpleBehaviour {
    Logger logger = LoggerFactory.getLogger(RejectProposalBehaviour.class);
    //private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL);
    private ACLMessage msg;
    private AID receiver;
    private Candidacy rejectedCandidacy;

    public RejectProposalBehaviour(Candidacy candidacy) {
        super();
        this.receiver = new AID(candidacy.getPretenderId(), AID.ISGUID);
        this.rejectedCandidacy = candidacy;
    }
    public void action() {
        logger.info(this.getClass().getName() + " START");


        String content = JsonMapper.createJsonFromObject(rejectedCandidacy);

        this.msg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
        this.msg.addReceiver(receiver);
        this.msg.setContent(content);

        logger.info("Sending REJECT_PROPOSAL to "+ receiver.getName());
        myAgent.send(msg);
    }
    public boolean done() {
        return true;
    }
}
