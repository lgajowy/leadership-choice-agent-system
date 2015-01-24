package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by adam on 18.01.15.
 */
public class SendProposalsToAll extends SimpleBehaviour {

    Logger logger = LoggerFactory.getLogger(SendProposalsToAll.class);
    //private MessageTemplate mt=MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

    private ACLMessage msg;

    public void action() {
        LeadershipChoiceAgent agent = (LeadershipChoiceAgent) this.myAgent;

        msg = new ACLMessage(ACLMessage.PROPOSE);

        // TODO FIXME: podać id grupy, która nas interesuje.
        // TODO FIXME:Wcześniej istniało błędne załozenie ze jest tylko jedna grupa do której należy agent.
        Set<String> members = agent.getGroupRegistrar().getMembersForId("xxx");
        msg.setContent("PROPOZYCJA");

        for (String s : members) {
            if (!s.equals(myAgent.getName()))
                msg.addReceiver(new AID(s, AID.ISGUID));
        }

        logger.info(myAgent.getName() + " Rozsyłam do wszystkich " + msg.getContent());
        myAgent.send(msg);
    }

    public boolean done() {
        return true;
    }
}
