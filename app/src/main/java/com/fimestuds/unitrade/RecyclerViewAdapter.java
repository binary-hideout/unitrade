package com.fimestuds.unitrade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ArticulosViewHolder> {
    private final List<Articulo> arrayarticulos;

    RecyclerViewAdapter(List<Articulo> arrayarticulos) {
        this.arrayarticulos = arrayarticulos;
    }

    public class ArticulosViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView Nombre;
        ImageView Imagencard;
        TextView descripcion;

        ArticulosViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            Nombre = (TextView) itemView.findViewById(R.id.card_username);
            Imagencard = (ImageView) itemView.findViewById(R.id.carduser_img);
            descripcion=(TextView)itemView.findViewById(R.id.carduser_desc);
        }
    }

    @Override
    public ArticulosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        ArticulosViewHolder cvh = new ArticulosViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(ArticulosViewHolder holder, int position) {
        holder.Nombre.setText(arrayarticulos.get(position).art_name);

        holder.descripcion.setText(arrayarticulos.get(position).descripcion);


        if (arrayarticulos.get(position).getImagen() != null) {
            // Si existe la imagen la cargamos
            Glide.with(holder.itemView.getContext())
                    .load(arrayarticulos.get(position).getImagen())
                    .into(holder.Imagencard);

        }else if( arrayarticulos.get(position).getType()==1){

            holder.Imagencard.setImageResource(R.drawable.unitrade);
        }
        else if( arrayarticulos.get(position).getType()==2){

            holder.Imagencard.setImageResource(R.drawable.zarpazo);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return arrayarticulos.size();
    }
}
