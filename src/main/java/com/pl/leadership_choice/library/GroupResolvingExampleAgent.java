package com.pl.leadership_choice.library;

import com.pl.leadership_choice.library.behaviours.messages.GroupIdOnlyContent;
import com.pl.leadership_choice.library.behaviours.messages.GroupResolvingRequest;
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
 * Created by lukasz on 29.01.15.
 */
public class GroupResolvingExampleAgent extends Agent {
    Logger logger = LoggerFactory.getLogger(GroupResolvingExampleAgent.class);

    GroupResolvingRequest request;

    protected void setup() {
        try {
            String requestString = FileReader.readFile(String.valueOf(getArguments()[0]), StandardCharsets.UTF_8);
            request = (GroupResolvingRequest) JsonMapper.mapJsonStringToObject(requestString, GroupResolvingRequest.class);
            if (request != null) {
                ACLMessage resolveGroupRequest = prepareGroupResolveRequestMessage();
                logger.info("sending group resolving request.");
                this.send(resolveGroupRequest);
            }

        } catch (IOException e) {
            e.printStackTrace();
            doDelete();
        }
    }

    private ACLMessage prepareGroupResolveRequestMessage() {
        ACLMessage choiceRequest = new ACLMessage(ACLMessage.CANCEL);
        addMessageReceivers(request.getGroupMembers(), choiceRequest);
        String jsonStringFromObject = JsonMapper.createJsonStringFromObject(new GroupIdOnlyContent(request.getGroupId()));
        choiceRequest.setContent(jsonStringFromObject);
        return choiceRequest;
    }

    private void addMessageReceivers(Set<String> receivers, ACLMessage request) {
        for (String aid : receivers) {
            request.addReceiver(new AID(aid, AID.ISGUID));
        }
    }

}
