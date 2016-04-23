package br.edu.fa7.clientes;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import br.edu.fa7.clientes.resources.ClienteResource;

@Configuration
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		register(ClienteResource.class);
	}
}
