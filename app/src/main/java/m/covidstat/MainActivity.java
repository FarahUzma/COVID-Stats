package m.covidstat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements CountryAdapter.onItemClick {



    private RecyclerView recyclerView;
    private CountryAdapter countryAdapter;
    private List<StatisticsModel.Response> countryWiseCasesList = new ArrayList<>();
    private List<StatisticsModel.Response> tempCountryWiseCasesList  = new ArrayList<>();
    private TextInputLayout searchText;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ImageView bannerImage;
    private AppCompatImageView backButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);

        initCollapsingToolbar();
        searchText = findViewById(R.id.search_text);
        recyclerView =  findViewById(R.id.recyle_view);
        floatingActionButton = findViewById(R.id.fab_search);
        bannerImage = findViewById(R.id.backdrop);
        toolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.appbar);
        backButton = findViewById(R.id.close);
        floatingActionButton.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);
        searchText.setHint("Search");



        backButton.setOnClickListener(v -> finish());
        toolbar.setNavigationOnClickListener(v -> {
            hideKeyboard(v);
            appBarLayout.setExpanded(true);
            searchText.getEditText().setText("");
        });
        floatingActionButton.setOnClickListener(v -> {
            if ((Integer) floatingActionButton.getTag() == R.drawable.ic_search){
                toolbar.setVisibility(View.VISIBLE);
                appBarLayout.setExpanded(false);
            }
            else {
                toolbar.setVisibility(View.GONE);
                appBarLayout.setExpanded(true);
                recyclerView.smoothScrollToPosition(0);
                searchText.getEditText().setText("");
                hideKeyboard(v);
            }

        });

        recyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> hideKeyboard(v));

        searchText.getEditText().addTextChangedListener(new TextWatcher() {
            String searchString;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchString = searchText.getEditText().getText().toString();
                if (searchString.isEmpty()) {updateAdapter(tempCountryWiseCasesList);}
                else {
                    updateAdapter(Flowable.fromIterable(tempCountryWiseCasesList)
                            .filter(SearchFilter.searchFilter(searchString))
                            .toList()
                            .blockingGet());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class );
        service.getStatistics().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(statisticsModel -> MainActivity.this.setRecyclerView(statisticsModel), throwable -> {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Unable to Fetch Data",Toast.LENGTH_LONG).show();
                });

    }

    private void updateAdapter(List<StatisticsModel.Response> countryList) {
        if (countryList==null || countryList.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            return;
        }
        countryAdapter.updateAdapter(countryList);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void setRecyclerView(StatisticsModel statisticsModel) {
        TypedArray countryImageResources = getResources().obtainTypedArray(R.array.country_images);
        statisticsModel.setImageResource(countryImageResources);
       countryWiseCasesList.addAll(statisticsModel.getResponse());
       tempCountryWiseCasesList.addAll(statisticsModel.getResponse());
       countryAdapter = new CountryAdapter(countryWiseCasesList, this, statisticsModel);
       recyclerView.setAdapter(countryAdapter);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        countryAdapter.setOnItemClickListener((position, countryWiseCoronaCasesList, imgIndex) -> {

            Intent intent = new Intent(MainActivity.this.getApplicationContext(), DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            bundle.putInt("imgIndex", imgIndex);
            bundle.putSerializable("list", (Serializable) countryWiseCoronaCasesList);
            intent.putExtra("details", bundle);
            MainActivity.this.startActivity(intent);
        });
       floatingActionButton.setVisibility(View.VISIBLE);

    }



    @Override
    public void onCountryClick(int position, List<StatisticsModel.Response> countryWiseCoronaCasesList, int imgIndex) {}


    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
         appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if ((appBarLayout.getHeight() - appBarLayout.getBottom()) == 0){
                    toolbar.setVisibility(View.GONE);
                    floatingActionButton.setImageResource(R.drawable.ic_search);
                    floatingActionButton.setTag(R.drawable.ic_search);

                }
                else {
                    toolbar.setVisibility(View.VISIBLE);
                    floatingActionButton.setImageResource(R.drawable.up);
                    floatingActionButton.setTag(R.drawable.up);
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch(Exception ignored) {
        }
    }
}
