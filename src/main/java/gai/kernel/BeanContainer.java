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

package gai.kernel;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Loads the Kernel and agents .
 * @see gai.agents.AgentEnvironment#getCallback()
 */
public class BeanContainer extends GenericApplicationContext {

	public static final ClassLoader MY_CLASS_LOADER = BeanContainer.class.getClassLoader();
	private static Log log = LogFactory.getLog("gai");

	private ApplicationContext context = null;

	public static BeanContainer getInstance() {
		return new BeanContainer();
	}

	public void initContext() {

		// TODO FIXME: ClassLoader issue!!
		// Without this line, JRuby and BeanShell scripts can not be loaded
		// through the spring framework.
		// But this line will probably cause problems when our AI is used
		// together with other AI implementations that rely on the context
		// class loader as well, if they have to use a similar hack.
		ClassLoader originalContextClassLoader =
				Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(MY_CLASS_LOADER);
		//this.setClassLoader(MY_CLASS_LOADER);

		context = new ClassPathXmlApplicationContext("beans.xml");
		setParent(context);


		// reset the original CL, to try to prevent crashing with
		// other Java AI implementations
		Thread.currentThread().setContextClassLoader(originalContextClassLoader);
	}
}
