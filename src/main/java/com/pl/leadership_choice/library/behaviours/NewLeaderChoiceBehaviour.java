package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.json.JsonMapper;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lukasz on 26.01.15.
 */
public class NewLeaderChoiceBehaviour extends OneShotBehaviour {

    private Logger logger = LoggerFactory.getLogger(NewLeaderChoiceBehaviour.class);

    private Candidacy leader;

    private Candidacy subordinate;

    public NewLeaderChoiceBehaviour(Candidacy leader, Candidacy subordinate) {
        this.leader = leader;
        this.subordinate = subordinate;
    }

    @Override
    public void action() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;
        if (isMyAgentTheLeader()) {
            takeSubordinatesOver();
            informThatIAmTheLeader();
        } else {
            agreeThatHeShouldBeTheLeader();
        }
        myAgent.setLeader(leader);
    }

    private void agreeThatHeShouldBeTheLeader() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;
        ACLMessage agreeMessage = new ACLMessage(ACLMessage.AGREE);
        agreeMessage.setContent(JsonMapper.createJsonFromObject(subordinate));
        agreeMessage.addReceiver(new AID(leader.getPretenderId(), AID.ISGUID));
        logger.info("Sending AGREE message to " + leader.getPretenderId() + ", because he should be our group leader: " + leader.getGroupId());
        myAgent.send(agreeMessage);
    }

    private void informThatIAmTheLeader() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;
        ACLMessage informMessage = new ACLMessage(ACLMessage.INFORM);
        informMessage.setContent(JsonMapper.createJsonFromObject(leader));
        informMessage.addReceiver(new AID(subordinate.getPretenderId(), AID.ISGUID));
        logger.info("Sending INFORM message to " + subordinate.getPretenderId() + ", because I should be his leader group: " + leader.getGroupId());
        myAgent.send(informMessage);
    }

    private boolean isMyAgentTheLeader() {
        return leader.getPretenderId().equals(myAgent.getAID().getName());
    }

    private void takeSubordinatesOver() {
        logger.info("Taking over new subordinates of: " + subordinate.getPretenderId());
        leader.addNewSubordinates(subordinate.getPretenderSubordinates());
    }
}
