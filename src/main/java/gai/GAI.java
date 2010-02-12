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


import com.springrts.ai.oo.AIEvent;
import com.springrts.ai.oo.EventAIException;
import gai.event.BadStateException;
import gai.event.InvalidGEventException;
import gai.kernel.*;

import com.springrts.ai.AI;
import com.springrts.ai.oo.OOEventAI;
import com.springrts.ai.oo.clb.OOAICallback;
import com.springrts.ai.oo.evt.*;
import gai.event.EngineGEvent;
import org.apache.commons.logging.LogFactory;

/**
 * This class represents an actual instance of a GAI Skirmish AI.
 * Each team controlled by GAI has an instance of this class assigned.
 * This is the main centre of engine -> AI communication.
 * For AI -> engine communication, see
 * {@link com.springrts.ai.oo.OOAICallback}.
 */
public class GAI extends OOEventAI implements AI {

	private Environment mEnv;

	@Override
	public void handleEvent(AIEvent evt) throws EventAIException {

		if (evt instanceof InitAIEvent) {
			InitAIEvent initEvt = (InitAIEvent) evt;
			try {
				BeanContainer beans = BeanContainer.getInstance();
				beans.initContext();

				mEnv = (Environment) beans.getBean("environment");
				mEnv.init(beans, initEvt.getSkirmishAIId(), initEvt.getCallback());
			} catch (Exception ex) {
				LogFactory.getLog(GAI.class).error("Failed initializing DefaultEnvironment", ex);
				mEnv = null;
				throw new EventAIException(ex);
			}
		}

		try {
			mEnv.handle(new EngineGEvent(evt));
		} catch (InvalidGEventException ex) {
			LogFactory.getLog(GAI.class).error(null, ex);
			throw new EventAIException(ex);
		} catch (BadStateException ex) {
			LogFactory.getLog(GAI.class).error(null, ex);
			throw new EventAIException(ex);
		}
	}
}
