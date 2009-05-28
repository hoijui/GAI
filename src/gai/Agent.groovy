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

package gai;


/**
 * An <code>Agent</code> is a work horse.
 * It is responsible for processing <code>Task</code>s and will often be
 * scheduling new ones.
 * For a more detailed description of <code>Agent</code>s and info about
 * how to develop and share them,
 * see {@link http://springrts.com/wiki/AI:GAI the spring Wiki}
 * and {@link http://wiki.github.com/hoijui/springGAI the GAI Wiki}.
 *
 * @author Marcel Hauf <marcel.hauf@googlemail.com>
 * @author Robin Vobruba <hoijui.quaero@gmail.com>
 */
interface Agent {

	/**
	 * Returns a number that uniquely identifies an <code>Agent</code>
	 * withing an AI instance.
	 */
	long getId();

	/**
	 * Initializes this <code>Agent</code>.
	 * The <code>Agent</code> will not receive any queries
	 * prior to the call of this method.
	 * It is possible to access and use all parts of the AI
	 * in this method already, but not before it was called.
	 *
	 * @param environment functions as the connection to the AI
	 */
	void initialize(AgentEnvironment environment);
	/**
	 * Destroys this <code>Agent</code>.
	 * The <code>Agent</code> will not receive any queries
	 * after this method is called.
	 * It is still possible to access and use all parts of the AI
	 * in this method, but not after it was called.
	 */
	void destroy();
}
