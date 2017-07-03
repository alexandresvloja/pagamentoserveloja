package br.com.servelojapagamento.adapter_recycler_view;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.servelojapagamento.R;
import br.com.servelojapagamento.interfaces.ClickRecyclerViewListener;

/**
 * Created by Alexandre on 03/07/2017.
 */

public class AdapterRvListaDispositivos extends RecyclerView.Adapter<AdapterRvListaDispositivos.ViewHolder> {

    private ArrayList<BluetoothDevice> lista;
    private ClickRecyclerViewListener clickRecyclerViewListener;

    public AdapterRvListaDispositivos(ArrayList<BluetoothDevice> lista, ClickRecyclerViewListener clickRecyclerViewListener) {
        this.lista = lista;
        this.clickRecyclerViewListener = clickRecyclerViewListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTituloDispositivo;

        public ViewHolder(View v) {
            super(v);
            tvTituloDispositivo = (TextView) v.findViewById(R.id.item_rv_dispositivo_titulo);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_lista_dispositivo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTituloDispositivo.setText(lista.get(position).getName());
        holder.tvTituloDispositivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRecyclerViewListener.onClickDispositivo(lista.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void atualizarLista() {
        notifyDataSetChanged();
    }

}

