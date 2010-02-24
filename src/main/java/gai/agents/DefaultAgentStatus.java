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

package gai.agents;


/**
 * Default implementation of <code>AgentStatus</code>.
 */
public class DefaultAgentStatus implements AgentStatus {

	private String mDescription;
	private boolean mOperational;

	@Override
	public String getDescription() {
		return mDescription;
	}
	public void setDescription(String description) {
		mDescription = description;
	}

	@Override
	public boolean isOperational() {
		return mOperational;
	}
	public void setOperational(boolean operational) {
		mOperational = operational;
	}

	@Override
	public String toString() {

		return String.format("%s: [%4soperational] '%s'",
				getClass().getSimpleName(),
				isOperational() ? "" : "non-",
				getDescription());
	}
}
