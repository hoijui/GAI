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


import gai.tasks.Task;

import com.springrts.ai.oo.clb.OOAICallback;

/**
 * Forms the central point of reference of the architecture of the AI
 * from an <code>Agent</code>s point of view.
 *
 * @see gai.kernel.Environment
 */
public interface AgentEnvironment {

	/**
	 * Returns this AIs team ID, which is assigned to it by the engine,
	 * and used as a unique identifier for an AI instance.
	 */
	int getTeamId();

	/**
	 * Returns the callback object for communication with the engine.
	 * CAUTION: This callback may only be used in the main thread,
	 * which is the one that sends events to the AI.
	 */
	OOAICallback getCallback();

	/**
	 * Schedule a task for processing by (an other) <code>Agent</code>.
	 */
	void scheduleTask(Task task);
}
