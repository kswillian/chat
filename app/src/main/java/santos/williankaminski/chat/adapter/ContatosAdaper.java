package santos.williankaminski.chat.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import santos.williankaminski.chat.R;
import santos.williankaminski.chat.model.User;

/**
 * @author Willian Kaminski dos santos
 * @since 05-10-2019
 * @version 0.0.1
 */
public class ContatosAdaper extends RecyclerView.Adapter<ContatosAdaper.MyViewHolder> {

    private List<User> contatos;
    private Context context;


    public ContatosAdaper(List<User> listUser, Context c) {
        this.contatos = listUser;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_contatos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user = contatos.get(position);
        holder.nome.setText(user.getUserName());
        holder.email.setText(user.getUserEmail());

        if(user.getUserPhoto() != null){
            Uri uri = Uri.parse(user.getUserPhoto());
            Glide.with(context).load(uri).into(holder.foto);
        }else{
            holder.foto.setImageResource(R.drawable.default_img);
        }

    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView foto;
        TextView nome, email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foto = itemView.findViewById(R.id.imageViewPerfilContato);
            nome = itemView.findViewById(R.id.textViewNomeContato);
            email = itemView.findViewById(R.id.textViewEmailContato);
        }
    }
}
