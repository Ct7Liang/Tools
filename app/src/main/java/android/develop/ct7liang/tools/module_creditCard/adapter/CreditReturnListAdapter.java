package android.develop.ct7liang.tools.module_creditCard.adapter;

import android.content.Context;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.bean.CreditReturnBean;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ct7liang.tangyuan.recyclerview.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/9/26.
 *
 */

public class CreditReturnListAdapter extends BaseRecyclerViewAdapter {

    private Context context;
    private List<CreditReturnBean> list;

    public CreditReturnListAdapter(Context context, List<CreditReturnBean> list) {
        super(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReturnListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit_return_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CreditReturnBean creditReturnBean = list.get(position);
        if (holder instanceof ReturnListViewHolder){
            ((ReturnListViewHolder) holder).returnRecycle.setText(creditReturnBean.getReturnRecycle());
            ((ReturnListViewHolder) holder).returnDay.setText(creditReturnBean.getReturnDate());
            ((ReturnListViewHolder) holder).returnEndDay.setText(creditReturnBean.getReturnEndDay());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ReturnListViewHolder extends ContentViewHolder{
        TextView returnRecycle;
        TextView returnDay;
        TextView returnEndDay;
        ReturnListViewHolder(View itemView) {
            super(itemView);
            returnRecycle = (TextView) itemView.findViewById(R.id.return_recycle);
            returnDay = (TextView) itemView.findViewById(R.id.return_day);
            returnEndDay = (TextView) itemView.findViewById(R.id.return_end_day);
        }
        @Override
        public void onClick(View v) {

        }
        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
