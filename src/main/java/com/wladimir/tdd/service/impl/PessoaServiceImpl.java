package com.wladimir.tdd.service.impl;

import java.util.Optional;

import com.wladimir.tdd.modelo.Pessoa;
import com.wladimir.tdd.repository.PessoaRepository;
import com.wladimir.tdd.service.PessoaService;
import com.wladimir.tdd.service.exception.UnicidadeCPFException;

public class PessoaServiceImpl implements PessoaService {

	private PessoaRepository pessoaRepository;

	public PessoaServiceImpl(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	@Override
	public Pessoa salvar(Pessoa pessoa) {
		Optional<Pessoa> optinal = pessoaRepository.findByCPF(pessoa.getCpf());
		if(optinal.isPresent()) {
			throw new UnicidadeCPFException();
		}
		return pessoaRepository.save(pessoa);
	}

	
}
