package android.triadworks.cursos.appevento.adapter;

import android.support.v7.widget.RecyclerView;
import android.triadworks.cursos.appevento.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by handersonbf on 02/09/16.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolderRecycle> {
    private String[] dados;
    public RecycleAdapter(String[] dados) {
        this.dados = dados;
    }

    public static class ViewHolderRecycle extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolderRecycle(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.id_text_view);
        }
    }
    @Override
    public RecycleAdapter.ViewHolderRecycle onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.text_view_layout, parent, false);
//
//        ViewHolderRecycle vh = new ViewHolderRecycle(view);

        return new ViewHolderRecycle(LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolderRecycle holder, int position) {
        holder.mTextView.setText(dados[position]);
    }

    @Override
    public int getItemCount() {
        return dados.length;
    }
}
