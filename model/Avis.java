package model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "avis")
public class Avis {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private String commentaire;
    private int note; // note sur 5 étoiles
    private long date;
    private String categorieRetour;

    // Constructeurs, getters et setters
    public Avis(int userId, String commentaire, int note, long date, String categorieRetour) {
        this.userId = userId;
        this.commentaire = commentaire;
        this.note = note;
        this.date = date;
        this.categorieRetour = categorieRetour;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public String getCommentaire() { return commentaire; }
    public int getNote() { return note; }
    public long getDate() { return date; }

    public String getCategorieRetour() {
        return categorieRetour;
    }

    public void setCategorieRetour(String categorieRetour) {
        this.categorieRetour = categorieRetour;
    }
}
