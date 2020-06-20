package m.covidstat;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.List;

public class CountryCasesDetailFragment extends Fragment {

    private DetailView newCases;
    private DetailView activeCases;
    private DetailView criticalCases;
    private DetailView recoveredCases;
    private DetailView totalCases;
    private DetailView newDeaths;
    private AppCompatTextView countryName;
    private DetailView totalDeaths;
    private ImageView backDrop;
    private AppCompatImageView backBtn;
    private List<StatisticsModel.Response> countryList;
    private int position;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_country_details, container, false);
        newCases = view.findViewById(R.id.new_cases);
        activeCases = view.findViewById(R.id.active_cases);
        criticalCases = view.findViewById(R.id.critical_cases);
        recoveredCases = view.findViewById(R.id.recovered_cases);
        totalCases = view.findViewById(R.id.total_cases);
        newDeaths = view.findViewById(R.id.new_deaths);
        countryName = view.findViewById(R.id.country_name);
        totalDeaths = view.findViewById(R.id.total_deaths);
        backDrop = view.findViewById(R.id.backdrop);
        backBtn = view.findViewById(R.id.close);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle==null) return;
        if (bundle.getSerializable("list") == null) return;
        position = bundle.getInt("position");
        int drawable = bundle.getInt("imgIndex");
        countryList = (List<StatisticsModel.Response>) bundle.getSerializable("list");
        TypedArray tArray = getResources().obtainTypedArray(
                R.array.country_images);
        Glide.with(getContext())
                .load(tArray.getResourceId(drawable, 1))
                .asBitmap()
                .into(backDrop);

        newCases.setLeftText(getContext().getResources().getString(R.string.detail_new_cases));
        newCases.setValue(countryList.get(position).getCases().getNew());
        totalDeaths.showHalfDivider(true);

        activeCases.setLeftText(getContext().getResources().getString(R.string.detail_active));
        activeCases.setValue(String.valueOf(countryList.get(position).getCases().getActive()));
        totalDeaths.showHalfDivider(true);

        criticalCases.setLeftText(getContext().getResources().getString(R.string.detail_critical));
        criticalCases.setValue(String.valueOf(countryList.get(position).getCases().getCritical()));
        totalDeaths.showHalfDivider(true);

        recoveredCases.setLeftText(getContext().getResources().getString(R.string.detail_recovered));
        recoveredCases.setValue(String.valueOf(countryList.get(position).getCases().getRecovered()));
        totalDeaths.showHalfDivider(true);

        totalCases.setLeftText(getContext().getResources().getString(R.string.detail_total));
        totalCases.setValue(String.valueOf(countryList.get(position).getCases().getTotal()));
        totalDeaths.showHalfDivider(true);

        newDeaths.setLeftText(getContext().getResources().getString(R.string.detail_new_deaths));
        newDeaths.setValue(countryList.get(position).getDeaths().getNew());
        totalDeaths.showHalfDivider(true);


        totalDeaths.setLeftText(getContext().getResources().getString(R.string.detail_total_deaths));
        totalDeaths.setValue(String.valueOf(countryList.get(position).getDeaths().getTotal()));
        totalDeaths.showHalfDivider(true);

        countryName.setText(countryList.get(position).getCountry());
        backBtn.setOnClickListener(v -> getActivity().finish());
    }


}
