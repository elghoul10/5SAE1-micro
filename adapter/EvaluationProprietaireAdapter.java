package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import model.EvaluationProprietaire;
import com.example.yachtjetrental.R;
public class EvaluationProprietaireAdapter extends ListAdapter<EvaluationProprietaire, EvaluationProprietaireAdapter.EvaluationProprietaireViewHolder> {

    public EvaluationProprietaireAdapter() {
        super(DIFF_CALLBACK);
    }

    // DiffUtil pour comparer les objets et optimiser les mises à jour
    private static final DiffUtil.ItemCallback<EvaluationProprietaire> DIFF_CALLBACK = new DiffUtil.ItemCallback<EvaluationProprietaire>() {
        @Override
        public boolean areItemsTheSame(EvaluationProprietaire oldItem, EvaluationProprietaire newItem) {
            return oldItem.getId() == newItem.getId();  // On compare par l'ID des évaluations
        }

        @Override
        public boolean areContentsTheSame(EvaluationProprietaire oldItem, EvaluationProprietaire newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public EvaluationProprietaireViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluation_proprietaire, parent, false);
        return new EvaluationProprietaireViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EvaluationProprietaireViewHolder holder, int position) {
        EvaluationProprietaire evaluation = getItem(position);

        // Remplir les vues avec les données de l'évaluation
        holder.proprietaireIdTextView.setText("Propriétaire ID: " + evaluation.getProprietaireId());
        holder.noteMoyenneTextView.setText("Note Moyenne: " + evaluation.getNoteMoyenne());
        holder.nombreEvaluationsTextView.setText("Nombre d'Évaluations: " + evaluation.getNombreEvaluations());
    }

    // ViewHolder pour chaque item du RecyclerView
    public static class EvaluationProprietaireViewHolder extends RecyclerView.ViewHolder {
        TextView proprietaireIdTextView;
        TextView noteMoyenneTextView;
        TextView nombreEvaluationsTextView;

        public EvaluationProprietaireViewHolder(View itemView) {
            super(itemView);
            proprietaireIdTextView = itemView.findViewById(R.id.proprietaire_id);
            noteMoyenneTextView = itemView.findViewById(R.id.note_moyenne);

        }

    }
}