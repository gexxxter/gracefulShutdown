package edu.slaxxx.tools;


import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TomcatConfigurerer {

	@Bean
	public TomcatServletWebServerFactory undertowServletWebServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {

			@Override
			public void customize(Connector connector) {
				Http11NioProtocol protocolHandler = (Http11NioProtocol) connector.getProtocolHandler();
				protocolHandler.setUseKeepAliveResponseHeader(false);
			}
		});

		return factory;
	}

}
