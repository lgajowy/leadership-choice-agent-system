package com.pl.leadership_choice.library.group;

import com.pl.leadership_choice.library.agent.leader_choice_request.LeaderParameter;
import com.pl.leadership_choice.library.agent.leader_choice_request.MandatoryLeaderParameter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupRegistrarTest {
    private final String expectedGroupId = "xxx";
    private Set<String> groupMembers;
    private Map<String, MandatoryLeaderParameter> groupLeaderMandatoryParameters;
    private Map<String, LeaderParameter> groupLeaderOptionalFeatures;
    private Group expectedGroup;
    private GroupRegistrar registrar;

    @Before
    public void setUp() throws Exception {
        registrar = new GroupRegistrar();
        groupMembers = new HashSet<>();
        groupLeaderMandatoryParameters = new HashMap<>();
        groupLeaderOptionalFeatures = new HashMap<>();
        expectedGroup = new Group(groupMembers, groupLeaderMandatoryParameters, groupLeaderOptionalFeatures);
    }

    @Test
    public void shouldAddNewGroup() throws Exception {
        registrar.registerGroup(expectedGroupId, expectedGroup);

        assertThat(registrar.getGroups()).isNotEmpty();
    }

    @Test
    public void shouldNotRegisterGroupWithTheSameIdTwice() throws Exception {
        registrar.registerGroup(expectedGroupId, expectedGroup);
        registrar.registerGroup(expectedGroupId, expectedGroup);


        assertThat(registrar.getGroups().size()).isEqualTo(1);
    }

    @Test
    public void shouldModifyGroupMembers() throws Exception {
        registrar.registerGroup(expectedGroupId, expectedGroup);
        groupMembers.add("aNewGroupMemberWhoJustJoined");
        registrar.registerGroup(expectedGroupId, expectedGroup);

        assertThat(registrar.getMembersForId(expectedGroupId).size()).isEqualTo(1);
    }

    @Test
    public void shouldThrowExceptionIfTryingToGetMembersOfANotRegisteredGroup() throws Exception {
        catchException(registrar).getMembersForId(expectedGroupId);

        assertThat(caughtException()).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void shouldGetMembers() throws Exception {
        groupMembers.add("xyz");
        registrar.registerGroup(expectedGroupId, expectedGroup);
        Set<String> membersForId = registrar.getMembersForId(expectedGroupId);

        assertThat(membersForId).isNotEmpty();
    }

    @Test
    public void shouldThrowExceptionIfTryingToGetMandatoryParametersOfANotRegisteredGroup() throws Exception {
        catchException(registrar).getMandatoryLeaderParametersForId(expectedGroupId);

        assertThat(caughtException()).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void shouldGetMandatoryLeaderParameters() throws Exception {
        groupLeaderMandatoryParameters.put("xyz", new MandatoryLeaderParameter());
        registrar.registerGroup(expectedGroupId, expectedGroup);

        Map<String, MandatoryLeaderParameter> mandatoryLeaderParameters = registrar.getMandatoryLeaderParametersForId(expectedGroupId);

        assertThat(mandatoryLeaderParameters).isNotEmpty();
    }

    @Test
    public void shouldThrowExceptionIfTryingToGetOptionalParametersOfANotRegisteredGroup() throws Exception {
        catchException(registrar).getOptionalLeaderParametersForId(expectedGroupId);

        assertThat(caughtException()).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void shouldGetOptionalLeaderParameters() throws Exception {
        groupLeaderOptionalFeatures.put("xyz", new LeaderParameter());
        registrar.registerGroup(expectedGroupId, expectedGroup);

        Map<String, LeaderParameter> optionalParameters = registrar.getOptionalLeaderParametersForId(expectedGroupId);

        assertThat(optionalParameters).isNotEmpty();
    }
}