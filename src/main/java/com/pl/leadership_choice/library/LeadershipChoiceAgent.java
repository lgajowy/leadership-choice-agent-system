/**
 * ***************************************************************
 * JADE - Java Agent DEvelopment Framework is a framework to develop
 * multi-agent systems in compliance with the FIPA specifications.
 * Copyright (C) 2000 CSELT S.p.A.
 *
 * GNU Lesser General Public License
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation,
 * version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 * **************************************************************
 */
package com.pl.leadership_choice.library;

import com.pl.leadership_choice.library.domain.group.candidacy.Candidacy;
import com.pl.leadership_choice.library.infrastructure.configuration.FromJsonFileAgentConfigurer;
import com.pl.leadership_choice.library.behaviours.ReceiveGroupRegistrationRequestBehaviour;
import com.pl.leadership_choice.library.domain.group.GroupRegistrar;
import com.pl.leadership_choice.library.domain.group.member.GroupMembershipRegistrar;
import jade.core.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LeadershipChoiceAgent extends Agent {
    Logger logger = LoggerFactory.getLogger(LeadershipChoiceAgent.class);

    private Map agentProperties = new HashMap();

    private GroupRegistrar groupRegistrar = new GroupRegistrar();
    private GroupMembershipRegistrar groupMembershipRegistrar = new GroupMembershipRegistrar();


    public String getLeader(String groupId) {
        return groupRegistrar.getGroups().get(groupId).getLeader();
    }

    public Candidacy getCandidacy(String groupId) {
        return groupMembershipRegistrar.getGroupMemberships().get(groupId).getMemberCandidacy();
    }

    public boolean alreadyHasALeader(String groupId) {
        return this.groupMembershipRegistrar.getGroupMemberships().get(groupId) != null;
    }

    public boolean canBecomeLeader(String groupId) {
        return groupMembershipRegistrar.getGroupMemberships().get(groupId).getPredisposition().getCanBecomeLeader();
    }

    private void readAgentProperties(String propertiesFilePath) {
        agentProperties = new FromJsonFileAgentConfigurer(new File(propertiesFilePath)).configureAgent();
    }

    protected void setup() {
        logger.info(getAID().getName() + ": Agent has started.");

        readAgentProperties(String.valueOf(getArguments()[0]));

        this.addBehaviour(new ReceiveGroupRegistrationRequestBehaviour());

        //doDelete();
    }

    public GroupRegistrar getGroupRegistrar() {
        return groupRegistrar;
    }

    public GroupMembershipRegistrar getGroupMembershipRegistrar() {
        return groupMembershipRegistrar;
    }

    public Map getAgentProperties() {
        return agentProperties;
    }
}

