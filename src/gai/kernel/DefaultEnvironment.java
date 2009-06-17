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


import gai.tasks.Task;
import gai.tasks.TaskQueue;
import gai.agents.Agent;

import com.clan_sy.spring.ai.oo.OOAICallback;

import org.apache.commons.logging.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Default implementation of <code>Environment</code>.
 */
public class DefaultEnvironment implements Environment {

	public static final ClassLoader MY_CLASS_LOADER = DefaultEnvironment.class.getClassLoader();

	private BeanContainer mBeans;
	private int mTeamId;
	private OOAICallback mCallback;
	private Set<Agent> mAgents;
	private TaskQueue mTaskQueue;

    private Log log = LogFactory.getLog(DefaultEnvironment.class);

	@Override
	public void init(BeanContainer beans, int teamId, OOAICallback callback) {
		mBeans = beans;
		mTeamId = teamId;
		mCallback = callback;

		mAgents = new HashSet<Agent>();
		// mTaskQueue is set through the spring framework
		//mTaskQueue = { new PriorityQueue<Task>() } as TaskQueue;
		//mTaskQueue = (TaskQueue) mBeans.getBean("taskQueue");
	}

	@Override
	public int handleEvent(Object engineEvent) {

		// TODO FIXME big haxors!
		if (engineEvent instanceof Integer) {
			// assume it is an update event
			int frame = ((Integer)engineEvent).intValue();
			if ((frame % (10 * 30)) == 0) {
				System.out.printf("Stati for frame %d\n", frame);
				for (Agent agent : mAgents) {
					System.out.printf("\tAgent %30s: %s\n",
							agent.getName(), agent.getStatus().getDescription());
				}
			}
		}
		
		return 0;
	}

	public void addTestAgents() {

		Agent agentK    = (Agent) mBeans.getBean("agentK");
		Agent agentJ    = (Agent) mBeans.getBean("agentJ");
		Agent agentRuby = (Agent) mBeans.getBean("rubyAgent");
		Agent agentBsh  = (Agent) mBeans.getBean("bshAgent");

		this.enrole(agentK);
		this.enrole(agentJ);
		this.enrole(agentRuby);
		this.enrole(agentBsh);
	}



	/* (non-Javadoc)
	 * @see gai.agents.AgentEnvironment#getCallback()
	 */
	public OOAICallback getCallback() {
		return mCallback;
	}
	/*public void setCallback(OOAICallback callback) {
		mCallback = callback;
	}*/

	/* (non-Javadoc)
	 * @see gai.agents.AgentEnvironment#getCallback()
	 */
	public int getTeamId() {
		return mTeamId;
	}
	/*public void setTeamId(int teamId) {
		mTeamId = teamId;
	}*/

	/* (non-Javadoc)
	 * @see gai.agents.AgentEnvironment#scheduleTask(Task task)
	 */
	public void scheduleTask(Task task) {
		mTaskQueue.add(task);
	}

	/* (non-Javadoc)
	 * @see gai.kernel.Environment#getTaskQueue()
	 */
	public TaskQueue getTaskQueue() {
		return mTaskQueue;
	}
	public void setTaskQueue(TaskQueue taskQueue) {
		mTaskQueue = taskQueue;
	}

	/* (non-Javadoc)
	 * @see gai.kernel.Environment#enrole(Agent agent)
	 */
	public void enrole(Agent agent) {

		mAgents.add(agent);
		agent.engage(this);
	}
}
