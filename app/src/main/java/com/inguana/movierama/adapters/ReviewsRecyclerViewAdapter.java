package com.inguana.movierama.adapters;

import static com.inguana.movierama.utils.Constants.EMPTY_TYPE;
import static com.inguana.movierama.utils.Constants.IMAGE_TYPE;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inguana.movierama.R;
import com.inguana.movierama.models.Review;
import com.inguana.movierama.utils.Constants;

import java.util.List;

public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ViewHolder> {

    private List<Review> reviewList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView authorNameReviewListItem;
        private TextView reviewMovieReviewListItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            authorNameReviewListItem = itemView.findViewById(R.id.authorNameReviewListItem);
            reviewMovieReviewListItem = itemView.findViewById(R.id.reviewMovieReviewListItem);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        ViewHolder result = null;

        switch (viewType) {
            case IMAGE_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_recyclerview_list_item, parent, false);
                result = new ViewHolder(view);
                break;
            }
            case EMPTY_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exhausted_result_recyclerview_list_item, parent, false);
                result = new ViewHolder(view);
                break;
            }
            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_recyclerview_list_item, parent, false);
                result = new ViewHolder(view);
            }
        }
        return result;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType == Constants.IMAGE_TYPE) {
            holder.authorNameReviewListItem.setText(reviewList.get(position).getAuthor());
            holder.reviewMovieReviewListItem.setText(reviewList.get(position).getContent());
        }

    }

    @Override
    public int getItemViewType(int position) {
        int result = EMPTY_TYPE;
        if(reviewList != null) {
            if(reviewList.size() > 0) {
                result = IMAGE_TYPE;
            }
        }

        return result;
    }

    @Override
    public int getItemCount() {
        return reviewList != null ? reviewList.size() : 0;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

}
