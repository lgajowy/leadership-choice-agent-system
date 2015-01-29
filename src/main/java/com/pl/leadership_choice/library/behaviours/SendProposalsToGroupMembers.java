package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by adam on 18.01.15.
 */
public class SendProposalsToGroupMembers extends OneShotBehaviour {

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
        msg.setContent(JsonMapper.createJsonStringFromObject(agentCandidacy));
        addMessageReceivers();

        logger.info("PROPOSAL to all members except myself.");
        myAgent.send(msg);
    }

    private void addMessageReceivers() {
        Set<String> members = ((LeadershipChoiceAgent) myAgent).getGroupRegistrar().getMembersForGroupsId(groupId);
        for (String s : members) {
            if (!s.equals(myAgent.getName())) {
                msg.addReceiver(new AID(s, AID.ISGUID));
            }
        }
    }
}
