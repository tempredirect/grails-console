class ConsoleGrailsPlugin {
    def version = '0.1.2',grailsVersion = "1.1-RC1 > *"
    def dependsOn = [:], pluginExcludes = ["grails-app/views/error.gsp"]

    def author = "Siegfried Puchbauer/Mingfai Ma", authorEmail = "siegfried.puchbauer@gmail.com / mingfai.ma@gmail.com"
    def title = "Web-based Groovy Console for Grails"
    def description = '''\\
Install a web-based Groovy consle for interactive runtime application management
'''
    def documentation = "http://grails.org/Console+Plugin"

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
