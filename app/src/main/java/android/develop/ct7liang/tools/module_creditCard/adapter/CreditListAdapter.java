package android.develop.ct7liang.tools.module_creditCard.adapter;

import android.content.Context;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.bean.CreditCardBean;
import android.develop.ct7liang.tools.module_creditCard.ziyuan.CreditInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ct7liang.tangyuan.recyclerview.BaseRecyclerViewAdapter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018-09-18.
 *  6225 7686 2681 4048   22 21 9  22 9
 *  6222 5306 2189 2269   19 18 12 23 3
 */
public class CreditListAdapter extends BaseRecyclerViewAdapter {

    private final CreditInfo instance;
    private Context context;
    private List<CreditCardBean> list;
    private final int[] tags;

    public CreditListAdapter(Context context, List<CreditCardBean> list) {
        super(context);
        this.context = context;
        this.list = list;
        instance = CreditInfo.getInstance();
        tags = instance.tag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CreditListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit_card, parent, false));
//        return new CreditListViewHolder(View.inflate(context, R.layout.item_credit_card, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CreditListViewHolder){
            int tag = list.get(position).getTag();
            int i = Arrays.binarySearch(tags, tag);
            ((CreditListViewHolder) holder).img.setImageResource(instance.bank_icon[i]);
            ((CreditListViewHolder) holder).tv.setText(instance.bank_name[i]);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class CreditListViewHolder extends ContentViewHolder{
        public ImageView img;
        public TextView tv;
        public CreditListViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.credit_img);
            tv = itemView.findViewById(R.id.credit_text);
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
