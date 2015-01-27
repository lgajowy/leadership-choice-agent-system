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
public class AcceptProposalBehaviour extends SimpleBehaviour {

    Logger logger = LoggerFactory.getLogger(AcceptProposalBehaviour.class);
    //private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL);
    private ACLMessage msg;
    private AID receiver;
    private Candidacy acceptedCandidacy;

    public AcceptProposalBehaviour(Candidacy acceptedCandidacy) {
        super();
        this.receiver = new AID(acceptedCandidacy.getPretenderId(), AID.ISGUID);
        this.acceptedCandidacy = acceptedCandidacy;
    }

    public void action() {
        logger.info(this.getClass().getName() + " START");

        String content = JsonMapper.createJsonFromObject(acceptedCandidacy);

        this.msg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
        this.msg.addReceiver(receiver);
        this.msg.setContent(content);

        logger.info("Sending ACCEPT_PROPOSAL to "+ receiver.getName());
        myAgent.send(msg);
    }

    public boolean done() {
        return true;
    }
}
