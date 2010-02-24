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


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Default implementation of a <code>GEventSender</code>.
 */
public class DefaultGEventSender implements GEventSender {

	private Collection<String> suppliedTags;
	private List<GEventReceiver> gEventReceivers;

	public DefaultGEventSender() {
		gEventReceivers = new ArrayList<GEventReceiver>();
	}

	@Override
	public Collection<String> getSuppliedTags() {
		return Collections.unmodifiableCollection(suppliedTags);
	}
	public void setSuppliedTags(Collection<String> suppliedTags) {
		this.suppliedTags = suppliedTags;
	}

	@Override
	public void addGEventReceiver(GEventReceiver eventReceiver) {
		gEventReceivers.add(eventReceiver);
	}
	@Override
	public void removeGEventReceiver(GEventReceiver eventReceiver) {
		gEventReceivers.remove(eventReceiver);
	}
	public List<GEventReceiver> getGEventReceivers() {
		return Collections.unmodifiableList(gEventReceivers);
	}
	public void send(GEvent evt) throws InvalidGEventException, BadStateException {

		for (GEventReceiver evtReceiver : gEventReceivers) {
			evtReceiver.handle(evt);
		}
	}
}
