package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.agent.leader_choice_request.LeadershipChoiceRequest;
import com.pl.leadership_choice.library.agent.leader_choice_request.LeadershipChoiceRequestMapper;
import com.pl.leadership_choice.library.group.Group;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adam on 18.01.15.
 */
public class ReceiveRequestBehaviour extends CyclicBehaviour {

    private Logger logger = LoggerFactory.getLogger(ReceiveRequestBehaviour.class);

    private MessageTemplate requestMessageTemplate = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

    private ACLMessage message;

    private LeadershipChoiceRequest request;




    public void action() {
        LeadershipChoiceAgent agent = (LeadershipChoiceAgent)this.myAgent;

        logger.info(myAgent.getName() + " ReceiveRequestBehaviour START");

        message = myAgent.receive(requestMessageTemplate);
        if (message == null) {
            block();
        } else {
            logger.info("Received REQUEST: " + message.getContent() + ". From : " + message.getSender().getName());
            request = new LeadershipChoiceRequestMapper(message.getContent()).mapRequest();

            Group newlyRegisteredGroup = new Group(request.getGroupMembers(), request.getMandatoryFeatures(), request.getOptionalFeatures());
            agent.getGroupRegistrar().registerGroup(request.getGroupId(), newlyRegisteredGroup);

            if (((LeadershipChoiceAgent) myAgent).canAgentBecomeLeader(request.getGroupId())) {
                //może być liderem

                //zapytaj wszystkich
                myAgent.addBehaviour(new SendProposalsToAll());
                myAgent.addBehaviour(new ReceiveProposalBehaviour());
            } else {
                //nie może być liderem

                //czekaj aż ktoś Cię zapyta
            }
        }
    }


}
