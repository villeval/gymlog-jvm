package gymlog

import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.stereotype.Component

@Component
class ConfigureJetty : WebServerFactoryCustomizer<JettyServletWebServerFactory> {
    override fun customize(factory: JettyServletWebServerFactory) {
        factory.serverCustomizers
    }
}