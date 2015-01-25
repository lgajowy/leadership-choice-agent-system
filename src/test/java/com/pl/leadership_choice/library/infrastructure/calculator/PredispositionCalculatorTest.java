package com.pl.leadership_choice.library.infrastructure.calculator;


import com.pl.leadership_choice.library.domain.group.leader.OptionalLeaderParameter;
import com.pl.leadership_choice.library.domain.group.leader.MandatoryLeaderParameter;
import com.pl.leadership_choice.library.domain.group.member.Predisposition;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PredispositionCalculatorTest {

    private Map<String, Double> expectedAgentFeatures = new HashMap<>();

    private Map<String, MandatoryLeaderParameter> expectedLeaderMandatoryParameters = new HashMap<>();

    private Map<String, OptionalLeaderParameter> expectedLeaderOptionalParameters = new HashMap<>();

    private PredispositionCalculator calculator;

    private MandatoryLeaderParameter mandatoryParameter;

    @Before
    public void setUp() throws Exception {
        calculator = new PredispositionCalculator(expectedLeaderMandatoryParameters, expectedLeaderOptionalParameters);
        mandatoryParameter = new MandatoryLeaderParameter(10D, 10D, "isEqualTo");
    }

    @Test
    public void shouldReturnParameterObjectWhenCalculatingScore() throws Exception {
        PredispositionCalculator calculator = new PredispositionCalculator(expectedLeaderMandatoryParameters, expectedLeaderOptionalParameters);

        assertThat(calculator.calculatePredisposition(expectedAgentFeatures)).isInstanceOf(Predisposition.class);
    }

    @Test
    public void ifDoesNotHaveMandatoryFeatureSpecifiedShouldNotHaveScoreCalculated() throws Exception {
        expectedLeaderMandatoryParameters.put("feature1", mandatoryParameter);

        Predisposition predisposition = calculator.calculatePredisposition(expectedAgentFeatures);

        assertThat(predisposition.getScore()).isEqualTo(0D);
    }

    @Test
    public void shouldDeterminePositivePredispositionIfMandatoryParametersAreMet() throws Exception {
        expectedAgentFeatures.put("feature1", 10D);
        expectedLeaderMandatoryParameters.put("feature1", mandatoryParameter);

        Predisposition predisposition = calculator.calculatePredisposition(expectedAgentFeatures);

        assertThat(predisposition.getCanBecomeLeader()).isTrue();

    }

    @Test
    public void shouldCalculateProperScoreIfMandatoryParametersAreMet() throws Exception {
        expectedLeaderMandatoryParameters.put("feature1", mandatoryParameter);
        expectedAgentFeatures.put("feature1", 10D);

        Predisposition predisposition = calculator.calculatePredisposition(expectedAgentFeatures);

        assertThat(predisposition.getScore()).isEqualTo(10D);
    }

    @Test
    public void shouldCalculateScoreForMandatoryAndOptionalParameters() throws Exception {
        expectedLeaderMandatoryParameters.put("feature1", mandatoryParameter);
        expectedAgentFeatures.put("feature1", 10D);
        OptionalLeaderParameter optionalParameter = new OptionalLeaderParameter(15D);
        expectedLeaderOptionalParameters.put("feature2", optionalParameter);
        expectedAgentFeatures.put("feature2", 15D);

        Predisposition predisposition = calculator.calculatePredisposition(expectedAgentFeatures);

        assertThat(predisposition.getScore()).isEqualTo(13D);
    }

    @Test
    public void shouldDeterminePredispositionProperly() throws Exception {
        expectedLeaderMandatoryParameters.put("feature1", mandatoryParameter);
        expectedAgentFeatures.put("feature1", 10D);
        OptionalLeaderParameter optionalParameter = new OptionalLeaderParameter(15D);
        expectedLeaderOptionalParameters.put("feature2", optionalParameter);
        expectedAgentFeatures.put("feature2", 15D);

        Predisposition predisposition = calculator.calculatePredisposition(expectedAgentFeatures);

        assertThat(predisposition.getScore()).isEqualTo(13D);
        assertThat(predisposition.getCanBecomeLeader()).isTrue();

    }
}