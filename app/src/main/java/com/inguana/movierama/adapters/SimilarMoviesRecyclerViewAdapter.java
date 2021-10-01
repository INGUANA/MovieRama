package com.inguana.movierama.adapters;

import static com.inguana.movierama.utils.Constants.EMPTY_TYPE;
import static com.inguana.movierama.utils.Constants.IMAGE_TYPE;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.inguana.movierama.R;

import java.util.List;

public class SimilarMoviesRecyclerViewAdapter extends RecyclerView.Adapter<SimilarMoviesRecyclerViewAdapter.ViewHolder> {

    private List<String> imageUrlList;
    private String imageBaseUrl;

    @NonNull
    @Override
    public SimilarMoviesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        SimilarMoviesRecyclerViewAdapter.ViewHolder result = null;

        switch (viewType) {
            case IMAGE_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_carousel_list_item, parent, false);
                result = new ViewHolder(view);
                break;
            }
            case EMPTY_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar_recyclerview_layout, parent, false);
                result = new ViewHolder(view);
                break;
            }
            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_carousel_list_item, parent, false);
                result = new ViewHolder(view);
            }
        }
        return result;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView movieImageCarouselListItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImageCarouselListItem = itemView.findViewById(R.id.movieImageCarouselListItem);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarMoviesRecyclerViewAdapter.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType == IMAGE_TYPE) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(imageBaseUrl + imageUrlList.get(position))
                    .into(((ViewHolder)holder).movieImageCarouselListItem);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int result = EMPTY_TYPE;
        if(imageUrlList != null) {
            if (imageUrlList.size() > 0) {
                result = IMAGE_TYPE;
            }
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return imageUrlList != null ? imageUrlList.size() : 0;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
        notifyDataSetChanged();
    }

    public void setImageBaseUrl(String imageBaseUrl) {
        this.imageBaseUrl = imageBaseUrl;
    }
}
