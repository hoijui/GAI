/*
	Copyright 2010 Robin Vobruba <hoijui.quaero@gmail.com>

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

package gai.event;


import java.io.Serializable;

/**
 * The generic or general event is what all GAI events derive from.
 * It is suposed to be the main method of interaction between
 * the separate parts of the AI, like agents, for example.
 */
public interface GEventReceiver extends Serializable {

	/**
	 * Handle a generic event.
	 * Obviously, what will have to be done here totally depends
	 * on the part implementing this interface.
	 * @param event the generic event to be handled
	 * @throws InvalidGEventException
	 *         thrown when the supplied event does not meet the receivers input
	 *         criteria, eg. does not come with the needed tags,
	 *         or does not implement the expected interfaces
	 * @throws BadStateException
	 *         thrown when this receiver is not in the right state to receive
	 *         the supplied event
	 */
	void handle(GEvent event) throws InvalidGEventException, BadStateException;

}
