package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.JsonMapper;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by adam on 18.01.15.
 */
public class SendProposalsToGroupMembers extends SimpleBehaviour {

    Logger logger = LoggerFactory.getLogger(SendProposalsToGroupMembers.class);

    private ACLMessage msg;

    private String groupId = null;

    public SendProposalsToGroupMembers(String groupId) {
        super();
        this.groupId = groupId;
    }

    public void action() {
        Double candidateScore = ((LeadershipChoiceAgent) myAgent).getGroupMembershipRegistrar()
                .getGroupMemberships().get(groupId).getPredisposition().getScore();
        Candidacy agentCandidacy = new Candidacy(myAgent.getAID().getName(), groupId, candidateScore, null);

        msg = new ACLMessage(ACLMessage.PROPOSE);
        msg.setContent(JsonMapper.createJsonFromObject(agentCandidacy));
        addMessageReceivers();

        logger.info(myAgent.getAID().getName() + ": Sending proposal message to all members..." + msg.getContent());
        myAgent.send(msg);
        myAgent.addBehaviour(new ReceiveProposalResponseBehaviour());
    }

    private void addMessageReceivers() {
        Set<String> members = ((LeadershipChoiceAgent) myAgent).getGroupRegistrar().getMembersForGroupsId(groupId);
        for (String s : members) {
            if (!s.equals(myAgent.getName())) {
                logger.info("my name: "+myAgent.getName()+", his name: " + s);
                msg.addReceiver(new AID(s, AID.ISGUID));
            }
        }
    }

    public boolean done() {
        return true;
    }
}
