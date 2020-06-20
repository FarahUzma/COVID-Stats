package m.covidstat;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;


public class DetailView extends RelativeLayout {

    private Context context;
    private AppCompatTextView leftText;
    private AppCompatTextView value;
    private View halfDivider;
    private View fullDivider;

    public DetailView(Context context) {
        super(context);
        this.context = context;
        init();
    }



    public DetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public DetailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }


    private void init() {
        ViewGroup viewGroup = (ViewGroup) inflate(context , R.layout.sample_detail_view, null);
        addView(viewGroup);
        leftText = viewGroup.findViewById(R.id.left_text);
        value = viewGroup.findViewById(R.id.value);
        halfDivider = viewGroup.findViewById(R.id.half_divider);
        fullDivider = viewGroup.findViewById(R.id.full_divider);

    }


    public void setLeftText(String text){
        leftText.setText(text);
    }

    public void setValue(String value){
        startCountAnimation(this.value, Integer.parseInt(value.replaceAll("[^0-9]", "")));
        this.value.setText(value);
    }

    public void showHalfDivider(boolean show){
        if (show) {
            halfDivider.setVisibility(VISIBLE);
            fullDivider.setVisibility(GONE);
        }
        else {
            halfDivider.setVisibility(GONE);
            fullDivider.setVisibility(VISIBLE);
        }
    }

    private void startCountAnimation(TextView textView, int till) {
        ValueAnimator animator = ValueAnimator.ofInt(0, till);
        animator.setDuration(1500);
        animator.addUpdateListener(animation -> textView.setText(animation.getAnimatedValue().toString()));
        animator.start();
    }




}
