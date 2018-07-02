package br.unibh.loja.integracao;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.unibh.loja.entidades.Cliente;
import br.unibh.loja.negocio.ServicoCliente;
import io.swagger.annotations.Api;

@Api
@Path("cliente")
public class RestCliente {
	@Inject
	private ServicoCliente cl;

	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cliente> helloworld() throws Exception {
		return cl.findAll();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Cliente hello(@PathParam("id") final Long id) throws Exception {
		return cl.find(id);
	}
}