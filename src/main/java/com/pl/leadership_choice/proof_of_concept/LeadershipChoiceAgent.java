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
package com.pl.leadership_choice.proof_of_concept;

import com.pl.leadership_choice.library.agent.configuration.FromJsonFileAgentConfigurer;
import com.pl.leadership_choice.library.agent.leader_choice_request.LeaderParameter;
import com.pl.leadership_choice.library.agent.leader_choice_request.MandatoryLeaderParameter;
import com.pl.leadership_choice.library.behaviours.ReceiveRequestBehaviour;
import jade.core.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class LeadershipChoiceAgent extends Agent {
    Logger logger = LoggerFactory.getLogger(LeadershipChoiceAgent.class);

    private Map agentParameters;

    private String groupId;
    private ArrayList groupMembers;
    private Map<String, MandatoryLeaderParameter> mandatoryFeatures;
    private Map<String, LeaderParameter> optionalFeatures;

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }
    public void setGroupMembers(ArrayList groupMembers)
    {
        this.groupMembers = groupMembers;
    }
    public void setMandatoryFeatures(Map<String, MandatoryLeaderParameter> mandatoryFeatures)
    {
        this.mandatoryFeatures = mandatoryFeatures;
    }
    public void setOptionalFeatures(Map<String, LeaderParameter> optionalFeatures)
    {
        this.optionalFeatures = optionalFeatures;
    }
    public String getGroupId()
    {
        return this.groupId;
    }
    public ArrayList getGroupMembers()
    {
        return this.groupMembers;
    }
    public Map<String, MandatoryLeaderParameter> getMandatoryFeatures()
    {
        return this.mandatoryFeatures;
    }
    public Map<String, LeaderParameter> getOptionalFeatures()
    {
        return this.optionalFeatures;
    }

    public boolean canBeLeader()
    {
        return true;
    }

    protected void setup() {
        logger.info(getAID().getName() + ": Agent has started.");
        Object[] args = getArguments();
        agentParameters = new FromJsonFileAgentConfigurer(new File(String.valueOf(args[0]))).configureAgent();

        this.addBehaviour(new ReceiveRequestBehaviour());

        //doDelete();
    }
}

