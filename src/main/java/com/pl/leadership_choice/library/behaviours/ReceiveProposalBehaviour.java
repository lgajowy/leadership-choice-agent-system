package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.JsonMapper;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adam on 18.01.15.
 */
public class ReceiveProposalBehaviour extends CyclicBehaviour {

    Logger logger = LoggerFactory.getLogger(ReceiveProposalBehaviour.class);
    private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
    private ACLMessage msg;
    private String agentName;

    public void action() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;

        agentName = myAgent.getAID().getName();
        logger.debug(agentName + ": " + this.getClass().getName() + "STARTED");

        msg = myAgent.receive(mt);
        if (msg == null) {
            block();
        } else {
            logger.info(agentName + ": PROPOSAL message received: " + msg.getContent() + " from: " + msg.getSender().getName());
            Candidacy otherAgentsCandidacy = (Candidacy) JsonMapper.mapJsonStringToObject(msg.getContent(), Candidacy.class);

            if (myAgent.alreadyHasALeader(otherAgentsCandidacy.getGroupId())) {
                logger.info(agentName + ": already has leader. Declines: , " + otherAgentsCandidacy.getPretenderId());
                //FIXME: wysłać wiadomość REJECT??
            } else {
                if (myAgent.canBecomeLeader(otherAgentsCandidacy.getGroupId())) {
                    //TODO: if scores are equal, they both consider themselves leaders. (>=)

                    if(myAgent.getCandidacy(otherAgentsCandidacy.getGroupId()).compareTo(otherAgentsCandidacy) == 1) {
                        // i become leader
                    } else if (myAgent.getCandidacy(otherAgentsCandidacy.getGroupId()).compareTo(otherAgentsCandidacy) == -1) {
                        // he becomes leader
                    } else if (myAgent.getCandidacy(otherAgentsCandidacy.getGroupId()).compareTo(otherAgentsCandidacy) == 0) {
                        // we shuffle who becomes leader
                    }
                } else {
                    // accept him as a leader
                }
            }
        }
    }
}
