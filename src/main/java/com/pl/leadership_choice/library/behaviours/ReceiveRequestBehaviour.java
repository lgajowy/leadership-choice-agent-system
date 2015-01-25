package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.infrastructure.leader_choice_request.LeadershipChoiceRequest;
import com.pl.leadership_choice.library.infrastructure.leader_choice_request.LeadershipChoiceRequestMapper;
import com.pl.leadership_choice.library.domain.group.Group;
import com.pl.leadership_choice.library.domain.group.member.GroupMember;
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

    private LeadershipChoiceAgent agent = (LeadershipChoiceAgent) this.myAgent;

    public void action() {
        logger.info(myAgent.getName() + " ReceiveRequestBehaviour START");

        message = myAgent.receive(requestMessageTemplate);
        if (message == null) {
            block();
        } else {
            logger.info(agent.getAID().getName() + ": Received REQUEST: " + message.getContent() + ". From : " + message.getSender().getName());
            request = new LeadershipChoiceRequestMapper(message.getContent()).mapRequest();

            Group newlyRegisteredGroup = registerGroup();
            logger.info(agent.getAID().getName() + ": Registered new group with id: " + request.getGroupId());
            GroupMember agentMembershipInGroup = registerAgentsMembership(newlyRegisteredGroup);
            logger.debug(agent.getAID().getName() + ": Registered the agent as a member of the group: " + request.getGroupId());


            if (agentMembershipInGroup.getPredisposition().getCanBecomeLeader()) {
                logger.info(agent.getAID().getName() + ": Agent can become leader of group: " + request.getGroupId()
                + " It's score: " + agentMembershipInGroup.getPredisposition().getScore());

                logger.info(agent.getAID().getName() + ": Sending proposals to other members... ");
                myAgent.addBehaviour(new SendProposalsToAllGroupMembers(request.getGroupId()));
                myAgent.addBehaviour(new ReceiveProposalBehaviour());
            } else {
                //nie może być liderem

                //czekaj aż ktoś Cię zapyta
            }
        }
    }

    private GroupMember registerAgentsMembership(Group newlyRegisteredGroup) {
        return agent.getGroupMembershipRegistrar().registerAgent(request.getGroupId(),
                newlyRegisteredGroup.getGroupLeaderRequirements(), agent.getAgentProperties());
    }

    private Group registerGroup() {
        Group newlyRegisteredGroup = new Group(request.getGroupMembers(), request.getMandatoryFeatures(), request.getOptionalFeatures());
        agent.getGroupRegistrar().registerGroup(request.getGroupId(), newlyRegisteredGroup);
        return newlyRegisteredGroup;
    }


}
