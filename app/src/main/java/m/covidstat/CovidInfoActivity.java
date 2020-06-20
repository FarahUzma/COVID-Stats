package m.covidstat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class CovidInfoActivity extends AppCompatActivity {

    private WebView webView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_info);

        webView = findViewById(R.id.webView);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://medicalaid.org/coronavirus-what-you-need-to-know/?gclid=CjwKCAjwv4_1BRAhEiwAtMDLsmeSAznXriv6kLdxY-i4VPMPWLz8qB1Fkeu6MU7jTLYyiv9Av8sOxRoCAkgQAvD_BwE");

    }
}
