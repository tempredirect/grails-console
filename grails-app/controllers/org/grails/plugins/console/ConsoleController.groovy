package org.grails.plugins.console

import grails.converters.JSON
import grails.util.GrailsUtil
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationContext

class ConsoleController implements ApplicationContextAware {
  ApplicationContext applicationContext
  def grailsApplication

  def index = { }

  def execute = {
    long startTime = System.currentTimeMillis()
    def code = params.code

    def out = new StringWriter()
    def output = new StringWriter()
    def sh = new GroovyShell(new Binding(out: new PrintWriter(output), ctx: applicationContext, grailsApplication: grailsApplication))
    def script = null, result = null

    try {
      script = sh.parse(code)

      try {
        def returnValue = script.run()
        out << output.encodeAsHTML()
        out.println '<span class="script-result">'
        out.println "Result: ${returnValue?.inspect()?.encodeAsHTML()}"
        out.println "&laquo; Executed in ${System.currentTimeMillis() - startTime} ms &raquo;"
        out.println '</span>'
        jsonHeader([success: true] as JSON)
      } catch (Throwable t) {
        jsonHeader([success: false, runtimeError: true] as JSON)
        out << output.encodeAsHTML()
        out.println '<div class="stacktrace">'
        out.println "Exception $t"
        GrailsUtil.printSanitizedStackTrace(t, new PrintWriter(out))
        out.println '</div>'
      }


    } catch (Throwable t) {
      jsonHeader([success: false, compilationError: true] as JSON)
      out << output.encodeAsHTML()
      out.println("Compilation Error: ")
      out.println '<div class="stacktrace">'
      GrailsUtil.printSanitizedStackTrace(t, new PrintWriter(out))
      out.println '</div>'
    }


    result = out.toString()
    render text: result.toString()
  }
}
