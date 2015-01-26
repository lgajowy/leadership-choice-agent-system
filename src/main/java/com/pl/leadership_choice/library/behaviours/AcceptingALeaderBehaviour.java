package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.JsonMapper;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lukasz on 26.01.15.
 */
public class AcceptingALeaderBehaviour extends CyclicBehaviour {

    private Logger logger = LoggerFactory.getLogger(AcceptingALeaderBehaviour.class);

    private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);

    private ACLMessage message;

    @Override
    public void action() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;

        message = myAgent.receive(mt);
        if (message == null) {
            block();
        } else {
            Candidacy newLeaderData = (Candidacy) JsonMapper.mapJsonStringToObject(message.getContent(), Candidacy.class);
            myAgent.setLeader(newLeaderData);

            //todo: tell my subordinates that we have new leader.
        }
    }
}
