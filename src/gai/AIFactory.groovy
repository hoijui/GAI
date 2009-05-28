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
 *
 * @author Marcel Hauf <marcel.hauf@googlemail.com>
 * @author Robin Vobruba <hoijui.quaero@gmail.com>
 */
public class AIFactory extends OOAIFactory {

	public AIFactory() {}

	@Override
	public OOAI createAI(int teamId, OOAICallback callback) {
		return new GAI();
	}
}
