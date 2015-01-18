package com.pl.leadership_choice.library.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adam on 18.01.15.
 */
public class ReceiveProposalBehaviour extends CyclicBehaviour {

    private MessageTemplate mt=MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
    private ACLMessage msg;

    Logger logger = LoggerFactory.getLogger(ReceiveProposalBehaviour.class);

    public void action()
    {
        logger.info(myAgent.getName() + " ReceiveRequestBehaviour START");

        msg = myAgent.receive(mt);

        if(msg == null)
        {
            block();
            //return;
        }
        else
        {
            logger.info("otrzymano PROPOSE: " + msg.getContent() + " od: " + msg.getSender().getName());
        }
    }
}
