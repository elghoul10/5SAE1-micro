package repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.Executors;
import database.AppDatabase;


import dao.AvisDao;
import model.Avis;

public class AvisRepository {
    private static AvisDao avisDao;

    public AvisRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        avisDao = db.avisDao();
    }

    public void insert(Avis avis) {
        Executors.newSingleThreadExecutor().execute(() -> avisDao.insert(avis));
    }

    public static List<Avis> getAvisByUser(int userId) {
        return avisDao.getAvisByUser(userId);
    }
    // Récupérer les avis d'un utilisateur via LiveData pour pouvoir les observer
    public LiveData<List<Avis>> getAvisLiveByUser(int userId) {
        return avisDao.getAvisLiveByUser(userId);  // Retourne un LiveData
    }
    public LiveData<List<Avis>> getAvisByUserLiveData(int userId) {
        return avisDao.getAvisLiveByUser(userId);  // assurez-vous que la méthode dans le DAO retourne LiveData
    }


    public void update(Avis avis) {
        Executors.newSingleThreadExecutor().execute(() -> avisDao.update(avis));
    }

    public void delete(Avis avis) {
        Executors.newSingleThreadExecutor().execute(() -> avisDao.delete(avis));
    }
}
