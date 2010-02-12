/*
	Copyright (c) 2010
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

package gai.event;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The generic or general event is what all GAI events derive from.
 * It is suposed to be the main method of interaction between
 * the separate parts of the AI, like agents, for example.
 */
public final class GEventUtil {

	private GEventUtil() {}

	public static Collection<String> newTagsCollection() {
		return new ArrayList<String>();
	}

	public static Map<String, Object> newPropertiesMap() {
		return new HashMap<String, Object>();
	}
}
