package com.wladimir.tdd.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.wladimir.tdd.modelo.Pessoa;
import com.wladimir.tdd.modelo.Telefone;
import com.wladimir.tdd.repository.PessoaRepository;
import com.wladimir.tdd.service.exception.TelefoneNaoEncontradoException;
import com.wladimir.tdd.service.exception.UnicidadeCPFException;
import com.wladimir.tdd.service.exception.UnicidadeTelefoneException;
import com.wladimir.tdd.service.impl.PessoaServiceImpl;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

	private static final String _NUMERO = "34342017";
	private static final String _DDD	 = "55";
	private static final String Nome = "Wladimir Alves Bandeira";
	private static final String CPF = "04417338400";
	
	
	

	@MockBean
	private PessoaRepository pessoaRepository;
	
	private PessoaService pessoaService;
	
	private Pessoa pessoa;
	private Telefone telefone;
	
	@Before
	public void setUp() throws Exception{
		pessoaService = new PessoaServiceImpl(pessoaRepository);
		
		pessoa = new Pessoa();
		pessoa.setNome(Nome);
		pessoa.setCpf(CPF); 
		
		telefone = new Telefone();
	
		telefone.setDdd(_DDD);
		telefone.setNumero(_NUMERO);
		
		pessoa.setTelefone(Arrays.asList(telefone));
		
	    when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(_DDD, _NUMERO)).thenReturn(Optional.empty());
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
	
	@Test(expected = UnicidadeTelefoneException.class)
	public void nao_deve_salvar_duas_pessoas_com_o_mesmo_TELEFONE() throws Exception{
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(_DDD, _NUMERO)).thenReturn(Optional.of(pessoa));
		
		pessoaService.salvar(pessoa);
	}
	
	@Test(expected = TelefoneNaoEncontradoException.class)
	public void deve_retornar_excecao_de_nao_encontrar_quando_nao_existir__pessoa_pelo_DDD_e_Telefone() throws Exception{
		pessoaService.buscarPorTelefone(telefone);
	}
	
	
	@Test
	public void deve_buscar_pessoa_pelo_DDD_e_Telefone() throws Exception{
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(_DDD, _NUMERO)).thenReturn(Optional.of(pessoa));
		
		Pessoa pessoaTest = pessoaService.buscarPorTelefone(telefone);

		verify(pessoaRepository).findByTelefoneDddAndTelefoneNumero(_DDD, _NUMERO);
	
		assertThat(pessoaTest).isNotNull();
		assertThat(pessoaTest.getNome()).isEqualTo(Nome);
		assertThat(pessoaTest.getCpf()).isEqualTo(CPF);
	}
}
