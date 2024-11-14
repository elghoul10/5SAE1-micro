package viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import model.EvaluationProprietaire;
import repository.EvaluationProprietaireRepository;

public class EvaluationProprietaireViewModel extends AndroidViewModel{
    private EvaluationProprietaireRepository evaluationRepository;

    public EvaluationProprietaireViewModel(Application application) {
        super(application);
        evaluationRepository = new EvaluationProprietaireRepository(application);
    }

    public void insert(EvaluationProprietaire evaluation) {
        evaluationRepository.insert(evaluation);
    }

    public EvaluationProprietaire getEvaluationByProprietaire(int proprietaireId) {
        return evaluationRepository.getEvaluationByProprietaire(proprietaireId);
    }

    public void update(EvaluationProprietaire evaluation) {
        evaluationRepository.update(evaluation);
    }

    public List<EvaluationProprietaire> filtrerProprietairesParNote(double noteMin) {
        List<EvaluationProprietaire> tousEvaluations = evaluationRepository.getAllEvaluations();
        List<EvaluationProprietaire> evaluationsFiltrees = new ArrayList<>();

        for (EvaluationProprietaire evaluation : tousEvaluations) {
            if (evaluation.getNoteMoyenne() >= noteMin) {  // getNoteMoyenne() doit retourner une moyenne
                evaluationsFiltrees.add(evaluation);
            }
        }
        return evaluationsFiltrees;
    }

}
