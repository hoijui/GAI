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

package gai;


import org.apache.commons.logging.LogFactory;

import com.springrts.ai.oo.OOEventAI;
import com.springrts.ai.oo.clb.OOAICallback;
import com.springrts.ai.oo.evt.InitAIEvent;
import com.springrts.ai.oo.evt.UpdateAIEvent;
import com.springrts.ai.oo.AIEvent;
import com.springrts.ai.oo.EventAIException;

import gai.event.BadStateException;
import gai.event.InvalidGEventException;
import gai.event.EngineGEvent;
import gai.kernel.BeanContainer;
import gai.kernel.Environment;

/**
 * This class represents an actual instance of a GAI Skirmish AI.
 * Each team controlled by GAI has an instance of this class assigned.
 * This is the main center of engine -> AI communication.
 * For AI -> engine communication, see
 * {@link com.springrts.ai.oo.clb.OOAICallback}.
 */
public class GAI extends OOEventAI {

	private Environment mEnv;

	/**
	 * Can be used for simplistic testing of the AI,
	 * without having to start the engine.
	 */
	public static void main(String[] args) throws Exception {

		OOEventAI ai = new GAI();

		final int teamId = 0;
		OOAICallback callback = null;
		InitAIEvent initEvt = new InitAIEvent(teamId, callback);
		ai.handleEvent(initEvt);

		// start update cycle (aprox. 30 frames per second)
		int frame = 0;
		boolean running = true;
		try {
			while (running) {
				Thread.sleep(33);
				//System.out.println("update(frame = ${frame})");
				UpdateAIEvent updateAIEvent = new UpdateAIEvent(frame);
				ai.handleEvent(updateAIEvent);
				frame++;
			}
		} catch (InterruptedException ex) {
			System.out.println("InterruptedException");
		}
	}

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
