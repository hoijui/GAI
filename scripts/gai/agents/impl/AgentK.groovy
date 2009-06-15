/*
	Copyright (c) 2009
		Marcel Hauf <marcel.hauf@googlemail.com>
		Robin Vobruba <hoijui.quaero@gmail.com>

	This file is part of GAI (Groovy skirmish Artificial Intelligence
	for the spring RTS game engine).

	GAI is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 2 of the License, or
	(at your option) any later version.

	Foobar is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with GAI; If not, see <http://www.gnu.org/licenses/>.
*/

package gai.agents.impl;


import gai.agents.Agent;
import gai.agents.AgentStatus;
import gai.agents.AgentEnvironment;


/**
 * Test Agent, codename: K
 */
public class AgentK implements Agent {

	private static final String NAME = "K";
	private static final String DESCRIPTION = "stay calmed, need nothing";
	private AgentStatus mStatus = null;

	public AgentK() {

		def desc = {"green"};
		def oper = {true};
		mStatus = ["getDescription":desc, "isOperational":oper] as AgentStatus;
	}


	/* (non-Javadoc)
	 * @see gai.agents.Agent#getName()
	 */
	@Override
	public String getName() {
		return NAME;
	}

	/* (non-Javadoc)
	 * @see gai.agents.Agent#getDescription()
	 */
	@Override
	public String getDescription() {
		return DESCRIPTION;
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
