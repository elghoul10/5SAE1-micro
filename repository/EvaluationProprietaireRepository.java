package repository;

import android.app.Application;

import java.util.List;
import java.util.concurrent.Executors;

import dao.EvaluationProprietaireDao;
import database.AppDatabase;
import model.EvaluationProprietaire;
import dao.EvaluationProprietaireDao;

public class EvaluationProprietaireRepository {
    private EvaluationProprietaireDao evaluationProprietaireDao;

    public EvaluationProprietaireRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        evaluationProprietaireDao = db.evaluationProprietaireDao();
    }

    public void insert(EvaluationProprietaire evaluation) {
        Executors.newSingleThreadExecutor().execute(() -> evaluationProprietaireDao.insert(evaluation));
    }

    public EvaluationProprietaire getEvaluationByProprietaire(int proprietaireId) {
        return evaluationProprietaireDao.getEvaluationByProprietaire(proprietaireId);
    }

    public void update(EvaluationProprietaire evaluation) {
        Executors.newSingleThreadExecutor().execute(() -> evaluationProprietaireDao.update(evaluation));
    }
    public List<EvaluationProprietaire> getAllEvaluations() {
        return evaluationProprietaireDao.getAllEvaluations();
    }
}
