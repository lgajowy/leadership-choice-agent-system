package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adam on 26.01.15.
 */
public class ReceiveProposalResponseBehaviour extends CyclicBehaviour {

    Logger logger = LoggerFactory.getLogger(ReceiveProposalResponseBehaviour.class);
    private ACLMessage msg;

    public void action() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;
        MessageTemplate mtReject = MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL);
        MessageTemplate mtAccept = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);

        this.msg = myAgent.receive(MessageTemplate.or(mtAccept, mtReject));

        if (this.msg == null) {
            block();
        } else {
            Candidacy receivedCandidacy = (Candidacy) JsonMapper.mapJsonStringToObject(msg.getContent(), Candidacy.class);

            if (receivedCandidacy != null) {
                if (mtReject.match(this.msg)) {
                    logger.info("REJECT_PROPOSAL from " + this.msg.getSender().getName());
                    //myAgent.addBehaviour(new ReceiveProposalBehaviour());
                } else if (mtAccept.match(this.msg)) {
                    logger.info("ACCEPT_PROPOSAL from " + this.msg.getSender().getName());
                    myAgent.addBehaviour(new BecomingALeaderBehaviour(receivedCandidacy));
                }
            }
        }
    }
}
