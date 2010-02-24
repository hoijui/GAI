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
import java.util.Collection;
import java.util.Map;

/**
 * The generic or general event is what all GAI events derive from.
 * It is suposed to be the main method of interaction between
 * the separate parts of the AI, like agents, for example.
 */
public interface GEvent extends Serializable, Cloneable {

	/**
	 * Returns an unmodifiable collection of tags describing this event
	 * and its content.
	 * These can be used to filter the event inside a receiver.
	 * For a list of globally used tags:
	 * @see GEventHandler#getGlobalTags()
	 * @return tags describing this event and its content
	 */
	Collection<String> getTags();

	/**
	 * Returns an unmodifiable collection of properties associated
	 * with this event.
	 * For a list of globally used properties:
	 * @see GEventHandler#getGlobalProperyNames()
	 * @return properties associated with this event
	 */
	Map<String, Object> getProperties();
}
