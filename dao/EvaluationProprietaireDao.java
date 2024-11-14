package dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import model.EvaluationProprietaire;

@Dao
public interface EvaluationProprietaireDao {
    @Insert
    void insert(EvaluationProprietaire evaluation);

    @Query("SELECT * FROM evaluation_proprietaire WHERE proprietaireId = :proprietaireId")
    EvaluationProprietaire getEvaluationByProprietaire(int proprietaireId);

    @Query("SELECT * FROM evaluation_proprietaire")
    List<EvaluationProprietaire> getAllEvaluations(); // Cette méthode est cruciale
    @Update
    void update(EvaluationProprietaire evaluation);
}
