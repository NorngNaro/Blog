package com.naro.blog.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.naro.blog.R;
import com.naro.blog.model.Article;

import java.util.List;

public class ListMyBlogAdapter extends RecyclerView.Adapter<ListMyBlogAdapter.ArticleItemViewHolder> {

    private Context context;
    private List<Article> dataSet;
    private LayoutInflater inflater;
    private ListMyBlogAdapter.OnActionClick onActionClick;
    private ArticleItemViewHolder holder;
    private int position;

    public ListMyBlogAdapter(Context context , List<Article> dataSet , ListMyBlogAdapter.OnActionClick onActionClick) {
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
    public ListMyBlogAdapter.ArticleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_my_blog, parent, false);
        return new ArticleItemViewHolder(view);
    }

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
        private CardView btn_edit;
        private CardView btn_delete;


        public ArticleItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.txt_postdate);
            textDescription = itemView.findViewById(R.id.image_show);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_edit = itemView.findViewById(R.id.btn_edit);


            itemView.setOnClickListener(v -> onActionClick.onItemClick(getAdapterPosition()));
            btn_delete.setOnClickListener(v -> onActionClick.onDelete(getAdapterPosition()));
            btn_edit.setOnClickListener(v -> onActionClick.onEdit(getAdapterPosition()));


        }

    }

    public interface OnActionClick {
        void onItemClick(int position);
        void onEdit(int position);
        void onDelete(int position);
    }

    public String formatTo(String dateData) {
        String day , month , year ;
        day = dateData.substring(4,6);
        month = "." + dateData.substring(6,8);
        year = "." + dateData.substring(0,4);
        return day + month + year;
    }

}
