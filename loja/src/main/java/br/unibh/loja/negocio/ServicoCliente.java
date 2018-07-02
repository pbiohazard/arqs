package br.unibh.loja.negocio;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import br.unibh.loja.entidades.Cliente;

@Stateless
@LocalBean
public class ServicoCliente implements DAO<Cliente, Long> {
	@Inject
	EntityManager em;
	@Inject
	private Logger log;

	public Cliente insert(Cliente t) throws Exception {
		LocalDate datual = LocalDate.now();
		LocalDate d1 = t.getDataCadastro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long anos = Period.between(datual, d1).getYears();
		
		if(anos >= 1 && !(t.getPerfil().equals("Standard"))){
			throw new Exception("Permitido apenas o perfil Standard");
		}
		
		log.info("Persistindo " + t);
		em.persist(t);
		return t;
	}

	public Cliente update(Cliente t) throws Exception {
		
		LocalDate datual = LocalDate.now();
		LocalDate d1 = t.getDataCadastro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long anos = Period.between(datual, d1).getYears();
		
		
		if(anos >=1 && anos < 5 && !(t.getPerfil().equals("Standard") || t.getPerfil().equals("Premium"))){
			throw new Exception("Permitido apenas perfis Standard e Premium entre 1 e 5 anos");
		}
		else if(anos > 1 && !(t.getPerfil().equals("Standard"))){
			throw new Exception("Permitido perfis Standard apenas com menos de 1 ano");
		}
		else if(anos >= 5 && !(t.getPerfil().equals("Gold") || t.getPerfil().equals("Premium") || t.getPerfil().equals("Standard"))){
			throw new Exception("Permitido apenas os perfis Standard, Premium e Gold");
		}
		
		
		log.info("Atualizando " + t);
		return em.merge(t);
	}

	public void delete(Cliente t) throws Exception {
		log.info("Removendo " + t);
		Object c = em.merge(t);
		em.remove(c);
	}

	public Cliente find(Long k) throws Exception {
		log.info("Encontrando pela chave " + k);
		return em.find(Cliente.class, k);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> findAll() throws Exception {
		log.info("Encontrando todos os objetos");
		return em.createQuery("from Cliente").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> findByName(String name) throws Exception {
		log.info("Encontrando o " + name);
		return em.createNamedQuery("Cliente.findByName").setParameter("nome", "%" + name + "%").getResultList();
	}
}