package net.pad.irisco;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by s.noori on 10/13/2019.
 */

public class NoteAdapter extends ListAdapter<NoteModel, NoteAdapter.NoteViewHolder> {

    protected NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    public NoteModel getNote(int pos) {
        return getItem(pos);
    }


    private static DiffUtil.ItemCallback DIFF_CALLBACK = new DiffUtil.ItemCallback<NoteModel>() {
        @Override
        public boolean areItemsTheSame(NoteModel oldItem, NoteModel newItem) {
            return oldItem.getId() != newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(NoteModel oldItem, NoteModel newItem) {
            return (oldItem.getTitle().equalsIgnoreCase(newItem.getTitle()) && oldItem.getDescription().equalsIgnoreCase(newItem.getDescription()) && oldItem.getPriority() == (newItem.getPriority()));
        }
    };


    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteModel note = getItem(position);
        holder.txt_id.setText(String.valueOf(note.getId()));
        holder.txt_title.setText(note.getTitle());
        holder.txt_description.setText(note.getDescription());
    }

    private NoteClickListener noteClickListener;

    public interface NoteClickListener {
        void NoteClick(NoteModel noteModel);
    }

    public void setOnItemClickListener(NoteClickListener noteClickListener) {
        this.noteClickListener = noteClickListener;
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView txt_id, txt_title, txt_description;

        public NoteViewHolder(View itemView) {
            super(itemView);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_id = itemView.findViewById(R.id.txt_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (noteClickListener != null && pos != RecyclerView.NO_POSITION)
                        noteClickListener.NoteClick(getItem(pos));
                }
            });
        }
    }


}
