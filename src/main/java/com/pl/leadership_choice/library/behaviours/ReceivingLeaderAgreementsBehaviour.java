package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.JsonMapper;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by lukasz on 27.01.15.
 */
public class ReceivingLeaderAgreementsBehaviour extends CyclicBehaviour {

    private Logger logger = LoggerFactory.getLogger(ReceivingLeaderAgreementsBehaviour.class);
    private MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
    private ACLMessage agreeMessage;

    @Override
    public void action() {
        agreeMessage = myAgent.receive(messageTemplate);
        if (agreeMessage == null) {
            block();
        } else {
            logger.info("Adding new subordinates to my suborinates set.");
            addIncomingSubordinates();

            logger.info("Informing my suborinates about our new leader.");
            //TODO: wysłać wiadomość I am the new leader (itp).
        }
    }

    private void addIncomingSubordinates() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;
        Candidacy newSubordinate = (Candidacy) JsonMapper.mapJsonStringToObject(agreeMessage.getContent(), Candidacy.class);
        Set<String> allSubordinatesRuledByNewLeader = newSubordinate.getPretenderSubordinates();
        allSubordinatesRuledByNewLeader.add(newSubordinate.getGroupId());
        myAgent.getCandidacy(newSubordinate.getGroupId()).addNewSubordinates(allSubordinatesRuledByNewLeader);
    }
}
