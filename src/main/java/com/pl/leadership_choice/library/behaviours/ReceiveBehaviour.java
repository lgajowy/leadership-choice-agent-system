package com.pl.leadership_choice.library.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adam on 18.01.15.
 */
public class ReceiveBehaviour extends CyclicBehaviour {

    private MessageTemplate mt=MessageTemplate.MatchPerformative(ACLMessage.INFORM);
    private ACLMessage msg;
    Logger logger = LoggerFactory.getLogger(ReceiveBehaviour.class);

    public void action()
    {
        msg = myAgent.receive(mt);

        if(msg == null)
        {
            block();
            //return;
        }
        else
        {
            logger.info("Odebrano widaomość o treści: " + msg.getContent()
                    + ". Nadawca : " + msg.getSender().getName());
        }
    }

    private boolean canIBeLeader()
    {
        return true;
    }
}
