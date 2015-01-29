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
 * Created by lukasz on 29.01.15.
 */
public class ReceiveAcceptProposalResponseBehaviour extends CyclicBehaviour {

    private Logger logger = LoggerFactory.getLogger(ReceiveAcceptProposalResponseBehaviour.class);
    private MessageTemplate acceptProposalMessageTemplate = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
    private ACLMessage message;

    @Override
    public void action() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;

        message = myAgent.receive(acceptProposalMessageTemplate);
        if (this.message == null) {
            block();
        } else {
            Candidacy receivedCandidacy = (Candidacy) JsonMapper.mapJsonStringToObject(message.getContent(), Candidacy.class);
            if (receivedCandidacy != null) {
                logger.info("ACCEPT_PROPOSAL from " + this.message.getSender().getName());
                myAgent.addBehaviour(new BecomingALeaderBehaviour(receivedCandidacy));
            }
        }
    }
}
