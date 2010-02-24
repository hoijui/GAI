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


import java.io.Serializable;

/**
 * The <code>AgentStatus</code> contains various status info
 * about an <code>Agent</code>.
 */
public interface AgentStatus extends Serializable {

	/**
	 * Returns a short description of this status,
	 * eg. "everything OK here", "i need units to command!"
	 * or "Currently managing 3 attack groups with a total of 44 units".
	 */
	String getDescription();

	/**
	 * Returns true if this agent is ready to do its thing,
	 * eg. all requirements are met.
	 */
	boolean isOperational();
}
