package com.demo.encapsulationokhttp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.encapsulationokhttp.R;
import com.demo.encapsulationokhttp.bean.Joke;

import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {

    private Context mContext;
    private List<Joke.ListBean> list;

    public JokeAdapter(Context mContext, List<Joke.ListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public JokeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_joke,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull JokeAdapter.ViewHolder holder, int position) {
        holder.jokeContent.setText(list.get(position).getContent());
        holder.jokeTime.setText(list.get(position).getUpdateTime());
    }

    @Override
    public int getItemCount() {
        if (list==null) {
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView jokeTime;
        private final TextView jokeContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jokeContent = itemView.findViewById(R.id.joke_content);
            jokeTime = itemView.findViewById(R.id.joke_time);
        }
    }
}
