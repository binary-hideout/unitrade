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
    private OnArticuloListener mOnArticuloListener;

    RecyclerViewAdapter(List<Articulo> arrayarticulos,  OnArticuloListener onArticuloListener) {
        this.arrayarticulos = arrayarticulos;
        this.mOnArticuloListener = onArticuloListener;
    }



    public class ArticulosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView Nombre;
        ImageView Imagencard;
        TextView descripcion;
        OnArticuloListener onArticuloListener;


        ArticulosViewHolder(View itemView, OnArticuloListener onArticuloListener) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            Nombre = (TextView) itemView.findViewById(R.id.card_username);
            Imagencard = (ImageView) itemView.findViewById(R.id.carduser_img);
            descripcion=(TextView)itemView.findViewById(R.id.carduser_desc);
            this.onArticuloListener = onArticuloListener;
            itemView.setOnClickListener(this);
        }

            @Override
            public void onClick(View v) {
                onArticuloListener.onArticuloClick(getAdapterPosition(), arrayarticulos.get(getAdapterPosition()));
            }
        }

    @Override
    public ArticulosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        ArticulosViewHolder cvh = new ArticulosViewHolder(view, mOnArticuloListener);
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

        }else{
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
    public interface OnArticuloListener {
        void onArticuloClick(int position, Articulo articulo);
    }
}
