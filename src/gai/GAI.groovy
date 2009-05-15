/*
	Copyright (c) 2008 Robin Vobruba <hoijui.quaero@gmail.com>

	This program is free software; you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation; either version 2 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package gai;

import com.clan_sy.spring.ai.AICommand;
import com.clan_sy.spring.ai.AICommandWrapper;
import com.clan_sy.spring.ai.command.*;
import com.clan_sy.spring.ai.oo.AbstractOOAI;
import com.clan_sy.spring.ai.oo.OOAI;
import com.clan_sy.spring.ai.oo.OOAICallback;


public class GAI extends AbstractOOAI implements OOAI {

	OOAICallback mCallback;
	int mTeamID;

	@Override
	public int init(int teamId, OOAICallback callback) {
		mTeamID = teamId;
		mCallback = callback;

		sendMessage("Hello Engine send by GAI a Groovy Sample AI.");
		setPause(true, "Testing pause");

		return 0;
	}

	private void handleEngineCommand(AICommand command) {
		mCallback.getEngine().handleCommand(AICommandWrapper.COMMAND_TO_ID_ENGINE, -1, command);
	}

	private void sendMessage(String message) {

		SendTextMessageAICommand msgCmd = new SendTextMessageAICommand(message, 0);
		handleEngineCommand(msgCmd);
	}

	private boolean setPause(boolean enable, String reason = "unknown") {

		PauseAICommand cmd = new PauseAICommand(enable, reason);
		boolean success = (handleEngineCommand(cmd) == 0);
		return success;
	}
}
