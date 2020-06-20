package m.covidstat;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LOADING = 0;
    private static final int ITEM = 1;
    private List<StatisticsModel.Response> countryWiseCoronaCasesList;
    private List<StatisticsModel.Response> tempCountryWiseCasesList;
    private Context context;
    private onItemClick onItemClick;
    private StatisticsModel statisticsModel;
    private boolean isLoadingAdded = false;
    TypedArray images ;



    public CountryAdapter(List<StatisticsModel.Response> countryWiseCoronaCasesList, Context context, StatisticsModel statisticsModel){
        this.countryWiseCoronaCasesList = countryWiseCoronaCasesList;
        this.tempCountryWiseCasesList = countryWiseCoronaCasesList;
        this.context = context;
        this.statisticsModel = statisticsModel;
        images = context.getResources().obtainTypedArray(R.array.country_images);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == ITEM){
            View v = inflater.inflate(R.layout.country_item_view, parent, false);
            return new CountryViewHolder(v);
        }

        return new LoadingViewHolder(inflater.inflate(R.layout.loader_view, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM){
            CountryViewHolder countryViewHolder = (CountryViewHolder) holder;
            setItems(countryViewHolder,position);
        }

    }



    private void setItems(CountryViewHolder holder, int position){
        holder.countryTitle.setText(countryWiseCoronaCasesList.get(position).getCountry());
        holder.activeCasesCount.setText(context.getResources().getString(R.string.active, countryWiseCoronaCasesList.get(position).getCases().getActive()));
        holder.recoveredCasesCount.setText(context.getResources().getString(R.string.recovered, countryWiseCoronaCasesList.get(position).getCases().getRecovered()));
        holder.totalCases.setText(context.getResources().getString(R.string.total, countryWiseCoronaCasesList.get(position).getCases().getTotal()));
        holder.thumbnail.setOnClickListener(v -> onItemClick.onCountryClick(position, tempCountryWiseCasesList,getImagePos(position)));
        holder.itemView.setOnClickListener(v -> onItemClick.onCountryClick(position, tempCountryWiseCasesList,getImagePos(position)));
        Glide.with(context)
                .load(statisticsModel.getImageResource().getResourceId(getImagePos(position),1))
                .asBitmap()
                .into(holder.thumbnail);
    }
    private int getImagePos(int position) {
        if (position< 8) return position;
        else return position - 9 * (position/9);
    }

    @Override
    public int getItemCount() {
        return countryWiseCoronaCasesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == countryWiseCoronaCasesList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void updateAdapter(List<StatisticsModel.Response> countryCasesList){
        countryWiseCoronaCasesList.clear();
        if (countryCasesList==null || countryCasesList.isEmpty()){
            countryWiseCoronaCasesList.addAll(tempCountryWiseCasesList);
            return;}
        countryWiseCoronaCasesList.addAll(countryCasesList);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(onItemClick onItemClickListener){
        this.onItemClick = onItemClickListener;
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder{

        TextView countryTitle;
        TextView activeCasesCount;
        TextView recoveredCasesCount;
        TextView totalCases;
        ImageView thumbnail;
        CardView cardView;
        LinearLayout linearLayout;


        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryTitle = itemView.findViewById(R.id.title);
            activeCasesCount =  itemView.findViewById(R.id.active_cases_count);
            recoveredCasesCount = itemView.findViewById(R.id.recovered_cases_count);
            totalCases = itemView.findViewById(R.id.total_cases_count);
            thumbnail =  itemView.findViewById(R.id.thumbnail);
            cardView = itemView.findViewById(R.id.card_view);
            linearLayout = itemView.findViewById(R.id.linear_layout);
        }

    }

    protected class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface onItemClick{
        void onCountryClick(int position, List<StatisticsModel.Response> countryWiseCoronaCasesList, int imgIndex);
    }
}
