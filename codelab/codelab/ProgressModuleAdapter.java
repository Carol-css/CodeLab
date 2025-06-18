package com.example.codelab;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProgressModuleAdapter extends RecyclerView.Adapter<ProgressModuleAdapter.ModuleViewHolder> {

    private List<ProgressModuleModel> moduleList;

    public ProgressModuleAdapter(List<ProgressModuleModel> moduleList) {
        this.moduleList = moduleList;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_module, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        ProgressModuleModel module = moduleList.get(position);

        // Ensure TextView is not null before setting text
        if (holder.tvModuleName != null) {
            holder.tvModuleName.setText(module.getModuleName());
        } else {
            Log.e("ProgressModuleAdapter", "TextView (tvModuleName) is NULL at position: " + position);
        }

        // Ensure ProgressBar is not null before setting progress
        if (holder.progressBar != null) {
            holder.progressBar.setProgress(module.getProgress());
        } else {
            Log.e("ProgressModuleAdapter", "ProgressBar is NULL at position: " + position);
        }

        // Ensure ImageView is not null before setting badge
        if (holder.imgBadge != null) {
            if (module.isCompleted()) {
                holder.imgBadge.setImageResource(R.drawable.ic_badge_unlocked);
            } else {
                holder.imgBadge.setImageResource(R.drawable.ic_badge_locked);
            }
        } else {
            Log.e("ProgressModuleAdapter", "ImageView (imgBadge) is NULL at position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    public static class ModuleViewHolder extends RecyclerView.ViewHolder {
        TextView tvModuleName;
        ProgressBar progressBar;
        ImageView imgBadge;

        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvModuleName = itemView.findViewById(R.id.tvModuleName);
            progressBar = itemView.findViewById(R.id.progressBar);
            imgBadge = itemView.findViewById(R.id.imgBadge);

            // Debugging to check if IDs are correct
            if (tvModuleName == null) {
                Log.e("ModuleViewHolder", "tvModuleName is NULL! Check item_module.xml");
            }
            if (progressBar == null) {
                Log.e("ModuleViewHolder", "progressBar is NULL! Check item_module.xml");
            }
            if (imgBadge == null) {
                Log.e("ModuleViewHolder", "imgBadge is NULL! Check item_module.xml");
            }
        }
    }
}
