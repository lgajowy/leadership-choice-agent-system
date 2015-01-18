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
    private MandatoryLeaderParameter mandatoryParameter;

    @Before
    public void setUp() throws Exception {
        calculator = new PredispositionCalculator(givenAgentFeatures, givenLeaderMandatoryFeatures, givenLeaderOptionalFeatures);
        mandatoryParameter = new MandatoryLeaderParameter(10D, 10D, "isEqualTo");
    }

    @Test
    public void shouldReturnParameterObjectWhenCalculatingScore() throws Exception {
        PredispositionCalculator calculator = new PredispositionCalculator(givenAgentFeatures, givenLeaderMandatoryFeatures, givenLeaderOptionalFeatures);

        assertThat(calculator.calculatePredisposition()).isInstanceOf(Predisposition.class);
    }

    @Test
    public void ifDoesNotHaveMandatoryFeatureSpecifiedShouldNotHaveScoreCalculated() throws Exception {
        givenLeaderMandatoryFeatures.put("feature1", mandatoryParameter);

        Predisposition predisposition = calculator.calculatePredisposition();

        assertThat(predisposition.getScore()).isEqualTo(0D);
    }

    @Test
    public void shouldDeterminePositivePredispositionIfMandatoryParametersAreMet() throws Exception {
        givenAgentFeatures.put("feature1", 10D);
        givenLeaderMandatoryFeatures.put("feature1", mandatoryParameter);

        Predisposition predisposition = calculator.calculatePredisposition();

        assertThat(predisposition.getCanBecomeLeader()).isTrue();

    }

    @Test
    public void shouldCalculateProperScoreIfMandatoryParametersAreMet() throws Exception {
        givenLeaderMandatoryFeatures.put("feature1", mandatoryParameter);
        givenAgentFeatures.put("feature1", 10D);

        Predisposition predisposition = calculator.calculatePredisposition();

        assertThat(predisposition.getScore()).isEqualTo(10D);
    }

    @Test
    public void shouldCalculateScoreForMandatoryAndOptionalParameters() throws Exception {
        givenLeaderMandatoryFeatures.put("feature1", mandatoryParameter);
        givenAgentFeatures.put("feature1", 10D);

        LeaderParameter optionalParameter = new LeaderParameter(15D);
        givenLeaderOptionalFeatures.put("feature2", optionalParameter);
        givenAgentFeatures.put("feature2", 15D);

        Predisposition predisposition = calculator.calculatePredisposition();

        assertThat(predisposition.getScore()).isEqualTo(13D);
    }

    @Test
    public void shouldDeterminePredispositionProperly() throws Exception {
        givenLeaderMandatoryFeatures.put("feature1", mandatoryParameter);
        givenAgentFeatures.put("feature1", 10D);

        LeaderParameter optionalParameter = new LeaderParameter(15D);
        givenLeaderOptionalFeatures.put("feature2", optionalParameter);
        givenAgentFeatures.put("feature2", 15D);

        Predisposition predisposition = calculator.calculatePredisposition();

        assertThat(predisposition.getScore()).isEqualTo(13D);
        assertThat(predisposition.getCanBecomeLeader()).isTrue();

    }
}