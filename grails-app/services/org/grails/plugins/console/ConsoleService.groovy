package org.grails.plugins.console

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import grails.util.GrailsUtil

/**
 * It is a session-scope service
 */
class ConsoleService implements ApplicationContextAware {
  static Logger logger = LoggerFactory.getLogger(ConsoleService.class)
  static transactional = false, scope = "session"
  ApplicationContext applicationContext
  def grailsApplication
  def shell

  def eval(code) {
    if (logger.isTraceEnabled()) logger.trace("eval() - code: $code, session: $session")
    if (!shell) {
      shell = new GroovyShell(new Binding(ctx: applicationContext, grailsApplication: grailsApplication))
      if (logger.isTraceEnabled()) logger.trace("eval() - created a new Groovy Shell - shell: $shell")
    }
    def result = [output: new StringWriter()]
    shell.setVariable('output', new PrintWriter(result.'output'))
    try {
      result.'returnValue' = shell.evaluate(code)
    } catch (t) {
      result.'exception' = t; result.'stacktrace' = new StringWriter()
      GrailsUtil.printSanitizedStackTrace(t, new PrintWriter(result.'stacktrace'))
    }
    if (logger.isDebugEnabled()) logger.debug("eval() - code: $code, return: $result")
    return result;
  }

}
