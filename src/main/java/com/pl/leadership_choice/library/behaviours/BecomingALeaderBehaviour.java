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
public class BecomingALeaderBehaviour extends OneShotBehaviour {

    private Logger logger = LoggerFactory.getLogger(BecomingALeaderBehaviour.class);

    private Candidacy leaderCandidacy;

    private Candidacy newSubordinateCandidacy;

    public BecomingALeaderBehaviour(Candidacy newSubordinateCandidacy) {
        this.newSubordinateCandidacy = newSubordinateCandidacy;
    }

    @Override
    public void action() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;
        leaderCandidacy = myAgent.getCandidacy(newSubordinateCandidacy.getGroupId());

        setMeAsLeader();
        takeOtherAgentsSubordinatesOver();

        ACLMessage informMessage = new ACLMessage(ACLMessage.INFORM);
        informMessage.setContent(JsonMapper.createJsonStringFromObject(leaderCandidacy));
        informMessage.addReceiver(new AID(newSubordinateCandidacy.getPretenderId(), AID.ISGUID));

        logger.info("INFORM to "
                + newSubordinateCandidacy.getPretenderId()
                + ", because I should be his leader group: "
                + leaderCandidacy.getGroupId()
                + " subordinates count: "
                + myAgent.getCandidacy(leaderCandidacy.getGroupId()).getPretenderSubordinates().size());
        myAgent.send(informMessage);
    }

    private void setMeAsLeader() {
        LeadershipChoiceAgent myAgent = (LeadershipChoiceAgent) this.myAgent;
        myAgent.setLeader(leaderCandidacy);
    }

    private void takeOtherAgentsSubordinatesOver() {
        logger.info("Taking over subordinates of: " + newSubordinateCandidacy.getPretenderId());
        leaderCandidacy.addNewSubordinates(newSubordinateCandidacy.getPretenderSubordinates());
        leaderCandidacy.addNewSubordinate(newSubordinateCandidacy.getPretenderId());
    }
}
