package org.grails.plugins.console

import grails.converters.JSON
import grails.util.GrailsUtil
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ConsoleController {
  static Logger logger = LoggerFactory.getLogger(ConsoleService.class)
  def consoleService

  def index = {
    render(view:params.'dev'?'dev':'index')
  }

  def execute = {
    long startTime = System.currentTimeMillis()
    def code = params.code
    def out = new StringWriter()

    def script = null, result = null, output

    try {
      result = consoleService.eval(code)
      if (!result.'exception') {
        out << result.'out'?.encodeAsHTML()
        out.println '<span class="script-result">'
        out.println "Result: ${result.'returnValue'?.inspect()?.encodeAsHTML()}"
        out.println "&laquo; Executed in ${System.currentTimeMillis() - startTime} ms &raquo;"
        out.println '</span>'
        jsonHeader([success: true] as JSON)
      } else {
        jsonHeader([success: false, runtimeError: true] as JSON)
        out << result.'out'?.encodeAsHTML()
        out.println '<div class="stacktrace">'
        out.println "Exception ${result.'exception'}"
        out.println "${result.'stacktrace'}"
        out.println '</div>'
      }

    } catch (Throwable t) {
      logger.error(t.message, t)
      jsonHeader([success: false, compilationError: true] as JSON)
      out << output?.encodeAsHTML()
      out.println("Compilation Error: ")
      out.println '<div class="stacktrace">'
      GrailsUtil.printSanitizedStackTrace(t, new PrintWriter(out))
      out.println '</div>'
    }
    
    render text: out.toString()
  }
}
