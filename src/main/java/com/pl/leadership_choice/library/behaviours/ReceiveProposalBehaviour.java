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
 * Created by adam on 18.01.15.
 */
public class ReceiveProposalBehaviour extends CyclicBehaviour {

    private Logger logger = LoggerFactory.getLogger(ReceiveProposalBehaviour.class);
    private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
    private ACLMessage msg;

    public synchronized void action() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;

        msg = myAgent.receive(mt);
        if (msg == null) {
            block();
        } else {
            logger.info("PROPOSAL from: " + msg.getSender().getName());
            Candidacy otherAgentsCandidacy = (Candidacy) JsonMapper.mapJsonStringToObject(msg.getContent(), Candidacy.class);

            if (myAgent.alreadyHasALeader(otherAgentsCandidacy.getGroupId())) {
                myAgent.addBehaviour(new RejectProposalBehaviour(otherAgentsCandidacy));
            } else {
                if (myAgent.canBecomeLeader(otherAgentsCandidacy.getGroupId())) {
                    if (myScoreIsGreater(myAgent, otherAgentsCandidacy)) {
                        myAgent.addBehaviour(new RejectProposalBehaviour(otherAgentsCandidacy));
                        myAgent.addBehaviour(new BecomingALeaderBehaviour(otherAgentsCandidacy));
                    } else if (myScoreIsSmaller(myAgent, otherAgentsCandidacy)) {
                        myAgent.addBehaviour(new AcceptProposalBehaviour(otherAgentsCandidacy));
                    } else if (scoreIsEqual(myAgent, otherAgentsCandidacy)
                            && (!myAgent.getLeader(otherAgentsCandidacy.getGroupId()).equals(otherAgentsCandidacy.getPretenderId()))) {
                        //we need to check whether he is not our leader already
                    }
                } else {
                    myAgent.addBehaviour(new AcceptProposalBehaviour(otherAgentsCandidacy));
                }
            }
        }

    }

    private boolean scoreIsEqual(LeadershipChoiceAgent myAgent, Candidacy otherAgentsCandidacy) {
        return myAgent.getCandidacy(otherAgentsCandidacy.getGroupId()).compareTo(otherAgentsCandidacy) == 0;
    }

    private boolean myScoreIsSmaller(LeadershipChoiceAgent myAgent, Candidacy otherAgentsCandidacy) {
        return myAgent.getCandidacy(otherAgentsCandidacy.getGroupId()).compareTo(otherAgentsCandidacy) == -1;
    }

    private boolean myScoreIsGreater(LeadershipChoiceAgent myAgent, Candidacy otherAgentsCandidacy) {
        return myAgent.getCandidacy(otherAgentsCandidacy.getGroupId()).compareTo(otherAgentsCandidacy) == 1;
    }

}
