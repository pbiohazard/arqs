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

import br.unibh.loja.entidades.Cliente;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteProduto {
	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		System.out.println("Inicializando validador...");
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testeValidacaoCliente1() {
		long l = 1;
		Cliente cl = new Cliente(l, "Paulo", "pbiohazard", "12345", "biohazard", "09312894676", "(99)9999-9999",
				"biohazardpaulo@gmail.com", new Date(), new Date());
		System.out.println(cl);
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cl);
		for (ConstraintViolation<Cliente> c : constraintViolations) {
			System.out.println(" Erro de Validacao: " + c.getMessage());
		}
		Assert.assertEquals(0, constraintViolations.size());
	}

	@Test
	public void testeValidacaoCliente2() {
		long l = 1;
		Cliente cl = new Cliente(l, "Paulo", "pbiohazard", "12345", "biohazard", "##093.128.946-76", "(99)9999-9999",
				"biohazardpaulo@gmail.com", new Date(), new Date());
		System.out.println(cl);
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cl);
		for (ConstraintViolation<Cliente> c : constraintViolations) {
			System.out.println(" Erro de Validacao: " + c.getMessage());
		}
		Assert.assertEquals(1, constraintViolations.size());
	}

	
}