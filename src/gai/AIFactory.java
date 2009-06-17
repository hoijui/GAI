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


import com.clan_sy.spring.ai.oo.OOAI;
import com.clan_sy.spring.ai.oo.OOAICallback;
import com.clan_sy.spring.ai.oo.OOAIFactory;

/**
 * The main entry point of the AI, from the engines point of view.
 * Only a single instance of this class is created, which then initializes
 * all one AI instance per team.
 */
public class AIFactory extends OOAIFactory {

	/**
	 * Can be used for simplistic testing of the AI,
	 * without having to start the engine.
	 */
	public static void main(String[] args) throws Exception {

		AIFactory fac = new AIFactory();

		final int teamId = 0;
		final OOAICallback callback = null;
		OOAI ai = fac.createAI(teamId, callback);
		int error = ai.init(teamId, callback);

		// start update cycle (aprox. 30 frames per second)
		int frame = 0;
		boolean running = (error == 0);
		try {
			while (running) {
				Thread.sleep(33);
				//System.out.println("update(frame = ${frame})");
				ai.update(frame);
				frame++;
			}
		} catch (InterruptedException ex) {
			System.out.println("InterruptedException");
		}
	}

	@Override
	public OOAI createAI(int teamId, OOAICallback callback) {
		return new GAI();
	}
}
