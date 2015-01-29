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
    private MessageTemplate proposeTemplate = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
    private ACLMessage message;

    public void action() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;

        message = myAgent.receive(proposeTemplate);
        if (message == null) {
            block();
        } else {
            logger.info("PROPOSAL from: " + message.getSender().getName());
            Candidacy otherAgentsCandidacy = (Candidacy) JsonMapper.mapJsonStringToObject(message.getContent(), Candidacy.class);

            if (!myAgent.alreadyHasALeader(otherAgentsCandidacy.getGroupId())) {
                if (myAgent.canBecomeLeader(otherAgentsCandidacy.getGroupId())) {
                    if (myScoreIsGreater(myAgent, otherAgentsCandidacy)) {
                        myAgent.addBehaviour(new BecomingALeaderBehaviour(otherAgentsCandidacy));
                    } else if (myScoreIsSmaller(myAgent, otherAgentsCandidacy)) {
                        myAgent.addBehaviour(new AcceptProposalBehaviour(otherAgentsCandidacy));
                    } else if (scoreIsEqual(myAgent, otherAgentsCandidacy)
                            && (!myAgent.getLeader(otherAgentsCandidacy.getGroupId()).equals(otherAgentsCandidacy.getPretenderId()))) {
                        myAgent.addBehaviour(new BecomingALeaderBehaviour(otherAgentsCandidacy));
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
