package com.example.codelab;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {
    private Context context;
    private List<Language> languageList;

    public LanguageAdapter(Context context, List<Language> languageList) {
        this.context = context;
        this.languageList = languageList;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.language_item, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        Language language = languageList.get(position);
        holder.languageName.setText(language.getName());
        holder.languageImage.setImageResource(language.getImageResId());

        // Set click listener only for Java card
        holder.itemView.setOnClickListener(v -> {
            if (language.getName().equalsIgnoreCase("Java")) {
                Intent intent = new Intent(context, JavaLang.class);
                intent.putExtra("languageName", language.getName());
                context.startActivity(intent);
            } else if (language.getName().equalsIgnoreCase("C")) {
                Intent intent = new Intent(context, CLang.class);
                intent.putExtra("languageName", language.getName());
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Clicked on " + language.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

    public static class LanguageViewHolder extends RecyclerView.ViewHolder {
        TextView languageName;
        ImageView languageImage;

        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            languageName = itemView.findViewById(R.id.languageName);
            languageImage = itemView.findViewById(R.id.languageImage);
        }
    }
}
