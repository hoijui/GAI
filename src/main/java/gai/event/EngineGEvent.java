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


import com.springrts.ai.oo.AIEvent;
import com.springrts.ai.oo.evt.EnemyAIEvent;
import com.springrts.ai.oo.evt.LoadSaveAIEvent;
import com.springrts.ai.oo.evt.UnitAIEvent;
import com.springrts.ai.oo.evt.UnitLifeStateAIEvent;
import com.springrts.ai.oo.evt.UnitTeamChangeAIEvent;
import java.util.Collection;

/**
 * The generic or general event is what all GAI events derive from.
 * It is suposed to be the main method of interaction between
 * the separate parts of the AI, like agents, for example.
 */
public class EngineGEvent extends AbstractGEvent implements GEvent {

	public EngineGEvent(AIEvent aiEvent) {

		addTags(evaluateAIEventTags(aiEvent));
		addProperty("engineEvent", aiEvent);
	}

	private static Collection<String> evaluateAIEventTags(AIEvent aiEvent) {

		Collection<String> tags = GEventUtil.newTagsCollection();

		tags.add("engine");
		if (aiEvent instanceof UnitAIEvent) {
			tags.add("unit");
			if (aiEvent instanceof UnitLifeStateAIEvent) {
				tags.add("lifeState");
			}
			if (aiEvent instanceof EnemyAIEvent) {
				tags.add("enemy");
			} else {
				tags.add("friendly");
			}
			if (aiEvent instanceof UnitTeamChangeAIEvent) {
				tags.add("teamChange");
			}
		}
		if (aiEvent instanceof LoadSaveAIEvent) {
			tags.add("loadSave");
		}

		return tags;
	}
}
