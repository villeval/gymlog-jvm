package gymlog

import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.stereotype.Component

@Component
class configureJetty : WebServerFactoryCustomizer<JettyServletWebServerFactory> {

    override fun customize(factory: JettyServletWebServerFactory) {
        factory.serverCustomizers
    }
}