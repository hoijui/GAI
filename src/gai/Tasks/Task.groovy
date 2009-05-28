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
 * A <code>Task</code> represents an unit of work
 * that is requested scheduled by one <code>Agent</code>,
 * and processed by an other.
 *
 * @author Marcel Hauf <marcel.hauf@googlemail.com>
 * @author Robin Vobruba <hoijui.quaero@gmail.com>
 */
interface Task {

	// relevant for scheduling:

	/**
	 * Returns a number that uniquely identifies a task
	 * withing an AI instance.
	 */
	long getId();
	/**
	 * Specifies the priority for processing this <code>Task</code>.
	 * The <code>Task</code> with the highest priority is tried to be processed
	 * next.
	 */
	TaskPriority getPriority();
	/**
	 * If the <code>Task</code> is meant ot be processed, and this returns false,
	 * the <code>Task</code> is purged without beeing processed.
	 */
	boolean isProcessable();


	// relevant for processing:

	/**
	 * Returns the category/type of this <code>Task</code>.
	 * This is mainly used to distinguish which <code>Agent</code> is best
	 * suited to process this <code>Task</code>.
	 */
	TaskCategory getCategory();
	/**
	 * TODO: describe
	 */
	TaskArguments getArguments();
}
