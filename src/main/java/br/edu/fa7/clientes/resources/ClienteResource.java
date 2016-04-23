package br.edu.fa7.clientes.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.fa7.clientes.model.Cliente;
import br.edu.fa7.clientes.model.Endereco;
import br.edu.fa7.clientes.service.ClienteService;

@Component
@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@GET
	public List<Cliente> findAll() {
		List<Cliente> clientes = service.findAll();
		for(Cliente cliente : clientes) {
			removerRefereciaCirular(cliente);;
		}
		return clientes;
	}
	
	@GET
	@Path("{id}")
	public Response find(@PathParam("id") Long id) {
		Cliente cliente = service.find(id);
		if (cliente != null) {
			removerRefereciaCirular(cliente);
			return Response.ok(cliente).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	private void removerRefereciaCirular(Cliente cliente) {
		if(cliente.getEnderecos() != null) {
			removerReferenciaCliente(cliente.getEnderecos());
		}
		
	}

	private void removerReferenciaCliente(List<Endereco> enderecos) {
		for(Endereco endereco : enderecos) {
			endereco.setCliente(null);
		}
	}

	@POST
	public Response save(Cliente cliente) {
		service.save(cliente);
		return Response.ok().build();
	}
	
	@PUT
	@Path("{id}")
	public Response update(@PathParam ("id") Long id, Cliente cliente) {
		service.update(id, cliente);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam ("id") Long id){
		Cliente cliente = service.find(id);
		if(cliente != null) {
			service.delete(id);
			return Response.ok().build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("{id}/enderecos")
	public List<Endereco> findEnderecos(@PathParam ("id") Long id) {
		List<Endereco> enderecos = service.findEnderecosByCliente(id);
		removerReferenciaCliente(enderecos);
		return enderecos;
	}
	
	@GET
	@Path("{id}/enderecos/{eid}")
	public Response getEndereco(@PathParam ("id") Long id, @PathParam ("eid") Long eid) {
		Endereco endereco = service.findEnderecoByCliente(id, eid);
		if(endereco != null) {
			endereco.setCliente(null);
			return Response.ok(endereco).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
