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
 * Created by adam on 26.01.15.
 */
public class ReceiveProposalResponseBehaviour extends CyclicBehaviour {

    Logger logger = LoggerFactory.getLogger(ReceiveProposalResponseBehaviour.class);
    private MessageTemplate mt;
    private ACLMessage msg;
    //private String groupId;


    public void action() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;

        logger.debug(this.getClass().getName() + " STARTED");

        MessageTemplate mtReject = MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL);
        MessageTemplate mtAccept = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);

        this.mt.or(mtAccept, mtReject);

        this.msg = myAgent.receive(this.mt);

        if(this.msg != null) {
            //odebrano komunikat
            Candidacy receivedCandidacy = (Candidacy) JsonMapper.mapJsonStringToObject(msg.getContent(), Candidacy.class);

            if(mtReject.match(this.msg)) {
                //odebrano REJECT_PROPOSAL
                logger.info("odebralem REJECT_PROPOSAL od " + this.msg.getSender().getName() + " tresc: " + this.msg.getContent());
            } else {
                //odebrano ACCEPT_PROPOSAL
                logger.info("odebralem ACCEPT_PROPOSAL od " + this.msg.getSender().getName() + " tresc: " + this.msg.getContent());
                myAgent.addBehaviour(new BecomingALeaderBehaviour(myAgent.getCandidacy(receivedCandidacy.getGroupId())));
            }
        } else {
            block();
        }
    }
}
