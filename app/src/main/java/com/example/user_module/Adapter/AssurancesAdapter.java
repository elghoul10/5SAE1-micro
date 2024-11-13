package com.example.user_module.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.user_module.R;
import com.example.user_module.activity.AddEditAssurancesActivity;
import com.example.user_module.activity.AssurancesListActivity;
import com.example.user_module.activity.ViewAssurancesActivity;
import com.example.user_module.AppDatabase;
import com.example.user_module.entity.Assurances;

import java.util.List;
import java.util.concurrent.Executors;

public class AssurancesAdapter extends RecyclerView.Adapter<AssurancesAdapter.SiteViewHolder> {

    private Context context;
    private List<Assurances> assuList;

    public AssurancesAdapter(Context context, List<Assurances> assuList) {
        this.context = context;
        this.assuList = assuList;
    }

    @NonNull
    @Override
    public SiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_site, parent, false);
        return new SiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteViewHolder holder, int position) {
        Assurances assu = assuList.get(position);
        holder.textViewName.setText(assu.getName());
        holder.textViewLocation.setText(assu.getLocation());

        // Load the image using Glide if imageUri is available
        if (assu.getImageUri() != null && !assu.getImageUri().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(assu.getImageUri())
                    .placeholder(R.drawable.ic_add) // Optional placeholder
                    .into(holder.imageViewSite);  // Load image into ImageView
        }

        // Set up the menu button with options
        holder.buttonMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.buttonMenu);
            popupMenu.inflate(R.menu.menu_site_item);

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_view) {
                    viewSite(assu);
                    return true;
                } else if (item.getItemId() == R.id.menu_edit) {
                    editSite(assu);
                    return true;
                } else if (item.getItemId() == R.id.menu_delete) {
                    deleteSite(assu);
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }


    @Override
    public int getItemCount() {
        return assuList.size();
    }

    private void viewSite(Assurances assu) {
        Intent intent = new Intent(context, ViewAssurancesActivity.class);
        intent.putExtra("siteId", assu.getId());
        context.startActivity(intent);
    }

    private void editSite(Assurances assu) {
        Intent intent = new Intent(context, AddEditAssurancesActivity.class);
        intent.putExtra("siteId", assu.getId());
        context.startActivity(intent);
    }

    private void deleteSite(Assurances assu) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(context);
            db.assurancesDao().delete(assu);

            ((AssurancesListActivity) context).runOnUiThread(() -> {
                assuList.remove(assu);
                notifyDataSetChanged();
                Toast.makeText(context, "assu deleted", Toast.LENGTH_SHORT).show();
            });
        });
    }

    public static class SiteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewLocation;
        ImageButton buttonMenu;
        ImageView imageViewSite; // New ImageView for site image

        public SiteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            buttonMenu = itemView.findViewById(R.id.buttonMenu);
            imageViewSite = itemView.findViewById(R.id.image_view_site); // Initialize ImageView
        }
    }

}
