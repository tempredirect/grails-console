package org.grails.plugins.console

public class GroovyShellTests extends GroovyTestCase {
  void testBinding() {
    def bindings = new Binding('foo0': 'bar0')
    def shell = new GroovyShell(bindings)
    assertEquals 'bar0', shell.evaluate("return foo0")
    shell.evaluate("foo1 = 'bar1'") //without 'def', a variable can be bounded
    assertNotNull bindings.variables.find {k, v -> k == 'foo1'}
    shell.evaluate("def foo2 = 'bar2'") //with 'def' keyword, the variable fail to bind
    assertNull bindings.variables.find {k, v -> k == 'foo2'}
    shell.'foo3' = 'bar3' // set variable with obj.'var'
    assertNotNull bindings.variables.find {k, v -> k == 'foo3'}
  }


}