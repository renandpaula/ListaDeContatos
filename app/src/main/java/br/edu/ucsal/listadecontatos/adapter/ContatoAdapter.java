package br.edu.ucsal.listadecontatos.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.ucsal.listadecontatos.R;
import br.edu.ucsal.listadecontatos.model.Contato;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.MinhaViewHolder> {

    private List<Contato> listaContatos;

    public ContatoAdapter(List<Contato> lista) {
        this.listaContatos = lista;
    }

    @NonNull
    @Override
    public MinhaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemLista = LayoutInflater.from(viewGroup.getContext())
                                        .inflate(R.layout.lista_contato_adapter, viewGroup, false);

        return new MinhaViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MinhaViewHolder minhaViewHolder, int i) {

        Contato contato = listaContatos.get(i);
        minhaViewHolder.contato.setText(contato.getNome());

    }

    @Override
    public int getItemCount() {

        return this.listaContatos.size();

    }

    public class MinhaViewHolder extends RecyclerView.ViewHolder{

        TextView contato;

        public MinhaViewHolder(@NonNull View itemView) {

            super(itemView);

            contato = itemView.findViewById(R.id.textViewContato);
        }

    }
}
