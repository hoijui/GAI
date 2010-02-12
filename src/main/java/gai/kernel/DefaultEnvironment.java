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

package gai.kernel;


import com.springrts.ai.oo.AIEvent;
import gai.event.BadStateException;
import gai.event.GEventReceiver;
import gai.event.InvalidGEventException;
import gai.tasks.Task;
import gai.tasks.TaskQueue;
import gai.agents.Agent;

import com.springrts.ai.oo.clb.OOAICallback;
import com.springrts.ai.oo.evt.UpdateAIEvent;
import gai.event.DefaultGEventSender;
import gai.event.GEvent;
import java.util.Collection;

import org.apache.commons.logging.*;

import java.util.Set;
import java.util.HashSet;

/**
 * Default implementation of <code>Environment</code>.
 */
public class DefaultEnvironment implements Environment {

	public static final ClassLoader MY_CLASS_LOADER = DefaultEnvironment.class.getClassLoader();

	private BeanContainer mBeans;
	private int mTeamId;
	private OOAICallback mCallback;
	private Set<Agent> mAgents = new HashSet<Agent>();
	private TaskQueue mTaskQueue;
	private boolean mInitialized = false;
	private DefaultGEventSender eventSender = new DefaultGEventSender();

    private Log log = LogFactory.getLog(DefaultEnvironment.class);


	@Override
	public void init(BeanContainer beans, int teamId, OOAICallback callback) {
		mBeans = beans;
		mTeamId = teamId;
		mCallback = callback;

		// mTaskQueue is set through the spring framework
		//mTaskQueue = { new PriorityQueue<Task>() } as TaskQueue;
		//mTaskQueue = (TaskQueue) mBeans.getBean("taskQueue");

		// disengage agents from the old agency
		if (mInitialized) {
			for (Agent agent : mAgents) {
				agent.disengage();
			}
		}
		mInitialized = true;
		// (re-)engage agents with the new agency
		for (Agent agent : mAgents) {
			agent.engage(this);
		}

		// register agents for global events
		for (Agent agent : mAgents) {
			if (agent instanceof GEventReceiver) {
				eventSender.addGEventReceiver((GEventReceiver) agent);
			}
		}
	}

	@Override
	public void handle(GEvent event) throws InvalidGEventException, BadStateException {

		// TODO FIXME big haxors!
		if (event.getTags().contains("engine")) {
			AIEvent engineEvent = (AIEvent) event.getProperties().get("engineEvent");

			if (engineEvent instanceof UpdateAIEvent) {
				UpdateAIEvent updateEvent = (UpdateAIEvent) engineEvent;
				int frame = updateEvent.getFrame();
				if ((frame % (10 * 30)) == 0) {
					System.out.printf("Agent Stati for frame %d\n", frame);
					System.out.printf(" %10s   %40s   %s\n",
							"[Name]", "[Description]", "[Status.Description]");
					for (Agent agent : mAgents) {
						System.out.printf(" %10s | %40s | %s\n",
								agent.getName(), agent.getDescription(),
								agent.getStatus().getDescription());
					}
				}
			}
		}

		eventSender.send(event);
	}

	private void send(GEvent event) {
		// TODO: send to all parts of the environment
	}


	/* (non-Javadoc)
	 * @see gai.agents.AgentEnvironment#getCallback()
	 */
	@Override
	public OOAICallback getCallback() {
		return mCallback;
	}
	/*public void setCallback(OOAICallback callback) {
		mCallback = callback;
	}*/

	/* (non-Javadoc)
	 * @see gai.agents.AgentEnvironment#getCallback()
	 */
	@Override
	public int getTeamId() {
		return mTeamId;
	}
	/*public void setTeamId(int teamId) {
		mTeamId = teamId;
	}*/

	/* (non-Javadoc)
	 * @see gai.agents.AgentEnvironment#scheduleTask(Task task)
	 */
	@Override
	public void scheduleTask(Task task) {
		mTaskQueue.add(task);
	}

	/* (non-Javadoc)
	 * @see gai.kernel.Environment#getTaskQueue()
	 */
	@Override
	public TaskQueue getTaskQueue() {
		return mTaskQueue;
	}
	public void setTaskQueue(TaskQueue taskQueue) {
		mTaskQueue = taskQueue;
	}

	/* (non-Javadoc)
	 * @see gai.kernel.Environment#enrole(Agent agent)
	 */
	@Override
	public void enrole(Agent agent) {

		mAgents.add(agent);
		if (mInitialized) {
			agent.engage(this);
		}
	}

	/* (non-Javadoc)
	 * @see gai.kernel.Environment#dismiss(Agent agent)
	 */
	@Override
	public void dismiss(Agent agent) {

		if (mInitialized) {
			agent.disengage();
		}
		mAgents.remove(agent);
	}
	/**
	 * Dismisses all agents currently enrolled in this agency.
	 */
	public void dismissAgents() {

		for (Agent agent : mAgents) {
			dismiss(agent);
		}
	}

	/* (non-Javadoc)
	 * @see gai.kernel.Environment#setAgents(Set<Agent> agents)
	 */
	@Override
	public void setAgents(Set<Agent> agents) {

		dismissAgents();
		for (Agent agent : agents) {
			enrole(agent);
		}
	}

	@Override
	public void addGEventReceiver(GEventReceiver eventReceiver) {
		eventSender.addGEventReceiver(eventReceiver);
	}

	@Override
	public void removeGEventReceiver(GEventReceiver eventReceiver) {
		eventSender.removeGEventReceiver(eventReceiver);
	}

	@Override
	public Collection<String> getSuppliedTags() {
		return eventSender.getSuppliedTags();
	}
}
