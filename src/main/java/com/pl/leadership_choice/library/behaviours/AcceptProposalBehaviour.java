package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by adam on 26.01.15.
 */
public class AcceptProposalBehaviour extends OneShotBehaviour {

    Logger logger = LoggerFactory.getLogger(AcceptProposalBehaviour.class);

    private ACLMessage msg;
    private AID receiver;
    private Candidacy acceptedCandidacy;

    public AcceptProposalBehaviour(Candidacy acceptedCandidacy) {
        super();
        this.receiver = new AID(acceptedCandidacy.getPretenderId(), AID.ISGUID);
        this.acceptedCandidacy = acceptedCandidacy;
    }

    public void action() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent)this.myAgent;

        String content = JsonMapper.createJsonStringFromObject(myAgent.getCandidacy(acceptedCandidacy.getGroupId()));

        this.msg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
        this.msg.addReceiver(receiver);
        this.msg.setContent(content);

        //tell my subordinates (if i have any) that we have new leader.
        if(myAgent.getCandidacy(acceptedCandidacy.getGroupId()) != null
                && myAgent.getCandidacy(acceptedCandidacy.getGroupId()).getPretenderSubordinates() != null
                && myAgent.getCandidacy(acceptedCandidacy.getGroupId()).getPretenderSubordinates().size() > 0
                ) {
            ACLMessage informMsg = new ACLMessage(ACLMessage.INFORM);
            content = JsonMapper.createJsonStringFromObject(acceptedCandidacy);
            informMsg.setContent(content);
            Set<String> subordinates = myAgent.getCandidacy(acceptedCandidacy.getGroupId()).getPretenderSubordinates();

            for (String s : subordinates) {
                informMsg.addReceiver(new AID(s, AID.ISGUID));
            }
            logger.info("INFORM to my subordinates (there is new leader around)");
            myAgent.send(informMsg);
        }
        logger.info("ACCEPT_PROPOSAL to " + receiver.getName());
        myAgent.setLeader(acceptedCandidacy);
        myAgent.send(msg);
    }
}
