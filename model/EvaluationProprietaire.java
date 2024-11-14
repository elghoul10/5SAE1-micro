package model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "evaluation_proprietaire")
public class EvaluationProprietaire {
    @PrimaryKey(autoGenerate = true)
    private int id;


    private int proprietaireId;
    private int noteMoyenne;
    private int nombreEvaluations;

    // Constructeurs, getters et setters
    public EvaluationProprietaire(int proprietaireId, int noteMoyenne, int nombreEvaluations) {
        this.proprietaireId = proprietaireId;
        this.noteMoyenne = noteMoyenne;
        this.nombreEvaluations = nombreEvaluations;
    }

    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }
    public int getProprietaireId() { return proprietaireId; }
    public int getNoteMoyenne() { return noteMoyenne; }
    public int getNombreEvaluations() { return nombreEvaluations; }

    @Override
    public boolean equals(Object obj) {
        // Vérifier si les deux objets sont identiques
        if (this == obj) return true;
        // Vérifier si l'objet comparé est de type EvaluationProprietaire
        if (obj == null || getClass() != obj.getClass()) return false;

        EvaluationProprietaire that = (EvaluationProprietaire) obj;

        // Comparer les attributs pertinents
        return proprietaireId == that.proprietaireId &&
                noteMoyenne == that.noteMoyenne &&
                nombreEvaluations == that.nombreEvaluations;
    }

    @Override
    public int hashCode() {
        // Calculer un hashCode basé sur les attributs pertinents
        int result = Integer.hashCode(proprietaireId);
        result = 31 * result + Integer.hashCode(noteMoyenne);
        result = 31 * result + Integer.hashCode(nombreEvaluations);
        return result;
    }
}
