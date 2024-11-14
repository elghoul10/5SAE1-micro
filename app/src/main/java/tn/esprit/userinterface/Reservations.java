package tn.esprit.userinterface;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Reservations extends AppCompatActivity {
    ArrayList<String> DateRes,Vehicule,Price,tel,ClientId,res_id;
    RecyclerView Reservations;
    Database bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservations);


        Database bd = new Database(this);


        DateRes = new ArrayList<>();
        Vehicule = new ArrayList<>();
        Price = new ArrayList<>();
        tel = new ArrayList<>();
        ClientId = new ArrayList<>();
        res_id = new ArrayList<>();

        Reservations = findViewById(R.id.reservationslist);

        // Set up RecyclerView
        ReservationAdapter adapter = new ReservationAdapter(Reservations.this,this,res_id,ClientId, tel, Price,Vehicule,DateRes); // Pass the data to the adapter
        Reservations.setLayoutManager(new LinearLayoutManager(this)); // Use LinearLayoutManager for vertical list
        Reservations.setAdapter(adapter);

    }
    void DisplayDataInRecycleView(){


        Cursor cursor = bd.getAllUsers();


        if(cursor.getCount() == 0){

            Toast.makeText(this,"No reservation found !",Toast.LENGTH_SHORT).show();
        }
        else {


            while(cursor.moveToNext()){
                res_id.add(cursor.getString(0));
                ClientId.add(cursor.getString(1));
                tel.add(cursor.getString(2));
                Price.add(cursor.getString(3));
                Vehicule.add(cursor.getString(4));
                DateRes.add(cursor.getString(5));

            }

        }
    }
}