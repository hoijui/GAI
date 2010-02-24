=begin
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
=end

include Java

module Agents
	include_package "gai.agents"
end

class RubyAgent
	include Agents::Agent

	NAME = "JRuby"
	DESCRIPTION = "sprinkling scripty beauty, nothing more"
	@@status = Agents::AgentStatus.new
	def @@status.getDescription
		"being very ruby"
	end
	def @@status.isOperational
		false
	end

	def setId(id)
		@@id = id
	end

	def getName
		NAME
	end

	def getDescription
		DESCRIPTION
	end

	def getStatus
		@@status
	end

	def engage(environment)
	end

	def disengage
	end

end

# When using JRuby in the context of Spring's dynamic language support,
# you are encouraged to instantiate and return a new instance of the JRuby class
# that you want to use as a dynamic-language-backed bean as the result of
# the execution of your JRuby source.
RubyAgent.new
