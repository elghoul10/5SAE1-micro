package activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yachtjetrental.R;

import java.util.List;
import java.util.Map;

import adapter.EvaluationProprietaireAdapter;
import model.EvaluationProprietaire;
import viewmodel.AvisViewModel;
import viewmodel.EvaluationProprietaireViewModel;

public class ActivityEvaluationProprietaire extends AppCompatActivity {

    private EvaluationProprietaireViewModel evaluationViewModel;
    private RecyclerView recyclerView;
    private EvaluationProprietaireAdapter adapter;
    private AvisViewModel avisViewModel;

    // TextViews pour afficher la répartition des catégories
    private TextView textViewCategorie1;
    private TextView textViewCategorie2;
    private TextView textViewCategorie3;
    private TextView textViewCategorie4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_proprietaire);


        // Initialisation du RecyclerView
        recyclerView = findViewById(R.id.recycler_view_evaluation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialiser l'adapter pour le RecyclerView
        adapter = new EvaluationProprietaireAdapter();
        recyclerView.setAdapter(adapter);

        // Initialisation du ViewModel
        evaluationViewModel = new ViewModelProvider(this).get(EvaluationProprietaireViewModel.class);
        avisViewModel = new ViewModelProvider(this).get(AvisViewModel.class);

        // Initialisation des TextViews pour les catégories
        textViewCategorie1 = findViewById(R.id.textViewCategorie1);
        textViewCategorie2 = findViewById(R.id.textViewCategorie2);
        textViewCategorie3 = findViewById(R.id.textViewCategorie3);
        textViewCategorie4 = findViewById(R.id.textViewCategorie4);

        // Exemple de filtrage par note minimale
        double noteMin = 4.0;  // Note minimale que tu veux filtrer
        // Appel de la méthode dans le ViewModel pour récupérer les évaluations filtrées
        List<EvaluationProprietaire> evaluationsFiltrees = evaluationViewModel.filtrerProprietairesParNote(noteMin);

        // Passer la liste filtrée à l'adapter pour l'afficher
        adapter.submitList(evaluationsFiltrees);

        // Appel de la méthode pour analyser la répartition des catégories
        analyserRepartitionCategories(1); // userId = 1, tu peux passer le bon ID de l'utilisateur ici
    }

    // Méthode pour analyser la répartition des catégories d'avis et mettre à jour l'UI
    private void analyserRepartitionCategories(int userId) {
        avisViewModel.analyserRepartitionCategories(userId, new AvisViewModel.CategoryCountCallback() {
            @Override
            public void onResult(Map<String, Integer> categories) {
                // Mettre à jour l'UI avec les résultats de la répartition des catégories
                afficherRepartitionCategories(categories);
            }
        });
    }

    // Méthode pour afficher la répartition des catégories dans les TextViews
    private void afficherRepartitionCategories(Map<String, Integer> repartitionCategories) {
        // Tableau des catégories connues (ou potentiellement dynamiques)
        String[] categories = {"Catégorie 1", "Catégorie 2", "Catégorie 3", "Catégorie 4"};
        TextView[] textViews = {textViewCategorie1, textViewCategorie2, textViewCategorie3, textViewCategorie4};

        // Parcours des catégories et des TextViews associés
        for (int i = 0; i < categories.length; i++) {
            String categorie = categories[i];
            int count = repartitionCategories.getOrDefault(categorie, 0); // On récupère la valeur ou 0 si elle n'existe pas
            String text = categorie + ": " + count + " avis"; // Préparer le texte à afficher

            // Si la catégorie existe dans la map, on l'affiche, sinon on affiche "Aucune donnée"
            if (count > 0) {
                textViews[i].setText(text); // Afficher la catégorie
            } else {
                textViews[i].setText(categorie + ": Aucune donnée"); // Afficher un message alternatif si la catégorie est absente
            }
        }
    }
}
