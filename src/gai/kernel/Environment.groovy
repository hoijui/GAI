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

package gai.kernel;


import gai.agents.Agent;
import gai.agents.AgentEnvironment;
import gai.tasks.TaskQueue;

/**
 * Central point of reference of the inner architecture of the AI.
 * All globaly important parts of the AI are referenced.
 * So this can be seen as the skeleton of the (micro-)kernel.
 * For further info about the architecture of the AI,
 * see {@link http://springrts.com/wiki/AI:GAI the spring Wiki}
 * and {@link http://wiki.github.com/hoijui/springGAI the GAI Wiki}.
 */
public interface Environment extends AgentEnvironment {

	/**
	 * Returns a number that uniquely identifies an <code>Agent</code>
	 * withing an AI instance.
	 */
	TaskQueue getTaskQueue();

	/**
	 * Incorporates an agent in the Agency identifies an <code>Agent</code>
	 * withing an AI instance.
	 */
	void enrole(Agent agent);
}
