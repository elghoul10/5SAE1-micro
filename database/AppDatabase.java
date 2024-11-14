package database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dao.AvisDao;
import dao.EvaluationProprietaireDao; // Ajouter l'import pour EvaluationProprietaireDao
import model.Avis;
import model.EvaluationProprietaire;

@Database(entities = {Avis.class, EvaluationProprietaire.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // DAO pour Avis
    public abstract AvisDao avisDao();

    // DAO pour EvaluationProprietaire
    public abstract EvaluationProprietaireDao evaluationProprietaireDao();

    private static volatile AppDatabase INSTANCE;

    // Méthode pour obtenir une instance unique de la base de données
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "avis_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
