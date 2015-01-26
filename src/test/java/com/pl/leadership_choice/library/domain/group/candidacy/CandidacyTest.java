package com.pl.leadership_choice.library.domain.group.candidacy;

import org.junit.Before;
import org.junit.Test;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;


public class CandidacyTest {

    private Candidacy higherScoreCandidate;
    private Candidacy lowerScoreCandidate;

    @Before
    public void setUp() throws Exception {
        higherScoreCandidate = new Candidacy("higher", "groupId", 100D, null);
        lowerScoreCandidate = new Candidacy("lower", "groupId", 10D, null);
    }

    @Test
    public void shouldReturnOneWhenComparedToCandidacyHasLowerScore() throws Exception {
        int result = higherScoreCandidate.compareTo(lowerScoreCandidate);

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void shouldReturnMinusOneWhenComparedToCandidacyHasHigherScore() throws Exception {
        int result = lowerScoreCandidate.compareTo(higherScoreCandidate);

        assertThat(result).isEqualTo(-1);
    }

    @Test
    public void shouldReturnZeroWhenEqual() throws Exception {
        int result = lowerScoreCandidate.compareTo(lowerScoreCandidate);

        assertThat(result).isEqualTo(0);
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenGroupIdsDiffer() throws Exception {
        lowerScoreCandidate.setGroupId("differentId");

        catchException(lowerScoreCandidate).compareTo(higherScoreCandidate);

        assertThat(caughtException()).isInstanceOf(RuntimeException.class);
    }
}