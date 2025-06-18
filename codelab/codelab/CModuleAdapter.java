package com.example.codelab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CModuleAdapter extends RecyclerView.Adapter<CModuleAdapter.ModuleViewHolder> {

    private List<CModule> moduleList;
    private int expandedPosition = -1; // Keeps track of expanded item
    private Context context; // Context for launching activity

    public CModuleAdapter(Context context, List<CModule> moduleList) {
        this.context = context;
        this.moduleList = moduleList;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_c_module, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CModule module = moduleList.get(position);
        holder.moduleTitle.setText(module.getTitle());
        holder.moduleDescription.setText(module.getDescription());

        boolean isExpanded = position == expandedPosition;
        holder.moduleDescription.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.playButton.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.cardView.setActivated(isExpanded);

        holder.cardView.setOnClickListener(v -> {
            if (expandedPosition == position) {
                expandedPosition = -1; // Collapse if it's already expanded
            } else {
                expandedPosition = position;
            }
            notifyDataSetChanged();
        });

        holder.playButton.setOnClickListener(v -> {
            if (position == 0) { // Only Module 1 opens the new page
                Intent intent = new Intent(context, CContentM1.class); 
                intent.putExtra("MODULE_TITLE", module.getTitle());
                context.startActivity(intent);
            } else {
                Toast.makeText(v.getContext(), "Module Locked: Complete previous modules first!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    public static class ModuleViewHolder extends RecyclerView.ViewHolder {
        TextView moduleTitle, moduleDescription;
        ImageButton playButton;
        CardView cardView;

        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleTitle = itemView.findViewById(R.id.moduleTitle);
            moduleDescription = itemView.findViewById(R.id.moduleDescription);
            playButton = itemView.findViewById(R.id.playButton);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
