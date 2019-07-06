package com.kaleab.tenaadam.Walkthrough;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaleab.tenaadam.Passcode.PasscodeActivity;
import com.kaleab.tenaadam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class WalkThrough extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private int[] layouts;

    final Context context = this;

    private TextView[] dots;
    private LinearLayout dotsLayout;
    WalkthroughManager walkthroughManager;

    Button next, skip;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        walkthroughManager = new WalkthroughManager(this);
        if(!walkthroughManager.Check()){
            Intent i =  new Intent(WalkThrough.this, PasscodeActivity.class);
            startActivity(i);
            finish();

        }else{
            walkthroughManager.isFirst(false);
            Toast.makeText(this, "RUN FOR THE FIRST TIME", Toast.LENGTH_LONG).show();
            fillDailyTip();
        }
        setContentView(R.layout.activity_walk_through);


        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        skip = (Button) findViewById(R.id.btn_skip);
        next = (Button) findViewById(R.id.btn_next);
        layouts = new int[]{R.layout.activity_screen_2,
                            R.layout.activity_screen_4,
                            R.layout.activity_screen_5,};

        //addBottomDots(0);
        //changeStatusBarColor();
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(WalkThrough.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int current = getItem(+1);
                if(current < layouts.length){
                    viewPager.setCurrentItem(current);
                }else{
                    Intent i =  new Intent(WalkThrough.this, RegisterActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private void addBottomDots(int position){
        dots = new TextView[layouts.length];

        int[] colorActive = getResources().getIntArray(R.array.dot_active);
        int[] colorInactive = getResources().getIntArray(R.array.dot_inactive);

        dotsLayout.removeAllViews();

        for(int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[position]);
            dotsLayout.addView(dots[i]);
        }
        if(dots.length > 0){
            dots[position].setTextColor(colorActive[position]);
        }
    }

    private int getItem(int i){
        return viewPager.getCurrentItem() + 1;
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if(position == dots.length - 1){
                next.setText("START");
                skip.setVisibility(View.GONE);
            }else{
                next.setText("NEXT");
                skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class ViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position], container, false);
            container.addView(v);

            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View) object;
            container.removeView(v);
        }
    }

    public void fillDailyTip() {
        try
        {
            String jsonLocation = AssetJSONFile("dailyTip.text");
            JSONObject jsonobject = new JSONObject(jsonLocation);
            JSONArray jarray = (JSONArray) jsonobject.getJSONArray("dailyTip");
            for(int i=0;i<jarray.length();i++)
            {
                JSONObject jb =(JSONObject) jarray.get(i);
                String day_number = jb.getString("day_number");
                Toast.makeText(this, day_number, Toast.LENGTH_LONG).show();
                String daily_tip = jb.getString("daily_tip");
                Toast.makeText(this, daily_tip, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {

            Toast.makeText(this, "IO Exeption", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (JSONException e) {

            Toast.makeText(this, "JSON exeption", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
    private static String AssetJSONFile (String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuilder text = new StringBuilder();
        String line = br.readLine();
        while(line != null){
            text.append(line);
            text.append("\n");
            br.readLine();
        }
        return text.toString();
    }
}