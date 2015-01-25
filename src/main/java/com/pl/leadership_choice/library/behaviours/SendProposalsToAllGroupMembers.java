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
public class SendProposalsToAllGroupMembers extends SimpleBehaviour {

    Logger logger = LoggerFactory.getLogger(SendProposalsToAllGroupMembers.class);

    LeadershipChoiceAgent agent = (LeadershipChoiceAgent) this.myAgent;

    private ACLMessage msg;

    private String groupId = null;

    public SendProposalsToAllGroupMembers(String groupId) {
        super();
        this.groupId = groupId;
    }

    public void action() {
        Candidacy agentCandidacy = new Candidacy(agent.getAID().getName(),
                agent.getGroupMembershipRegistrar().getGroupMemberships().get(groupId).getPredisposition().getScore(), null);

        msg = new ACLMessage(ACLMessage.PROPOSE);
        msg.setContent(JsonMapper.createJsonFromObject(agentCandidacy));
        addMessageReceivers();

        logger.info(agent.getAID().getName() + ": Sending proposal message to all members..." + msg.getContent());
        myAgent.send(msg);
    }

    private void addMessageReceivers() {
        Set<String> members = agent.getGroupRegistrar().getMembersForGroupsId(groupId);
        for (String s : members) {
            if (!s.equals(myAgent.getName()))
                msg.addReceiver(new AID(s, AID.ISGUID));
        }
    }

    public boolean done() {
        return true;
    }
}
