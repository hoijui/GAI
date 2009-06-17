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


import org.apache.commons.logging.*;

import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

/**
 * Loads the Kernel and agents .
 * @see gai.agents.AgentEnvironment#getCallback()
 */
public class BeanContainer extends StaticApplicationContext {

	public static final ClassLoader MY_CLASS_LOADER = DefaultEnvironment.class.getClassLoader();
    private static Log log = LogFactory.getLog("gai");

	// allows adding beans programmatically
	private StaticListableBeanFactory mProgBeanList = null;

	private BeanContainer(StaticListableBeanFactory progBeanList) {
		super(new GenericApplicationContext(new DefaultListableBeanFactory(progBeanList)));
		mProgBeanList = progBeanList;
	}

	public static BeanContainer getInstance() {
		return new BeanContainer(new StaticListableBeanFactory());
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

		Resource res_beans_xml   = new ClassPathResource("beans.xml", MY_CLASS_LOADER);
		Resource res_beans_props = new ClassPathResource("beans.properties", MY_CLASS_LOADER);

		this.setClassLoader(MY_CLASS_LOADER);

		if (res_beans_xml.exists()) {
			XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(this);
			xmlReader.loadBeanDefinitions(res_beans_xml);
			if (log.isInfoEnabled()) {
                log.info("Loaded context from " + res_beans_xml);
            }
		}
		System.out.println("beans.properties found? " + res_beans_props.exists());
		if (res_beans_props.exists()) {
			PropertiesBeanDefinitionReader propReader = new PropertiesBeanDefinitionReader(this);
			propReader.loadBeanDefinitions(res_beans_props);
			if (log.isInfoEnabled()) {
                log.info("Loaded context from " + res_beans_props);
            }
		}
		((GenericApplicationContext)this.getParent()).refresh();
		this.refresh();

		// reset the original CL, to try to prevent crashing with
		// other Java AI implementations
		Thread.currentThread().setContextClassLoader(originalContextClassLoader);
	}

	public void setupContext() {

		addBean("classLoader", MY_CLASS_LOADER);
	}


	public void addBean(String name, Object bean) {
		mProgBeanList.addBean(name, bean);
	}
	public boolean addBean(String name, Object bean, boolean overwrite) {

		if (overwrite || !isBeanNameInUse(name)) {
			addBean(name, bean);
			return true;
		}
		return false;
	}

}
