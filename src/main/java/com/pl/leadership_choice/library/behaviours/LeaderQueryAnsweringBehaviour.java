package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.behaviours.messages.GroupIdOnlyContent;
import com.pl.leadership_choice.library.behaviours.messages.LeaderResponse;
import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lukasz on 28.01.15.
 */
public class LeaderQueryAnsweringBehaviour extends CyclicBehaviour {

    private Logger logger = LoggerFactory.getLogger(LeaderQueryAnsweringBehaviour.class);
    private ACLMessage incomingMessage;
    private MessageTemplate queryTemplate = MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF);

    @Override
    public void action() {
        incomingMessage = myAgent.receive(queryTemplate);
        if (incomingMessage == null) {
            block();
        } else {
            LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;
            GroupIdOnlyContent leaderRequest = (GroupIdOnlyContent) JsonMapper.mapJsonStringToObject(incomingMessage.getContent(), GroupIdOnlyContent.class);
            Candidacy groupLeader = myAgent.getLeader(leaderRequest.getGroupId());

            ACLMessage responseMessage = new ACLMessage(ACLMessage.INFORM);
            responseMessage.addReceiver(incomingMessage.getSender());
            assignContentAccordingToLeaderPresence(leaderRequest, groupLeader, responseMessage);
            myAgent.send(responseMessage);
        }
    }

    private void assignContentAccordingToLeaderPresence(GroupIdOnlyContent leaderRequest, Candidacy groupLeader, ACLMessage responseMessage) {
        LeaderResponse responseContent;
        if(groupLeader != null) {
            responseContent = new LeaderResponse(groupLeader.getGroupId(), groupLeader.getPretenderId());
            responseMessage.setContent(JsonMapper.createJsonStringFromObject(responseContent));
        } else {
            responseContent = new LeaderResponse(leaderRequest.getGroupId(), null);
            responseMessage.setContent(JsonMapper.createJsonStringFromObject(responseContent));
        }
    }
}

