package br.unibh.loja;


import java.math.BigDecimal;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.unibh.loja.entidades.Categoria;
import br.unibh.loja.entidades.Produto;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteCliente {
	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		System.out.println("Inicializando validador...");
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testeValidacaoProduto1() {
		long l = 1;
		BigDecimal b1 = new BigDecimal(10000);
		Categoria c1 = new Categoria(l, "categoria1");
		Produto p1 = new Produto(l, "bicicleta", "caloi", c1, b1, "mm");
		System.out.println(p1);
		Set<ConstraintViolation<Produto>> constraintViolations = validator.validate(p1);
		for (ConstraintViolation<Produto> c : constraintViolations) {
			System.out.println(" Erro de Validacao: " + c.getMessage());
		}
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void testeValidacaoProduto2() {
		long l = 1;
		BigDecimal b1 = new BigDecimal(10000);
		Categoria c1 = new Categoria(l, "categoria1");
		Produto p1 = new Produto(l, "moto", "", c1, b1, "ch");
		System.out.println(p1);
		Set<ConstraintViolation<Produto>> constraintViolations = validator.validate(p1);
		for (ConstraintViolation<Produto> c : constraintViolations) {
			System.out.println(" Erro de Validacao: " + c.getMessage());
		}
		Assert.assertEquals(1, constraintViolations.size());
	}

	
}