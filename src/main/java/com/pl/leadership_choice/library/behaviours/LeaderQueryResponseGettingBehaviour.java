package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.behaviours.messages.LeaderResponse;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lukasz on 27.01.15.
 */
public class LeaderQueryResponseGettingBehaviour extends CyclicBehaviour {

    private Logger logger = LoggerFactory.getLogger(LeaderQueryResponseGettingBehaviour.class);
    private ACLMessage message;
    private MessageTemplate responseToQueryTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);


    @Override
    public void action() {
        message = myAgent.receive(responseToQueryTemplate);
        if (message == null) {
            block();
        } else {
            LeaderResponse chosenLeader = (LeaderResponse) JsonMapper.mapJsonStringToObject(message.getContent(), LeaderResponse.class);
            if (chosenLeader != null) {
                if (chosenLeader.getLeaderId() == null) {
                    logger.info("Leader has not been chosen yet");
                } else {
                    logger.info(chosenLeader.getGroupId() + "'s group leader is: " + chosenLeader.getLeaderId());
                }
            } else {
                logger.info("Asked agent does not know such group.");
            }
        }
    }
}
