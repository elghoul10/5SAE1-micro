package tn.esen.userinterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class usersAdapter extends RecyclerView.Adapter<usersAdapter.UserViewHolder> {

    private ArrayList<String> user_id, username, email;
    private Context context;
    private Activity activity;

    public usersAdapter(Activity activity, Context context, ArrayList<String> user_id, ArrayList<String> username, ArrayList<String> email) {
        this.activity = activity;
        this.context = context;
        this.user_id = user_id;
        this.username = username;
        this.email = email;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_row, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        holder.userId.setText(user_id.get(position));
        holder.userName.setText(username.get(position));
        holder.userEmail.setText(email.get(position));

        // Update button listener
        holder.updateButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateUser.class);
            intent.putExtra("id", user_id.get(position));
            intent.putExtra("username", username.get(position));
            intent.putExtra("email", email.get(position));
            context.startActivity(intent);
        });

        // Delete button listener
        holder.deleteButton.setOnClickListener(view -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete " + user_id.get(position) + " ?")
                    .setMessage("Are you sure you want to delete " + user_id.get(position) + " ?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        Database myDB = new Database(context);
                        myDB.deleteUser(Integer.parseInt(user_id.get(position)));
                        user_id.remove(position);
                        username.remove(position);
                        email.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, user_id.size());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return user_id.size(); // Return the size of the list
    }

    // ViewHolder class to hold references to the views in the item layout
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userId, userName, userEmail;
        Button updateButton, deleteButton;

        public UserViewHolder(View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.user_id);
            userName = itemView.findViewById(R.id.username);
            userEmail = itemView.findViewById(R.id.email);
            updateButton = itemView.findViewById(R.id.update_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
