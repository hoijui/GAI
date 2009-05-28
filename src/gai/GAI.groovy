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


import com.clan_sy.spring.ai.AICommand;
import com.clan_sy.spring.ai.AICommandWrapper;
import com.clan_sy.spring.ai.command.*;
import com.clan_sy.spring.ai.oo.AbstractOOAI;
import com.clan_sy.spring.ai.oo.OOAI;
import com.clan_sy.spring.ai.oo.OOAICallback;

/**
 * This class represents an actual instance of a GAI Skirmish AI.
 * Each team controlled by GAI has an instance of this class assigned.
 * This is the main centre of engine -> AI communication.
 * For AI -> engine communication, see
 * {@link com.clan_sy.spring.ai.oo.OOAICallback}.
 *
 * @author Marcel Hauf <marcel.hauf@googlemail.com>
 * @author Robin Vobruba <hoijui.quaero@gmail.com>
 */
public class GAI extends AbstractOOAI implements OOAI {

	private static final int SUCCESS   = 0;
	private static final int FAILURE_X = 1;

	private OOAICallback mCallback;
	private int mTeamID;

	@Override
	public int init(int teamId, OOAICallback callback) {
		mTeamID = teamId;
		mCallback = callback;

		sendMessage("Hello Engine send by GAI, a Groovy Skirmish AI.");
		setPause(true, "Testing pause");

		return SUCCESS;
	}

	/**
	 * Sends a command from the AI to the engine.
	 */
	private void handleEngineCommand(AICommand command) {
		mCallback.getEngine().handleCommand(
				AICommandWrapper.COMMAND_TO_ID_ENGINE, -1, command);
	}

	/**
	 * Sends a chat message to the engine, which is seen by the players
	 * during the game and stored in the engine log.
	 */
	private void sendMessage(String message) {

		SendTextMessageAICommand msgCmd =
				new SendTextMessageAICommand(message, 0);
		handleEngineCommand(msgCmd);
	}

	/**
	 * Pauses or unpauses the game.
	 */
	private boolean setPause(boolean enable, String reason = "unknown") {

		PauseAICommand cmd = new PauseAICommand(enable, reason);
		boolean success = (handleEngineCommand(cmd) == 0);
		return success;
	}
}
