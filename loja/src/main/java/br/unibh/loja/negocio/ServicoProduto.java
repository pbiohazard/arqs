package br.unibh.loja.negocio;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import br.unibh.loja.entidades.Produto;

@Stateless
@LocalBean
public class ServicoProduto implements DAO<Produto, Long> {
	
	@Inject
	EntityManager em;
	
	@Inject
	private Logger log;

	public Produto insert(Produto t) throws Exception {
		log.info("Persistindo " + t);
		em.persist(t);
		return t;
	}

	public Produto update(Produto t) throws Exception {
		log.info("Atualizando " + t);
		return em.merge(t);
	}

	public void delete(Produto t) throws Exception {
		log.info("Removendo " + t);
		Object c = em.merge(t);
		em.remove(c);
	}

	public Produto find(Long k) throws Exception {
		log.info("Encontrando pela chave " + k);
		return em.find(Produto.class, k);
	}

	@SuppressWarnings("unchecked")
	public List<Produto> findAll() throws Exception {
		log.info("Encontrando todos os objetos");
		return em.createQuery("from Produto").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Produto> findByName(String name) throws Exception {
		log.info("Encontrando o " + name);
		return em.createNamedQuery("Produto.findByName").setParameter("nome", "%" + name + "%").getResultList();
	}
}