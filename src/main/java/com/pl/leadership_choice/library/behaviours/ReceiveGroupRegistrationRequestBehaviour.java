package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.behaviours.messages.LeadershipChoiceRequest;
import com.pl.leadership_choice.library.domain.group.Group;
import com.pl.leadership_choice.library.domain.group.member.GroupMembership;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adam on 18.01.15.
 */
public class ReceiveGroupRegistrationRequestBehaviour extends CyclicBehaviour {

    private Logger logger = LoggerFactory.getLogger(ReceiveGroupRegistrationRequestBehaviour.class);

    private MessageTemplate requestMessageTemplate = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

    private ACLMessage message;

    private LeadershipChoiceRequest request;

    public void action() {
        logger.info(this.getClass().getName() + " START");

        message = myAgent.receive(requestMessageTemplate);
        if (message == null) {
            block();
        } else {
            logger.info("Received REQUEST: " + message.getContent() + ". From : " + message.getSender().getName());
            request = (LeadershipChoiceRequest) JsonMapper.mapJsonStringToObject(message.getContent(), LeadershipChoiceRequest.class);

            GroupMembership agentMembershipInGroup = registerNewGroup();
            if (isAgentTheOnlyGroupMember()) {
                logger.info("Agent is the only member of the group: " + request.getGroupId());
                setAsLeaderIfHeCanBeOne(agentMembershipInGroup);
            } else {
                sendProposalsIfAgentCanBecomeLeader(agentMembershipInGroup);
                myAgent.addBehaviour(new ReceiveProposalBehaviour());
            }
        }
    }

    private boolean isAgentTheOnlyGroupMember() {
        return request.getGroupMembers().size() == 1 && request.getGroupMembers().contains(myAgent.getName());
    }

    private void setAsLeaderIfHeCanBeOne(GroupMembership agentMembershipInGroup) {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;
        if (myAgent.canBecomeLeader(request.getGroupId())) {
            logger.info("Setting agent as the leader of one-agent group.");
            myAgent.setLeader(agentMembershipInGroup.getMemberCandidacy());
        }
    }

    private GroupMembership registerNewGroup() {
        Group newlyRegisteredGroup = registerGroup();
        logger.info(myAgent.getAID().getName() + ": Registered new group with id: " + request.getGroupId());
        GroupMembership agentMembershipInGroup = registerAgentsMembership(newlyRegisteredGroup);
        logger.debug(myAgent.getAID().getName() + ": Registered the agent as a member of the group: " + request.getGroupId());
        return agentMembershipInGroup;
    }

    private void sendProposalsIfAgentCanBecomeLeader(GroupMembership agentMembershipInGroup) {
        if (agentMembershipInGroup.getPredisposition().getCanBecomeLeader()) {
            logger.info(myAgent.getAID().getName() + ": Agent can become leader of group: " + request.getGroupId()
                    + " It's score: " + agentMembershipInGroup.getPredisposition().getScore());
            myAgent.addBehaviour(new SendProposalsToGroupMembers(request.getGroupId()));
        }
    }

    private GroupMembership registerAgentsMembership(Group newlyRegisteredGroup) {
        return ((LeadershipChoiceAgent) myAgent).getGroupMembershipRegistrar().registerAgent(myAgent.getAID().getName(), request.getGroupId(),
                newlyRegisteredGroup.getGroupLeaderRequirements(),
                ((LeadershipChoiceAgent) myAgent).getAgentProperties());
    }

    private Group registerGroup() {
        Group newlyRegisteredGroup = new Group(request.getGroupMembers(),
                request.getMandatoryFeatures(),
                request.getOptionalFeatures());

        ((LeadershipChoiceAgent) myAgent).getGroupRegistrar().registerGroup(request.getGroupId(),
                newlyRegisteredGroup);
        return newlyRegisteredGroup;
    }
}
