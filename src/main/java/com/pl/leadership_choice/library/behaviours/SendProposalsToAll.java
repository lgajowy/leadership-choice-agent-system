package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.proof_of_concept.LeadershipChoiceAgent;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by adam on 18.01.15.
 */
public class SendProposalsToAll extends SimpleBehaviour {

    //private MessageTemplate mt=MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
    private ACLMessage msg;
    Logger logger = LoggerFactory.getLogger(SendProposalsToAll.class);

    public void action()
    {
        msg = new ACLMessage(ACLMessage.PROPOSE);
        ArrayList<String> members = ((LeadershipChoiceAgent)myAgent).getGroupMembers();
        msg.setContent("PROPOZYCJA");

        for(String s: members)
        {
            if(!s.equals(myAgent.getName()))
                msg.addReceiver(new AID(s, AID.ISGUID));
        }

        logger.info(myAgent.getName() + " Rozsyłam do wszystkich " + msg.getContent());
        myAgent.send(msg);
    }

    public boolean done()
    {
        return true;
    }
}
