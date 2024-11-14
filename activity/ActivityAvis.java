package activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yachtjetrental.R;

import java.util.List;

import model.Avis;
import viewmodel.AvisViewModel;
import adapter.AvisAdapter;

public class ActivityAvis extends AppCompatActivity {
    private AvisViewModel avisViewModel;
    private RecyclerView recyclerView;
    private AvisAdapter adapter;
    private Button btnAjouterAvis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avis);

        // Initialiser le RecyclerView et l'adaptateur
        recyclerView = findViewById(R.id.recycler_view_avis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AvisAdapter(null);  // Passer null initialement
        recyclerView.setAdapter(adapter);

        // Initialiser le ViewModel
        avisViewModel = new ViewModelProvider(this).get(AvisViewModel.class);

        // Observer les changements dans les avis
        avisViewModel.getAvisByUser(1).observe(this, avisList -> {
            // Mettre à jour l'adaptateur lorsque les avis changent
            adapter.submitList(avisList);
        });

        // Initialiser le bouton "Ajouter Avis"
        btnAjouterAvis = findViewById(R.id.btn_ajouter_avis);
        btnAjouterAvis.setOnClickListener(v -> ajouterAvis());
    }

    // Méthode pour ajouter un avis
    private void ajouterAvis() {
        // Données de test pour l'avis
        String commentaire = "Super produit, je suis très satisfait !";
        int note = 4;  // Note sur 5
        String categorie = "Service";  // Exemple de catégorie

        // Créer un nouvel objet Avis
        Avis nouvelAvis = new Avis(1, commentaire, note, System.currentTimeMillis(), categorie);

        // Ajouter l'avis dans le ViewModel (et donc dans la base de données)
        avisViewModel.insert(nouvelAvis);

        // Afficher un message de confirmation
        Toast.makeText(ActivityAvis.this, "Avis ajouté avec succès", Toast.LENGTH_SHORT).show();
    }
}
