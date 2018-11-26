package com.tjrushby.podlite.ui.common;

import android.databinding.ViewDataBinding;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public abstract class DataBoundAdapter<T, V extends ViewDataBinding>
        extends RecyclerView.Adapter<DataBoundViewHolder<V>> {

    @Nullable
    private List<T> items;

    @NonNull
    @Override
    public DataBoundViewHolder<V> onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        V binding = createBinding(viewGroup);
        return new DataBoundViewHolder<>(binding);
    }

    protected abstract V createBinding(ViewGroup viewGroup);

    @Override
    public final void onBindViewHolder(@NonNull DataBoundViewHolder<V> holder, int position) {
        bind(holder.binding, items.get(position));
        holder.binding.executePendingBindings();
    }

    protected abstract void bind(V binding, T item);

    @MainThread
    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
