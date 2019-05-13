package br.edu.ucsal.listadecontatos.helper;

import java.util.List;

import br.edu.ucsal.listadecontatos.model.Contato;

public interface IContatoDAO {

    public boolean salvar(Contato contato);

    public boolean atualizar(Contato contato);

    public boolean deletar(Contato contato);

    public List<Contato> listar();



}
