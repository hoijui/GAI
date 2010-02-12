/*
	Copyright (c) 2010
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

package gai.event;


import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * The generic or general event is what all GAI events derive from.
 * It is suposed to be the main method of interaction between
 * the separate parts of the AI, like agents, for example.
 */
public abstract class AbstractGEvent implements GEvent {

	private Collection<String> tags;
	private Map<String, Object> properties;

	public AbstractGEvent() {

		tags       = GEventUtil.newTagsCollection();
		properties = GEventUtil.newPropertiesMap();
	}

	@Override
	public Collection<String> getTags() {
		return Collections.unmodifiableCollection(tags);
	}
	protected void clearTags() {
		this.tags.clear();
	}
	protected void setTags(Collection<String> tags) {

		clearTags();
		addTags(tags);
	}
	protected void addTags(Collection<String> tags) {
		this.tags.addAll(tags);
	}
	protected void addTag(String tag) {
		tags.add(tag);
	}
	protected void removeTag(String tag) {
		tags.remove(tag);
	}

	@Override
	public Map<String, Object> getProperties() {
		return Collections.unmodifiableMap(properties);
	}
	protected void clearProperties() {
		this.properties.clear();
	}
	protected void setProperties(Map<String, Object> properties) {

		clearProperties();
		setProperties(properties);
	}
	protected void addProperties(Map<String, Object> properties) {
		this.properties.putAll(properties);
	}
	protected void addProperty(String name, Object value) {
		properties.put(name, value);
	}
	protected void removeProperty(String name) {
		properties.remove(name);
	}
}
