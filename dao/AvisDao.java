package dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;

import model.Avis;

@Dao
public interface AvisDao {
    @Insert
    void insert(Avis avis);

    @Query("SELECT * FROM avis WHERE userId = :userId")
    List<Avis> getAvisByUser(int userId);

    @Update
    void update(Avis avis);

    @Delete
    void delete(Avis avis);
    @Query("SELECT * FROM avis WHERE userId = :userId")
    LiveData<List<Avis>> getAvisLiveByUser(int userId);  // Retourne un LiveData
}
