package moi.myesiea;

import android.content.Context;
import android.content.res.TypedArray;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    String[] titles;
    TypedArray icons;
    Context context;
    RecyclerViewAdapter(String[] titles , TypedArray icons , Context context){

        this.titles = titles;
        this.icons = icons;
        this.context = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView  navTitle;
        ImageView navIcon;
        Context context;

        public ViewHolder(View drawerItem , int itemType , Context context){

            super(drawerItem);
            this.context = context;
            drawerItem.setOnClickListener(this);
            if(itemType==1){
                navTitle = (TextView) itemView.findViewById(R.id.tv_NavTitle);
                navIcon = (ImageView) itemView.findViewById(R.id.iv_NavIcon);
            }
        }


        @Override
        public void onClick(View v) {

            MainActivity mainActivity = (MainActivity)context;
            mainActivity.drawerLayout.closeDrawers();
            FragmentTransaction fragmentTransaction = mainActivity.getFragmentManager().beginTransaction();

            switch (getPosition()){
                case 1:
                    Fragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.containerView,homeFragment);
                    fragmentTransaction.commit();
                    break;
                case 2:
                    Fragment RATPFragment = new RATPFragment();
                    fragmentTransaction.replace(R.id.containerView,RATPFragment);
                    fragmentTransaction.commit();
                    break;
                case 3:
                    Fragment emergency = new EmergencyFragment();
                    fragmentTransaction.replace(R.id.containerView,emergency);
                    fragmentTransaction.commit();
                    break;
            }
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType==1){
            View itemLayout =   layoutInflater.inflate(R.layout.drawer_item_layout,null);
            return new ViewHolder(itemLayout,viewType,context);
        }
        else if (viewType==0) {
            View itemHeader = layoutInflater.inflate(R.layout.header_layout,null);
            return new ViewHolder(itemHeader,viewType,context);
        }



        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        if(position!=0){
            holder.navTitle.setText(titles[position - 1]);
            holder.navIcon.setImageResource(icons.getResourceId(position-1,-1));
        }

    }
    @Override
    public int getItemCount() {
        return titles.length+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)return 0;
        else return 1;
    }


}
