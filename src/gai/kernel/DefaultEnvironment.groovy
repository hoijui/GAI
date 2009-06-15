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
 * Default implementaiton of <code>Environment</code>.
 * @see gai.agents.AgentEnvironment#getCallback()
 */
public class DefaultEnvironment implements Environment {

	public static final ClassLoader MY_CLASS_LOADER = DefaultEnvironment.class.getClassLoader();
    private static Log log = LogFactory.getLog(DefaultEnvironment.class);

	private ApplicationContext mContext;
	private int mTeamId;
	private OOAICallback mCallback;
	private Set<Agent> mAgents;
	private TaskQueue mTaskQueue;

	public DefaultEnvironment() {

		mAgents = new HashSet<Agent>();
		mTaskQueue = {new PriorityQueue<Task>()} as TaskQueue;

		loadContext();
	}

	private void loadContext() {

		// TODO FIXME: ClassLoader issue!!
		// Without this line, JRuby and BeanShell scripts can not be loaded
		// through the spring framework.
		// But this line will probably cause problems when our AI is used
		// together with other AI implementations that rely on the context
		// class loader as well, if they have to use a similar hack.
		ClassLoader originalContextClassLoader =
				Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(MY_CLASS_LOADER);

		Resource res_beans_xml   = new ClassPathResource("beans.xml", MY_CLASS_LOADER);
		Resource res_beans_props = new ClassPathResource("beans.properties", MY_CLASS_LOADER);

		GenericApplicationContext ctx = new GenericApplicationContext();
		ctx.setClassLoader(MY_CLASS_LOADER);

		if (res_beans_xml.exists()) {
			XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
			xmlReader.loadBeanDefinitions(res_beans_xml);
			if (log.isDebugEnabled()) {
                log.debug("Loaded context from " + res_beans_xml);
            }
		}
		if (res_beans_props.exists()) {
			PropertiesBeanDefinitionReader propReader = new PropertiesBeanDefinitionReader(ctx);
			propReader.loadBeanDefinitions(res_beans_props);
			if (log.isDebugEnabled()) {
                log.debug("Loaded context from " + res_beans_props);
            }
		}
		ctx.refresh();

		// reset the original CL, to try to prevent crashing with
		// other Java AI implementaitons
		Thread.currentThread().setContextClassLoader(originalContextClassLoader);

		mContext = ctx;
	}

	void update(int frame) {

		if ((frame % (10 * 30)) == 0) {
			System.out.println("Stati for frame ${frame}:");
			for (Agent agent : mAgents) {
				System.out.println("Agent ${agent.getName()}: ${agent.getStatus().getDescription()}");
			}
		}
	}

	public void addTestAgents() {

		Agent agentK    = (Agent) mContext.getBean("agentK");
		Agent agentJ    = (Agent) mContext.getBean("agentJ");
		Agent agentRuby = (Agent) mContext.getBean("rubyAgent");
		Agent agentBsh  = (Agent) mContext.getBean("bshAgent");

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
	public void setCallback(OOAICallback callback) {
		mCallback = callback;
	}

	/* (non-Javadoc)
	 * @see gai.agents.AgentEnvironment#getCallback()
	 */
	public int getTeamId() {
		return mTeamId;
	}
	public void setTeamId(int teamId) {
		mTeamId = teamId;
	}

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

	/* (non-Javadoc)
	 * @see gai.kernel.Environment#enrole(Agent agent)
	 */
	public void enrole(Agent agent) {

		mAgents.add(agent);
		agent.engage(this);
	}
}
