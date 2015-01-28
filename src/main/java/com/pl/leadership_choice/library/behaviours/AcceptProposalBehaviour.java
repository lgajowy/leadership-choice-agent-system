package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by adam on 26.01.15.
 */
public class AcceptProposalBehaviour extends SimpleBehaviour {

    Logger logger = LoggerFactory.getLogger(AcceptProposalBehaviour.class);

    private ACLMessage msg;
    private AID receiver;
    private Candidacy acceptedCandidacy;
    private boolean done;

    public AcceptProposalBehaviour(Candidacy acceptedCandidacy) {
        super();
        this.receiver = new AID(acceptedCandidacy.getPretenderId(), AID.ISGUID);
        this.acceptedCandidacy = acceptedCandidacy;
        this.done = false;
    }

    public void action() {
        //logger.info(this.getClass().getName() + " START");

        //String content = JsonMapper.createJsonStringFromObject(acceptedCandidacy);
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent)this.myAgent;

        String content = JsonMapper.createJsonStringFromObject(myAgent.getCandidacy(acceptedCandidacy.getGroupId())); //acceptedCandidacy);

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
            logger.info("INFORM to my subordinates (theres new leader around)");
            myAgent.send(informMsg);
        }
        logger.info("ACCEPT_PROPOSAL to " + receiver.getName());
        myAgent.setLeader(acceptedCandidacy);
        myAgent.send(msg);

        //add behaviour - waiting for any leader changes
        //myAgent.addBehaviour(new ReceiveNewLeaderBehaviour());
        this.done = true;
    }

    public boolean done() {
        return this.done;
    }
}
