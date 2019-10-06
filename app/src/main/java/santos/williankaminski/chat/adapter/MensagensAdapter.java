package santos.williankaminski.chat.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import santos.williankaminski.chat.R;
import santos.williankaminski.chat.model.Message;
import santos.williankaminski.chat.util.UserFirebase;

/**
 * @author Willian Kaminski dos santos
 * @since 06-10-2019
 * @version 0.0.1
 */
public class MensagensAdapter extends RecyclerView.Adapter<MensagensAdapter.MyViewHolder> {

    private List<Message> messages;
    private Context context;

    private static final int SENDER = 0;
    private static final int ADDRESS = 1;

    public MensagensAdapter(List<Message> listMessage, Context c) {
        this.context = c;
        this.messages = listMessage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;

        if(viewType == SENDER){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sender, parent, false);

        }else if(viewType == ADDRESS){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_address, parent, false);
        }

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Message message = messages.get(position);

        String msg = message.getMessage();
        String msgDt = message.getDate();
        String img = message.getPhoto();

        if(img != null){ // retorna a imagem e data

            Uri uri = Uri.parse(img);
            Glide.with(context).load(uri).into(holder.image);
            holder.mensagemDate.setText(msgDt.substring(18, 24));
            holder.mensagem.setVisibility(View.GONE);

        }else{ // retorno a mensagem e data
            holder.image.setVisibility(View.GONE);
            holder.mensagem.setText(msg);
            holder.mensagemDate.setText(msgDt.substring(18, 23));

        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {

        Message message = messages.get(position);
        String idUser = UserFirebase.getUserId();

        if(idUser.equals(message.getIdUser())){
            return SENDER;
        }

        return ADDRESS;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mensagem;
        ImageView image;
        TextView mensagemDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mensagem = itemView.findViewById(R.id.textViewMensagemText);
            image = itemView.findViewById(R.id.imageViewMensagemFoto);
            mensagemDate = itemView.findViewById(R.id.textViewMensagemData);

        }
    }
}
