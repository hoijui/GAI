/*
	Copyright 2009 Robin Vobruba <hoijui.quaero@gmail.com>

	This file is part of the Groovy Artificial Intelligence (GAI),
	a skirmish AI for the Spring RTS game engine.

	GAI is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 2 of the License, or
	(at your option) any later version.

	GAI is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with GAI; If not, see <http://www.gnu.org/licenses/>.
*/

package gai.agents.impl;


import gai.agents.Agent;
import gai.agents.AgentStatus;
import gai.agents.DefaultAgentStatus;
import gai.agents.AgentEnvironment;


/**
 * Test Agent, codename: J
 */
public class AgentJ implements Agent {

	private static final String NAME = "J";
	private static final String DESCRIPTION = "shooting aliens, need guns";
	private AgentStatus mStatus = null;

	public AgentJ() {

		//def desc = {"missing guns"};
		//def oper = {false};
		//mStatus = ["getDescription":desc, "isOperational":oper] as AgentStatus;
		mStatus = new DefaultAgentStatus();
		mStatus.setDescription("missing guns");
		mStatus.setOperational(false);
	}


	/* (non-Javadoc)
	 * @see gai.agents.Agent#getName()
	 */
	@Override
	public String getName() {
		return NAME;
	}

	private String mDescription = "<uninitialized>";
	/* (non-Javadoc)
	 * @see gai.agents.Agent#getDescription()
	 */
	@Override
	public String getDescription() {
		return mDescription;
	}
	public void setDescription(String description) {
		mDescription = description;
	}

	/* (non-Javadoc)
	 * @see gai.agents.Agent#getStatus()
	 */
	@Override
	public AgentStatus getStatus() {
		return mStatus;
	}

	/* (non-Javadoc)
	 * @see gai.agents.Agent#engage(gai.agents.AgentEnvironment)
	 */
	@Override
	public void engage(AgentEnvironment environment) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see gai.agents.Agent#disengage()
	 */
	@Override
	public void disengage() {
		// TODO Auto-generated method stub
	}

}
