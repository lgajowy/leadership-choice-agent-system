package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.behaviours.messages.GroupIdOnlyContent;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lukasz on 29.01.15.
 */
public class GroupResolvingRequestBehaviour extends CyclicBehaviour {

    MessageTemplate groupResolvingMessageTemplate = MessageTemplate.MatchPerformative(ACLMessage.CANCEL);
    private Logger logger = LoggerFactory.getLogger(GroupResolvingRequestBehaviour.class);
    private ACLMessage message;

    public void action() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;
        this.message = myAgent.receive(groupResolvingMessageTemplate);

        if (this.message == null) {
            block();
        } else {
            GroupIdOnlyContent content = (GroupIdOnlyContent) JsonMapper.mapJsonStringToObject(message.getContent(), GroupIdOnlyContent.class);
            if (content != null) {
                myAgent.resolveGroup(content.getGroupId());
                logger.info("Group with id:" + content.getGroupId() + " has been removed from agent memory.");
            }
        }
    }
}
