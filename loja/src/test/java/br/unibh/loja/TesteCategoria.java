package br.unibh.loja;


import java.util.Date;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteCategoria {
	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		System.out.println("Inicializando validador...");
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testeValidacaoCategoria(){
		//Date data = new Date();	
		Categoria cate = new Categoria(1L, "teste categoria");
		System.out.println(cate);
		Set<ConstraintViolation<Categoria>> constraintViolations = validator.validate(cate);
		for (ConstraintViolation<Categoria> c : constraintViolations) {
			System.out.println(" Erro de Validacao: " + c.getMessage());
		}
		Assert.assertEquals(0, constraintViolations.size());
	}

	@Test
	public void testeValidacaoCategoria2(){
		//Date data = new Date();	
		Categoria cate = new Categoria(1L, "teste categoria@@@@");
		System.out.println(cate);
		Set<ConstraintViolation<Categoria>> constraintViolations = validator.validate(cate);
		for (ConstraintViolation<Categoria> c : constraintViolations) {
			System.out.println(" Erro de Validacao: " + c.getMessage());
		}
		Assert.assertEquals(1, constraintViolations.size());
	}
	
}