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
