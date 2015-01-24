package com.pl.leadership_choice.library.behaviours;

import com.pl.leadership_choice.library.LeadershipChoiceAgent;
import jade.core.behaviours.Behaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by adam on 18.01.15.
 */
public class BasicBehaviour extends Behaviour {
    Logger logger = LoggerFactory.getLogger(LeadershipChoiceAgent.class);

    public void action()
    {
        logger.info("Praca nad zachowaniem. Agent " + myAgent.getName());
    }

    public boolean done()
    {
        logger.info("Zakończone zachowanie. Agent " + myAgent.getName());
        return true;
    }
}
