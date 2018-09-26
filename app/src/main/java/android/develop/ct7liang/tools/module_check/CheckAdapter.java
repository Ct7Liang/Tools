package android.develop.ct7liang.tools.module_check;

import android.content.Context;
import android.develop.ct7liang.tools.R;
import android.develop.ct7liang.tools.bean.Check;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ct7liang.tangyuan.recyclerview.BaseRecyclerViewAdapter;
import java.util.List;

/**
 * Created by Administrator on 2018-09-05.
 *
 */
public class CheckAdapter extends BaseRecyclerViewAdapter {

    private Context context;
    private List<Check> checks;

    public CheckAdapter(Context context, List<Check> checks) {
        super(context);
        this.context = context;
        this.checks = checks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 1:
                return new ViewHolderIn(View.inflate(context, R.layout.item_recycler_view_check_in, null));
            default:
                return new ViewHolderOut(View.inflate(context, R.layout.item_recycler_view_check_out, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Check check = checks.get(position);
        if (holder instanceof ViewHolderIn){
            ((ViewHolderIn) holder).tv_desc.setText(check.getDesc());
            ((ViewHolderIn) holder).tv_cash.setText(String.valueOf("+ " + check.getCash()));
            ((ViewHolderIn) holder).tv_time.setText(check.getTime());
        }
        if (holder instanceof ViewHolderOut){
            ((ViewHolderOut) holder).tv_desc.setText(check.getDesc());
            ((ViewHolderOut) holder).tv_cash.setText(String.valueOf("- " + check.getCash()));
            ((ViewHolderOut) holder).tv_time.setText(check.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return checks.size();
    }

    @Override
    public int getItemViewType(int position) {
        return checks.get(position).getType();
    }

    private class ViewHolderIn extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView tv_desc;
        public TextView tv_time;
        public TextView tv_cash;
        public ViewHolderIn(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_cash = (TextView) itemView.findViewById(R.id.tv_cash);
        }
        @Override
        public boolean onLongClick(View view) {
            if (mLongListener!=null){
                mLongListener.onLongClick(view, getAdapterPosition());
            }
            return false;
        }
    }

    private class ViewHolderOut extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView tv_desc;
        public TextView tv_time;
        public TextView tv_cash;
        public ViewHolderOut(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_cash = (TextView) itemView.findViewById(R.id.tv_cash);
        }
        @Override
        public boolean onLongClick(View view) {
            if (mLongListener!=null){
                mLongListener.onLongClick(view, getAdapterPosition());
            }
            return false;
        }
    }
}