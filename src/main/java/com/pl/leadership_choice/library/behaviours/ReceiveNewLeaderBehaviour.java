package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lukasz on 26.01.15.
 */
public class ReceiveNewLeaderBehaviour extends CyclicBehaviour {

    private Logger logger = LoggerFactory.getLogger(ReceiveNewLeaderBehaviour.class);

    private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);

    private ACLMessage message;

    @Override
    public void action() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;

        //logger.info(this.getClass().getSimpleName() + " START");

        message = myAgent.receive(mt);
        if (message == null) {
            block();
        } else {
            Candidacy newLeaderData = (Candidacy) JsonMapper.mapJsonStringToObject(message.getContent(), Candidacy.class);


            if(message.getSender().equals(myAgent.getLeader(newLeaderData.getGroupId()))) {
                //to jest wiadomość od mojego dotychczasowego lidera - przyjmuje wszystko
                myAgent.setLeader(newLeaderData);
                logger.info("INFORM from "
                            + message.getSender().getName()
                            +  " My new leader is "
                            + myAgent.getLeader(newLeaderData.getGroupId()).getPretenderId());

            } else {
                //jakis inny kolo sle mi informy - niech spada

                //logger.info("Ignoring INFORM msg. You are not my leader, " + newLeaderData.getPretenderId());
            }
        }
    }
}
