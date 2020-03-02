package ru.indychkov.tollroadsapp.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.indychkov.tollroadsapp.R;
import ru.indychkov.tollroadsapp.data.model.TollRoadPrice;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<TollRoadPrice> priceList;
    boolean isNight;
    boolean hasAvtodor;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String price = "";
        if (hasAvtodor) {
            if (isNight) {
                price = priceList.get(position).getBase_avtodor_night_price() + " \u20BD";
            } else {
                price = priceList.get(position).getBase_avtodor_price() + " \u20BD";
            }
        } else {
            if (isNight) {
                price = priceList.get(position).getBase_night_price() + " \u20BD";
            } else {
                price = priceList.get(position).getBase_price() + " \u20BD";
            }
        }
        holder.textView.setText(String.format("%s\n%d категория\n%s", priceList.get(position).getPart_name(), priceList.get(position).getCategory(), price));

    }

    @Override
    public int getItemCount() {
        if (priceList == null) {
            return 0;
        }
        return priceList.size();
    }

    public int getSum() {
        int sum = 0;
        for (TollRoadPrice element :
                this.priceList) {
            if (hasAvtodor) {
                if (isNight) {
                    sum += element.getBase_avtodor_night_price();
                } else {
                    sum += element.getBase_avtodor_price();
                }
            } else {
                if (isNight) {
                    sum += element.getBase_night_price();
                } else {
                    sum += element.getBase_price();
                }
            }
        }
        return sum;
    }

    public void setTasks(List<TollRoadPrice> personList, boolean isNight, boolean hasAvtodor) {
        priceList = personList;
        this.isNight = isNight;
        this.hasAvtodor = hasAvtodor;
        notifyDataSetChanged();
    }


    public List<TollRoadPrice> getTasks() {

        return priceList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.ter);
        }
    }
}
