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
import santos.williankaminski.chat.model.Talk;
import santos.williankaminski.chat.model.User;

/**
 * @author Willian Kaminski dos santos
 * @since 06-10-2019
 * @version 0.0.1
 */
public class ConversasAdapter extends RecyclerView.Adapter<ConversasAdapter.MyViewHolder>{

    private List<Talk> talks;
    private Context context;

    public ConversasAdapter(List<Talk> talks, Context c) {
        this.talks = talks;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_conversas, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Talk talk = talks.get(position);
        holder.lastMessage.setText(talk.getLastMessage());
        holder.data.setText(talk.getDataLastMessagem().substring(18, 23));

        User user = talk.getUser();
        holder.nome.setText(user.getUserName());

        if(user.getUserPhoto() != null){
            Uri uri = Uri.parse(user.getUserPhoto());
            Glide.with(context).load(uri).into(holder.foto);
        }else{
            holder.foto.setImageResource(R.drawable.default_img);
        }
    }

    @Override
    public int getItemCount() {
        return talks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView foto;
        TextView nome, lastMessage, data;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foto = itemView.findViewById(R.id.imageViewPerfilConversa);
            nome = itemView.findViewById(R.id.textViewNomeConversa);
            lastMessage = itemView.findViewById(R.id.textViewMessageConversa);
            data = itemView.findViewById(R.id.textViewDataConversa);
        }
    }
}
