package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yachtjetrental.R;

import java.util.List;

import model.Avis;

public class AvisAdapter extends RecyclerView.Adapter<AvisAdapter.AvisViewHolder> {
    private List<Avis> avisList;

    // Constructeur
    public AvisAdapter(List<Avis> avisList) {
        this.avisList = avisList;
    }

    // Méthode pour mettre à jour les données dans l'adaptateur
    public void submitList(List<Avis> newAvisList) {
        this.avisList = newAvisList;
        notifyDataSetChanged();  // Met à jour l'affichage du RecyclerView
    }

    @NonNull
    @Override
    public AvisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Créer la vue pour chaque élément de la liste
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_avis, parent, false);
        return new AvisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvisViewHolder holder, int position) {
        // Récupérer un avis et l'afficher dans les TextViews
        Avis avis = avisList.get(position);
        holder.commentaire.setText(avis.getCommentaire());
        holder.note.setText("Note : " + avis.getNote() + "/5");
        holder.date.setText("Date : " + avis.getDate());
    }

    @Override
    public int getItemCount() {
        return avisList == null ? 0 : avisList.size();
    }

    static class AvisViewHolder extends RecyclerView.ViewHolder {
        TextView commentaire, note, date;

        // Le constructeur du ViewHolder
        AvisViewHolder(@NonNull View itemView) {
            super(itemView);
            commentaire = itemView.findViewById(R.id.commentaire);
            note = itemView.findViewById(R.id.note);
            date = itemView.findViewById(R.id.date);
        }
    }
}
