package android.develop.ct7liang.tools.module_creditCard.adapter;

import android.content.Context;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.bean.CreditCardBean;
import android.develop.ct7liang.tools.module_creditCard.ziyuan.CreditInfo;
import android.graphics.Color;
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
 *
 */
public class CreditListAdapter extends BaseRecyclerViewAdapter {

    private final CreditInfo instance;
    private List<CreditCardBean> list;
    private final int[] tags;

    public CreditListAdapter(Context context, List<CreditCardBean> list) {
        super(context);
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
        CreditCardBean creditCardBean = list.get(position);
        if (holder instanceof CreditListViewHolder){
            int i = Arrays.binarySearch(tags, creditCardBean.getTag());
            ((CreditListViewHolder) holder).img.setImageResource(instance.bank_icon[i]);
            ((CreditListViewHolder) holder).tv.setText(instance.bank_name[i]);
            if (position != onClickPosition){
                ((CreditListViewHolder) holder).parent.setBackgroundColor(Color.parseColor("#F7F7F7"));
            }else{
                ((CreditListViewHolder) holder).parent.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private int onClickPosition = 0;
    /**
     * 设置被点击的位置的颜色
     * @param i
     */
    public void setOnClickPosition(int i) {
        onClickPosition = i;
    }

    private class CreditListViewHolder extends ContentViewHolder{
        ImageView img;
        TextView tv;
        View parent;
        CreditListViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.credit_img);
            tv = (TextView) itemView.findViewById(R.id.credit_text);
            parent = itemView.findViewById(R.id.parent);
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
