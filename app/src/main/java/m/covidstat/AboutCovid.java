package m.covidstat;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AboutCovid extends AppCompatActivity implements OnChartValueSelectedListener {

    protected RectF mOnValueSelectedRectF;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ImageView bannerImage;
    private AppCompatImageView backButton;
    ArrayList<Entry> x;
    ArrayList<String> y;
    private LineChart mChart;
    public String TAG = "AboutCovid";
    private IndiaModel modelData;
    ArrayList xaxis;
    ArrayList yaxis;
    TextView state;
    TextView active;
    TextView deaths;
    TextView recovered;
    BarChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.about_covid);
        state= (TextView)findViewById(R.id.state);
        active= (TextView)findViewById(R.id.active);
        deaths= (TextView)findViewById(R.id.deaths);
        recovered= (TextView)findViewById(R.id.recovered);
        mOnValueSelectedRectF = new RectF();

        RetrofitClientInstance2.GetIndiaDataService service = RetrofitClientInstance2.getRetrofitInstance2().create(RetrofitClientInstance2.GetIndiaDataService.class );
        service.getStatistics().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(indiaModel ->
                {
                    AboutCovid.this.getXAxisValues(indiaModel);
                    this.chart = (BarChart) findViewById(R.id.chart);
                    chart.setOnChartValueSelectedListener(this);
                    BarData data = new BarData(AboutCovid.this.getXAxisValues(indiaModel), AboutCovid.this.getDataSet(indiaModel));
                    chart.setData(data);
                    chart.setDescription("My Chart");
                    chart.animateXY(2000, 2000);
                    chart.invalidate();
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                },
                throwable -> {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Unable to Fetch Data",Toast.LENGTH_LONG).show();
                });







    }
    private ArrayList getDataSet(IndiaModel statisticsModel) {

        this.modelData=statisticsModel;
        ArrayList dataSets = null;
        ArrayList valueSet1 = new ArrayList();
        for(int i=1;i<statisticsModel.getDeaths().size();i++)
         {

        BarEntry v1e1 = new BarEntry(Integer.valueOf(statisticsModel.getDeaths().get(i).getActive()),i);
        valueSet1.add(v1e1);
         }



        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));

        dataSets = new ArrayList();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    private ArrayList getXAxisValues(IndiaModel statisticsModel) {

        final ArrayList<String> xAxis = new ArrayList<>();
        for(int i=1;i<statisticsModel.getDeaths().size();i++)
        {
            xAxis.add(statisticsModel.getDeaths().get(i).getState());

        }

        return xAxis;
    }


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        this.state.setText("State: "+this.modelData.getDeaths().get(e.getXIndex()).getState());
        this.active.setText("Active: "+this.modelData.getDeaths().get(e.getXIndex()).getActive());
        this.deaths.setText("Deaths: "+this.modelData.getDeaths().get(e.getXIndex()).getDeaths());
        this.recovered.setText("Recovered: "+this.modelData.getDeaths().get(e.getXIndex()).getRecovered());

    }

    @Override
    public void onNothingSelected() {

    }
}


