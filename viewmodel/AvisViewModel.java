package viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlinx.coroutines.Dispatchers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.Avis;
import repository.AvisRepository;

public class AvisViewModel extends AndroidViewModel {
    private AvisRepository avisRepository;
    private ExecutorService executorService;

    public AvisViewModel(Application application) {
        super(application);
        avisRepository = new AvisRepository(application);
        executorService = Executors.newSingleThreadExecutor();  // Executor pour gérer les tâches en arrière-plan
    }

    // Insertion d'un avis dans la base de données de manière asynchrone
    public void insert(Avis avis) {
        executorService.execute(() -> avisRepository.insert(avis));
    }

    // Récupérer tous les avis d'un utilisateur sous forme de LiveData
    public LiveData<List<Avis>> getAvisByUser(int userId) {
        return avisRepository.getAvisLiveByUser(userId);  // Renvoie un LiveData pour observer les changements dans la UI
    }

    // Mettre à jour un avis dans la base de données de manière asynchrone
    public void update(Avis avis) {
        executorService.execute(() -> avisRepository.update(avis));
    }

    // Supprimer un avis de la base de données de manière asynchrone
    public void delete(Avis avis) {
        executorService.execute(() -> avisRepository.delete(avis));
    }

    // Calculer la note moyenne d'un utilisateur de manière asynchrone
    public void calculerNoteMoyenne(int userId, NoteMoyenneCallback callback) {
        // Utilisation de l'executor pour effectuer la tâche en arrière-plan
        executorService.execute(() -> {
            List<Avis> avisList = avisRepository.getAvisByUser(userId);
            if (avisList.isEmpty()) {
                callback.onResult(0);  // Si la liste est vide, renvoyer 0
                return;
            }

            double somme = 0;
            for (Avis avis : avisList) {
                somme += avis.getNote();  // Calculer la somme des notes
            }
            double moyenne = somme / avisList.size();  // Calculer la moyenne
            callback.onResult(moyenne);  // Retourner la moyenne via le callback
        });
    }

    // Analyser la répartition des catégories d'avis pour un utilisateur donné
    public void analyserRepartitionCategories(int userId, CategoryCountCallback callback) {
        // Utilisation de l'executor pour effectuer la tâche en arrière-plan
        executorService.execute(() -> {
            List<Avis> avisList = avisRepository.getAvisByUser(userId);
            Map<String, Integer> categorieCount = new HashMap<>();  // Dictionnaire pour compter les catégories

            // Compter les occurrences de chaque catégorie dans les avis
            for (Avis avis : avisList) {
                String categorie = avis.getCategorieRetour();  // Catégorie de l'avis
                int count = categorieCount.getOrDefault(categorie, 0);  // Récupérer le nombre de fois que la catégorie apparaît
                categorieCount.put(categorie, count + 1);  // Incrémenter le compteur de cette catégorie
            }
            callback.onResult(categorieCount);  // Retourner le comptage des catégories via le callback
        });
    }

    // Interface pour retourner la note moyenne calculée
    public interface NoteMoyenneCallback {
        void onResult(double moyenne);  // Méthode de rappel pour la note moyenne
    }

    // Interface pour retourner la répartition des catégories
    public interface CategoryCountCallback {
        void onResult(Map<String, Integer> categories);  // Méthode de rappel pour les catégories
    }
}
