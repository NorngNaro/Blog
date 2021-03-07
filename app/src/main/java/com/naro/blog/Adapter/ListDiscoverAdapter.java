package com.naro.blog.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.naro.blog.R;
import com.naro.blog.model.Article;

import java.util.List;

public class ListDiscoverAdapter extends RecyclerView.Adapter<ListDiscoverAdapter.ArticleItemViewHolder> {

    private Context context;
    private List<Article> dataSet;
    private LayoutInflater inflater;
    private OnActionClick onActionClick;

    public ListDiscoverAdapter(Context context , List<Article> dataSet , OnActionClick onActionClick) {
        this.context = context;
        this.dataSet = dataSet;
        this.inflater = LayoutInflater.from(context);
        this.onActionClick = onActionClick;
    }

    public void setDataSet(List<Article> dataSet) {
        this.dataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public List<Article> getDataSet() {
        return dataSet;
    }

    @NonNull
    @Override
    public ArticleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_discover, parent, false);
        return new ArticleItemViewHolder(view);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ArticleItemViewHolder holder, int position) {
        // Set title
        holder.textTitle.setText(dataSet.get(position).getTitle());
        // Set date
        holder.date.setText(formatTo(dataSet.get(position).getCreatedDate()));
        // Set Image
        Glide.with(context)
                .load(dataSet.get(position).getImageUrl())
                .into(holder.textDescription);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class ArticleItemViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView textTitle;
        private AppCompatTextView date;
        private AppCompatImageView textDescription;

        public ArticleItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.txt_postdate);
            textDescription = itemView.findViewById(R.id.image_show);

            itemView.setOnClickListener(v -> onActionClick.onItemClick(getAdapterPosition()));

        }

    }

    public interface OnActionClick {
        void onItemClick(int position);

    }

    public String formatTo(String dateData) {
        String day , month , year ;
        day = dateData.substring(4,6);
        month = "." + dateData.substring(6,8);
        year = "." + dateData.substring(0,4);
        return day + month + year;
    }



}
