package android.develop.ct7liang.tools.module_check;

import android.content.Context;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.bean.MonthCheck;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ct7liang.tangyuan.recyclerview.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018-09-06.
 *
 */
public class MonthCheckAdapter extends BaseRecyclerViewAdapter {

    private Context context;
    private List<MonthCheck> monthChecks;

    public MonthCheckAdapter(Context context, List<MonthCheck> monthChecks) {
        super(context);
        this.context = context;
        this.monthChecks = monthChecks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(View.inflate(context, R.layout.item_recycler_view_check_month, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){
            ((ItemViewHolder) holder).tv_time.setText(String.valueOf(monthChecks.get(position).year+"年"+monthChecks.get(position).month+"月"));
            ((ItemViewHolder) holder).tv_cash.setText(String.valueOf(monthChecks.get(position).cash));
        }
    }

    @Override
    public int getItemCount() {
        return monthChecks.size();
    }

    private class ItemViewHolder extends ContentViewHolder{
        private TextView tv_time;
        private TextView tv_cash;
        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_cash = (TextView) itemView.findViewById(R.id.tv_cash);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener!=null){
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}