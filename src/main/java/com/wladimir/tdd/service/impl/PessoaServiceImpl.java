package com.wladimir.tdd.service.impl;

import java.util.Optional;

import com.wladimir.tdd.modelo.Pessoa;
import com.wladimir.tdd.modelo.Telefone;
import com.wladimir.tdd.repository.PessoaRepository;
import com.wladimir.tdd.service.PessoaService;
import com.wladimir.tdd.service.exception.TelefoneNaoEncontradoException;
import com.wladimir.tdd.service.exception.UnicidadeCPFException;
import com.wladimir.tdd.service.exception.UnicidadeTelefoneException;

public class PessoaServiceImpl implements PessoaService {

	private PessoaRepository pessoaRepository;

	public PessoaServiceImpl(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	@Override
	public Pessoa salvar(Pessoa pessoa) throws UnicidadeCPFException, UnicidadeTelefoneException {
		Optional<Pessoa> optinal = pessoaRepository.findByCPF(pessoa.getCpf());
		if(optinal.isPresent()) {
			throw new UnicidadeCPFException();
		}
		
		String ddd = pessoa.getTelefone().get(0).getDdd();
		String numero = pessoa.getTelefone().get(0).getNumero();
		optinal = pessoaRepository.findByTelefoneDddAndTelefoneNumero(ddd, numero );
		if(optinal.isPresent()) {
			throw new UnicidadeTelefoneException();
		}
		
		
		return pessoaRepository.save(pessoa);
	}

	@Override
	public Pessoa buscarPorTelefone(Telefone telefone) {
		final Optional<Pessoa> optinal = pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
		return optinal.orElseThrow(() -> new TelefoneNaoEncontradoException());
	}

	
}
