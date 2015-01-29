package com.pl.leadership_choice.library;

import com.pl.leadership_choice.library.behaviours.LeaderQueryResponseGettingBehaviour;
import com.pl.leadership_choice.library.behaviours.messages.GroupIdOnlyContent;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lukasz on 27.01.15.
 */
public class AskingForLeaderExampleAgent extends Agent {

    Logger logger = LoggerFactory.getLogger(AskingForLeaderExampleAgent.class);

    protected void setup() {
        String queryReceiver = String.valueOf(getArguments()[0]);
        String groupId = String.valueOf(getArguments()[1]);
        if (queryReceiver != null && groupId != null) {
            ACLMessage isLeaderElectedQuestion = new ACLMessage(ACLMessage.QUERY_IF);
            isLeaderElectedQuestion.addReceiver(new AID(queryReceiver, AID.ISGUID));

            GroupIdOnlyContent request = new GroupIdOnlyContent(groupId);
            String jsonStringFromObject = JsonMapper.createJsonStringFromObject(request);
            isLeaderElectedQuestion.setContent(jsonStringFromObject);
            logger.info("Asking whether the agent has been elected and who is he...");
            this.send(isLeaderElectedQuestion);

            addBehaviour(new LeaderQueryResponseGettingBehaviour());
        } else {
            throw new RuntimeException("NO group id or quiery receiver provided!");
        }
        //doDelete();
    }
}
