package br.unibh.loja.negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import br.unibh.loja.entidades.Categoria;
import br.unibh.loja.entidades.Cliente;
import br.unibh.loja.entidades.Produto;
import br.unibh.loja.negocio.DAO;
import br.unibh.loja.negocio.ServicoCategoria;
import br.unibh.loja.negocio.ServicoProduto;
import br.unibh.loja.util.Resources;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteServicoProduto {
	@Deployment
	public static Archive<?> createTestArchive() {
		// Cria o pacote que vai ser instalado no Wildfly para realizacao dos testes
		return ShrinkWrap.create(WebArchive.class, "testeloja.war")
				.addClasses(Categoria.class, Cliente.class, Produto.class, Resources.class, DAO.class,
						ServicoCategoria.class, ServicoProduto.class)
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	// Realiza as injecoes com CDI
	@Inject
	private Logger log;
	
	@Inject
	private ServicoProduto ss;
	
	@Inject
	private ServicoCategoria sc;
	
	@Test
	public void teste00_inserirCategoria() throws Exception {
		log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Categoria c1 = new Categoria(1L, "AAA");
		sc.insert(c1);
		Categoria aux = (Categoria) sc.findByName("AAA").get(0);
		assertNotNull(aux);
		log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	
	@Test
	public void teste01_inserirSemErro() throws Exception {
		log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Categoria c1 = (Categoria) sc.findByName("AAA").get(0);
		Produto o = new Produto(1L, "AAA", "BBB", c1, new BigDecimal(10.00), "kilo");
		ss.insert(o);
		Produto aux = (Produto) ss.findByName("AAA").get(0);
		assertNotNull(aux);
		log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	@Test
	public void teste02_inserirComErro() throws Exception {
	log.info("============> Iniciando o teste " +
	Thread.currentThread().getStackTrace()[1].getMethodName());
	try {
		Categoria c1 = (Categoria) sc.findByName("AAA").get(0);
		Produto o = new Produto(1L, "@@@@@", "BBB", c1, new BigDecimal(10.00), "1kilo");
	ss.insert(o);
	} catch (Exception e){
	
		assertTrue(checkString(e, "Caracteres permitidos: letras, espaços, ponto e aspas simples"));
	}
	log.info("============> Finalizando o teste " +
	Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	@Test
	public void teste03_atualizar() throws Exception {
		log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[0].getMethodName());
		Produto o = (Produto) ss.findByName("AAA").get(0);
		o.setNome("AAAA");
		ss.update(o);
		Produto aux = (Produto) ss.findByName("AAAA").get(0);
		assertNotNull(aux);
		log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[0].getMethodName());
	}

	@Test
	public void teste04_excluir() throws Exception {
		log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Produto o = (Produto) ss.findByName("AAAA").get(0);
		ss.delete(o);
		assertEquals(0, ss.findByName("AAAA").size());
		log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private boolean checkString(Throwable e, String str) {
		if (e.getMessage().contains(str)) {
			return true;
		} else if (e.getCause() != null) {
			return checkString(e.getCause(), str);
		}
		return false;
	}
}