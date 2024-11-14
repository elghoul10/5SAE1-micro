package tn.esprit.userinterface;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.MyviewHolder> {

    private ArrayList<String> res_id, ClientId, tel, Price, Vehicule, DateRes;
    private Context context;
    private Activity activity;

    // Constructor should initialize all the arrays passed
    public ReservationAdapter(Activity activity, Context context, ArrayList<String> res_id, ArrayList<String> ClientId, ArrayList<String> tel, ArrayList<String> Price, ArrayList<String> Vehicule, ArrayList<String> DateRes) {
        this.activity = activity;
        this.context = context;
        this.res_id = res_id;
        this.ClientId = ClientId;
        this.tel = tel;
        this.Price = Price;
        this.Vehicule = Vehicule;
        this.DateRes = DateRes;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflating the layout for each item
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_row, parent, false);
        return new MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        // Bind data to the views
        holder.res_id.setText(res_id.get(position));
        holder.ClientId.setText(ClientId.get(position));
        holder.tel.setText(tel.get(position));
        holder.Price.setText(Price.get(position));
        holder.Vehicule.setText(Vehicule.get(position));
        holder.DateRes.setText(DateRes.get(position));
    }

    @Override
    public int getItemCount() {
        return res_id.size(); // Return the size of the list
    }

    // ViewHolder class to hold references to the views in the item layout
    public static class MyviewHolder extends RecyclerView.ViewHolder {
        TextView res_id, ClientId, tel, Price, Vehicule, DateRes;

        public MyviewHolder(View itemView) {
            super(itemView);
            res_id = itemView.findViewById(R.id.res_id);
            ClientId = itemView.findViewById(R.id.clientId);
            tel = itemView.findViewById(R.id.tel);
            Price = itemView.findViewById(R.id.price);
            Vehicule = itemView.findViewById(R.id.vehicule);
            DateRes = itemView.findViewById(R.id.date);
        }
    }
}
