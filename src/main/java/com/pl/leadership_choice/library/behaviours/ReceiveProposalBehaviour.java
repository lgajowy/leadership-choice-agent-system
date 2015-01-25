package com.pl.leadership_choice.library.behaviours;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.JsonMapper;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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

            Candidacy candidacy;



            try{
                candidacy = new ObjectMapper().readValue(msg.getContent(), Candidacy.class);

                //do i have a leader?
                if(((LeadershipChoiceAgent)myAgent).getGroupMembershipRegistrar().getGroupMemberships().get(candidacy.getGroupId()).getCurrentLeaderAID().equals(myAgent.getAID())) {
                    //no, i don't have one
                    //should we compare our scores?
                    if (((LeadershipChoiceAgent)myAgent).canAgentBecomeLeader(candidacy.getGroupId())){
                        //lets check our scores
                        if (((LeadershipChoiceAgent)myAgent).getGroupMembershipRegistrar().
                                getGroupMemberships().get(candidacy.getGroupId()).
                                getPredisposition().getScore() >= candidacy.getPretenderScore()) {
                            //i have better score - i will be your leader

                        } else {
                            //you have better score - i agree you will be my leader
                        }
                    }
                    else{
                        //I cannot be leader in this group
                        //i agree that pretender will be mine
                    }
                } else {
                    //yes, i do already have a leader - rejecting the offer
                }


            } catch (IOException e) {
                logger.error("Couldn't parse configuration file!");
                e.printStackTrace();
            }
        }
    }
}
