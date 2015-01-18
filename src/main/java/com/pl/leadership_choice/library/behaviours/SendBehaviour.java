package com.pl.leadership_choice.library.behaviours;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adam on 18.01.15.
 */
public class SendBehaviour extends SimpleBehaviour {

    private MessageTemplate mt=MessageTemplate.MatchPerformative(ACLMessage.INFORM);
    private ACLMessage msg;
    Logger logger = LoggerFactory.getLogger(SendBehaviour.class);

    public void action()
    {
        msg = new ACLMessage(ACLMessage.INFORM);

        msg.setContent("TREŚĆ WIADOMOŚCI: DUPA");
        msg.addReceiver(new AID("leadershipChoiceAgent1@Y580:1099/JADE", AID.ISLOCALNAME));
        //logger.info(msg.getContent());
        myAgent.send(msg);
    }

    public boolean done()
    {
        return true;
    }
}
