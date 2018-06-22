package br.unibh.loja;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import br.unibh.loja.entidades.Categoria;
import br.unibh.loja.entidades.Cliente;
import br.unibh.loja.entidades.Produto;

import org.junit.Assert;

public class Teste {
	@Test
	public void TesteCliente() {
		Date data = new Date();

		Cliente c1 = new Cliente(74565L, "Paulo Rodrigues", "biohazard", "123456", "Cliente", "300.100.700-80",
				"31-1111-1111", "resident@biohazard.com", data, data);

		Cliente c2 = new Cliente(74565L, "Paulo Rodrigues", "biohazard", "123456", "Cliente", "300.100.700-80",
				"31-1111-1111", "resident@biohazard.com", data, data);

		c1.toString();
		c2.toString();
		assertEquals(c1.equals(c2), true);
	}

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		System.out.println("Inicializando validador...");
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void TestCategoria() {
		Categoria cat1 = new Categoria(212L, "Teste categoria");
		Categoria cat2 = new Categoria(212L, "Teste categoria");

		cat1.toString();
		cat2.toString();

		assertEquals(cat1.equals(cat2), true);
	}

	@Test
	public void TesteProduto() {
		Categoria cat1 = new Categoria(212L, "Categoria 34");
		Produto prod1 = new Produto(2222L, "produto 44", "produto de teste 1", cat1, new BigDecimal(10.99),
				"fabrica 2");
		Produto prod2 = new Produto(2222L, "produto 44", "produto de teste 1", cat1, new BigDecimal(10.99),
				"fabrica 2");
	}

	// BEANVALIDATION
	/*@Test
	public void testeValidacaoCliente() {
		Date data = new Date();
		
		Cliente cl = new Cliente(1L, "Paulo", "Biohazard", "ABC123", "babaca", "12345678901", "31123412345", "email@email.com", data, data);
		System.out.println(cl);
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cl);
		for (ConstraintViolation<Cliente> c : constraintViolations) {
			System.out.println(" Erro de Validacao: " + c.getMessage());
		}
		Assert.assertEquals(0, constraintViolations.size());
	}

	
	public void testeValidacaoCategoria(){
		Date data = new Date();
		
		Categoria cate = new Categoria(1L, "teste categoria");
		System.out.println(cate);
		Set<ConstraintViolation<Categoria>> constraintViolations = validator.validate(cate);
		for (ConstraintViolation<Categoria> c : constraintViolations) {
			System.out.println(" Erro de Validacao: " + c.getMessage());
		}
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	public void testeValidacaoProduto(){
		Date data = new Date();
		
		Categoria cate = new Categoria(1L, "teste categoria");
		System.out.println(cate);
		Set<ConstraintViolation<Categoria>> constraintViolations = validator.validate(cate);
		for (ConstraintViolation<Categoria> c : constraintViolations) {
			System.out.println(" Erro de Validacao: " + c.getMessage());
		}
		Assert.assertEquals(0, constraintViolations.size());
	}*/
}
