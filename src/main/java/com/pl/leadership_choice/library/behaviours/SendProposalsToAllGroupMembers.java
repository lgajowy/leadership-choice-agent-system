package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
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
        msg = new ACLMessage(ACLMessage.PROPOSE);
        msg.setContent("PROPOZYCJA");
        addMessageReceivers();

        logger.info(myAgent.getName() + "Sending proposal message to all members..." + msg.getContent());
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
