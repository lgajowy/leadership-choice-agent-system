package com.pl.leadership_choice.proof_of_concept.infrastructure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lukasz on 17.01.15.
 */
public class JadeBootThread extends Thread {

    private final String jadeBoot_CLASS_NAME = "jade.Boot";

    private final String MAIN_METHOD_NAME = "main";

    //add the <agent-local-name>:<fully-qualified-agent-class> name here;
    // you can add more than one by semicolon separated values.
    private final String ACTOR_NAMES_args = "leadershipChoiceAgent1:com.pl.leadership_choice.proof_of_concept.LeadershipChoiceAgent" +
            "(sampleConfiguration.json)"
            + ";leadershipChoiceAgent2:com.pl.leadership_choice.proof_of_concept.LeadershipChoiceAgent"
            + "(sampleConfiguration2.json)"
            + ";leadershipChoiceAgent3:com.pl.leadership_choice.proof_of_concept.LeadershipChoiceAgent"
            + "(sampleConfiguration2.json)"
            + ";leadershipChoiceAgent4:com.pl.leadership_choice.proof_of_concept.LeadershipChoiceAgent"
            + "(sampleConfiguration2.json)";

    private final String GUI_args = "-gui";

    private final Class<?> secondClass;

    private final Method main;

    private final String[] params;

    public JadeBootThread() throws ClassNotFoundException, SecurityException, NoSuchMethodException {
        secondClass = Class.forName(jadeBoot_CLASS_NAME);
        main = secondClass.getMethod(MAIN_METHOD_NAME, String[].class);
        params = new String[]{GUI_args, ACTOR_NAMES_args};
    }

    @Override
    public void run() {
        try {
            main.invoke(null, new Object[]{params});
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            ex.printStackTrace();
        }

    }
}