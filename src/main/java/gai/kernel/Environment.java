/*
	Copyright (c) 2009 Robin Vobruba <hoijui.quaero@gmail.com>

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

package gai.kernel;


import java.util.Set;

import com.springrts.ai.oo.clb.OOAICallback;

import gai.agents.Agent;
import gai.agents.AgentEnvironment;
import gai.event.GEventReceiver;
import gai.event.GEventSender;
import gai.tasks.TaskQueue;

/**
 * Central point of reference of the inner architecture of the AI.
 * All globally important parts of the AI are referenced.
 * So this can be seen as the skeleton of the (micro-)kernel.
 * For further info about the architecture of the AI,
 * see {@link http://springrts.com/wiki/AI:GAI the spring Wiki}
 * and {@link http://wiki.github.com/hoijui/springGAI the GAI Wiki}.
 */
public interface Environment extends AgentEnvironment, GEventReceiver, GEventSender {

	/**
	 * Initializes this environment with a complete AI instance specific context.
	 */
	void init(BeanContainer beans, int teamId, OOAICallback callback);

	/**
	 * Returns a number that uniquely identifies an <code>Agent</code>
	 * within an AI instance.
	 */
	TaskQueue getTaskQueue();

	/**
	 * Incorporates an <code>Agent</code> in the agency.
	 */
	void enrole(Agent agent);

	/**
	 * Removes an <code>Agent</code> from the agency,
	 * which means it will not get any more tasks.
	 */
	void dismiss(Agent agent);

	/**
	 * Enrolls the new agents after removing all current ones.
	 */
	void setAgents(Set<Agent> agents);
}
