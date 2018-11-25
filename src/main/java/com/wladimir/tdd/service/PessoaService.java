package com.wladimir.tdd.service;

import com.wladimir.tdd.modelo.Pessoa;
import com.wladimir.tdd.modelo.Telefone;

public interface PessoaService {

	Pessoa salvar(Pessoa pessoa);

	Pessoa buscarPorTelefone(Telefone telefone);

}
