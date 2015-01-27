package com.pl.leadership_choice.library;


import com.pl.leadership_choice.library.behaviours.messages.LeadershipChoiceRequest;
import com.pl.leadership_choice.library.infrastructure.configuration.FileReader;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * Created by lukasz on 27.01.15.
 */
public class GroupRequestSendingAgent extends Agent {

    Logger logger = LoggerFactory.getLogger(GroupRequestSendingAgent.class);


    LeadershipChoiceRequest requestContent;

    protected void setup() {
        try {
            String requestString = FileReader.readFile(String.valueOf(getArguments()[0]), StandardCharsets.UTF_8);
            requestContent = (LeadershipChoiceRequest) JsonMapper.mapJsonStringToObject(requestString, LeadershipChoiceRequest.class);
            if (requestContent != null) {
                ACLMessage choiceRequest = prepareLeadershipChoiceRequestMessage(requestString);
                logger.info("sending group leader choice request.");
                this.send(choiceRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
            doDelete();
        }
        doDelete();
    }

    private ACLMessage prepareLeadershipChoiceRequestMessage(String requestString) {
        ACLMessage choiceRequest = new ACLMessage(ACLMessage.REQUEST);
        addMessageReceivers(requestContent.getGroupMembers(), choiceRequest);
        choiceRequest.setContent(requestString);
        return choiceRequest;
    }

    private void addMessageReceivers(Set<String> reveivers, ACLMessage choiceRequest) {
        for (String aid : reveivers) {
            choiceRequest.addReceiver(new AID(aid, AID.ISGUID));
        }
    }
}
