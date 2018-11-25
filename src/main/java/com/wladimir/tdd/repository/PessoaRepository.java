package com.wladimir.tdd.repository;

import java.util.Optional;

import com.wladimir.tdd.modelo.Pessoa;

public interface PessoaRepository  {

	Pessoa save(Pessoa pessoa);

	Optional<Pessoa> findByCPF(String cpf);


}
