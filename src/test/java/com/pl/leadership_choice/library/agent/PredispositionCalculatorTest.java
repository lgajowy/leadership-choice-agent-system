package com.pl.leadership_choice.library.agent;


import com.pl.leadership_choice.library.agent.leader_choice_request.LeaderParameter;
import com.pl.leadership_choice.library.agent.leader_choice_request.MandatoryLeaderParameter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PredispositionCalculatorTest {

    private Map<String, Double> givenAgentFeatures = new HashMap<>();

    private Map<String, MandatoryLeaderParameter> givenLeaderMandatoryFeatures = new HashMap<>();

    private Map<String, LeaderParameter> givenLeaderOptionalFeatures = new HashMap<>();

    private PredispositionCalculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new PredispositionCalculator(givenAgentFeatures, givenLeaderMandatoryFeatures, givenLeaderOptionalFeatures);
    }

    @Test
    public void shouldReturnScoreObjectWhenCalculatingScore() throws Exception {
        PredispositionCalculator calculator = new PredispositionCalculator(givenAgentFeatures, givenLeaderMandatoryFeatures, givenLeaderOptionalFeatures);

        assertThat(calculator.calculatePredisposition()).isInstanceOf(Predisposition.class);
    }

    @Test
    public void ifDoesNotHaveMandatoryFeatureSpecifiedAgentShouldNotHaveScoreCalculated() throws Exception {
        givenLeaderMandatoryFeatures.put("feature1", new MandatoryLeaderParameter());

        Predisposition predisposition = calculator.calculatePredisposition();

        assertThat(predisposition.getScore()).isEqualTo(0D);
    }

    @Test
    public void shouldReturnTrueIfMandatoryParametersAreMet() throws Exception {
        MandatoryLeaderParameter parameter = new MandatoryLeaderParameter();
        parameter.setRelation("isEqualTo");
        parameter.setValue(10D);
        parameter.setWeight(10D);
        givenAgentFeatures.put("feature1", 10D);
        givenLeaderMandatoryFeatures.put("feature1", parameter);
        calculator.areMandatoryParametersMet();

        assertThat(calculator.areMandatoryParametersMet()).isTrue();

    }
}