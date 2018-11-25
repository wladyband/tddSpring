package com.wladimir.tdd.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.wladimir.tdd.modelo.Pessoa;
import com.wladimir.tdd.repository.PessoaRepository;
import com.wladimir.tdd.service.exception.UnicidadeCPFException;
import com.wladimir.tdd.service.impl.PessoaServiceImpl;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

	private static final String Nome = "Wladimir Alves Bandeira";


	private static final String CPF = "04417338400";
	

	@MockBean
	private PessoaRepository pessoaRepository;
	
	private PessoaService pessoaService;
	
	private Pessoa pessoa;
	
	@Before
	public void setUp() throws Exception{
		pessoaService = new PessoaServiceImpl(pessoaRepository);
		
		pessoa = new Pessoa();
		pessoa.setNome(Nome);
		pessoa.setCpf(CPF); 
		
		when(pessoaRepository.findByCPF(CPF)).thenReturn(Optional.empty());
	}
	
	@Test
	public void deve_salva_pessoa_no_repositorio() throws Exception{
		pessoaService.salvar(pessoa);
		
		verify(pessoaRepository).save(pessoa);
	}
	
	@Test(expected = UnicidadeCPFException.class)
	public void nao_deve_salvar_duas_pessoas_com_o_mesmo_CPF() throws Exception{
		when(pessoaRepository.findByCPF(CPF)).thenReturn(Optional.of(pessoa));
		
		pessoaService.salvar(pessoa);
	}
}
