package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.agent.leader_choice_request.LeadershipChoiceRequest;
import com.pl.leadership_choice.library.agent.leader_choice_request.LeadershipChoiceRequestMapper;
import com.pl.leadership_choice.proof_of_concept.LeadershipChoiceAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by adam on 18.01.15.
 */
public class ReceiveRequestBehaviour extends CyclicBehaviour {
    private MessageTemplate mt=MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
    private ACLMessage msg;
    private LeadershipChoiceRequest request;
    Logger logger = LoggerFactory.getLogger(ReceiveRequestBehaviour.class);


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
            logger.info("Odebrano REQUEST: " + msg.getContent()
                    + ". Nadawca : " + msg.getSender().getName());

            LeadershipChoiceRequestMapper mapper;
            mapper = new LeadershipChoiceRequestMapper(msg.getContent());
            request = mapper.mapRequest();

            ((LeadershipChoiceAgent)myAgent).setGroupId(request.getGroupId());
            ((LeadershipChoiceAgent)myAgent).setGroupMembers(request.getGroupMembers());
            ((LeadershipChoiceAgent)myAgent).setMandatoryFeatures(request.getMandatoryFeatures());
            ((LeadershipChoiceAgent)myAgent).setOptionalFeatures(request.getOptionalFeatures());

            //logger.info("Id grupy z komunikatu: " + ((LeadershipChoiceAgent)myAgent).groupId);
            //logger.info((String) ((LeadershipChoiceAgent)myAgent).groupMembers.get(0));
            //logger.info((String) ((LeadershipChoiceAgent)myAgent).mandatoryFeatures.get(0));
            //logger.info((String) ((LeadershipChoiceAgent)myAgent).optionalFeatures.get(0).getValue());

            if(((LeadershipChoiceAgent) myAgent).canBeLeader())
            {
                //może być liderem

                //zapytaj wszystkich
                myAgent.addBehaviour(new SendProposalsToAll());
                myAgent.addBehaviour(new ReceiveProposalBehaviour());
            }
            else
            {
                //nie może być liderem

                //czekaj aż ktoś Cię zapyta
            }
        }
    }


}
