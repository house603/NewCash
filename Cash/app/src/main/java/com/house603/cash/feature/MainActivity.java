package com.house603.cash.feature;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.house603.cash.R;
import com.house603.cash.feature.adapter.CustomDrawerAdapter;
import com.house603.cash.feature.adapter.MenuAdapter;
import com.house603.cash.feature.adapter.MenuAdapterListener;
import com.house603.cash.feature.model.CurrencyModel;
import com.house603.cash.feature.model.CustomDrawerModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    String[] MenuTitles = new String[]{"COMMENTARY", "COMMODITY", "ABOUT US"};
    SlidingPaneLayout mSlidingPanel;
    ListView mMenuList;
    ImageView appImage;
    TextView TitleText;
    private List<CurrencyModel> mMenuItemList;
    MenuAdapter mAdapter;
    private RecyclerView mRecyclerView;
    CurrencyModel mCurrencymodel;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout mCountryUp;
    private LinearLayout mCountryDown;
    int flag;
    String country;
    String iso;
    private ImageView mFlagCountryUp;
    private ImageView mFlagCountryDown;
    private TextView mCountryNameUp;
    private TextView mCountryNameDown;
    public int to;
    public int from;
    public String s;
    String isoUp, isoDown;
    private EditText mEdCountryUp;
    private EditText mEdCountryDown;
    private Button mCalculate;
    Double IsoUpRate, IsoDownRate;
    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=4691eb36ebce42a9ac5db9d977231aed";
    private String mValueCountryUp;
    private Double mDoubValueCountryUp;
    JSONObject ratesObject;
    JSONObject jsonObj = null;
    private String mValueCountryDown;
    private Double mDoubValueCountryDown;
    private Button mCalculates;
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initActionbar();
        initModel();


    }

    private void initActionbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initModel() {

        SetUpDrawer();
       // mMenuItemList = new ArrayList<>();
        //for (int i = 0; i < MenuTitles.length; i++) {
        //    CurrencyModel country = new CurrencyModel();
        //    country.setmItemName(MenuTitles[i]);
        //    mMenuItemList.add(country);
        //
        //    mAdapter = new MenuAdapter(getApplicationContext(), mMenuItemList, new MenuAdapterListener() {
        //        @Override
        //        public void ItemClick(int p) {
        //            switch (p){
        //                case 0:
        //                    Intent mIntent = new Intent(getApplicationContext(), CommenatryActivity.class);
        //                    startActivity(mIntent);
        //                    break;
        //                case 1:
        //                    Intent ntent = new Intent(getApplicationContext(), CommodityActivity.class);
        //                    startActivity(ntent);
        //                    break;
        //            }
        //
        //        }
        //    });
        //
        //    mRecyclerView.setLayoutManager(mLayoutManager);
        //    mRecyclerView.setAdapter(mAdapter);
  //          loadCurrencyExchangeData();
//        getActionBar().setDisplayShowHomeEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
  //      }
    }

    private void SetUpDrawer(){
        CustomDrawerModel[] drawerItem = new CustomDrawerModel[3];

        drawerItem[0] = new CustomDrawerModel(R.mipmap.ic_launcher, "Commodity");
        drawerItem[1] = new CustomDrawerModel(R.mipmap.ic_launcher, "Stock");
        drawerItem[2] = new CustomDrawerModel(R.mipmap.ic_launcher, "AboutUs");
        CustomDrawerAdapter adapter = new CustomDrawerAdapter(this, R.layout.item_drawer, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(true);
        setupDrawerToggle();
    }

    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {
        switch (position) {
            case 0:
                Intent mIntent = new Intent(getApplicationContext(), CommenatryActivity.class);
                startActivity(mIntent);
                break;
            case 1:
                Intent ntent = new Intent(getApplicationContext(), CommodityActivity.class);
                startActivity(ntent);
                break;
            case 2:
                break;

            default:
                break;
        }
    }

    private void initView() {

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
       // mRecyclerView = (RecyclerView) findViewById(R.id.rec_menu_item);
        //mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        //  mMenuList = (ListView) findViewById(R.id.MenuList);
        appImage = (ImageView) findViewById(android.R.id.home);
        TitleText = (TextView) findViewById(android.R.id.title);

        //  mMenuList.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,MenuTitles));

    //    mSlidingPanel.setPanelSlideListener(panelListener);
        mSlidingPanel.setParallaxDistance(200);

    }

    public void initViews() {
        mCountryUp = (LinearLayout) findViewById(R.id.ln_country1);
        mCountryDown = (LinearLayout) findViewById(R.id.ln_country2);
        mFlagCountryUp = (ImageView) findViewById(R.id.img_country1);
        mFlagCountryDown = (ImageView) findViewById(R.id.img_country2);
        mCountryNameUp = (TextView) findViewById(R.id.txt_country1);
        mCountryNameDown = (TextView) findViewById(R.id.txt_country2);
        mEdCountryUp = (EditText) findViewById(R.id.edt_country_up);
        mEdCountryDown = (EditText) findViewById(R.id.edt_country_down);
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mCalculate = (Button) findViewById(R.id.btn_cal);
        mCalculates = (Button) findViewById(R.id.btn_cals);
        mFlagCountryUp.setImageResource(R.mipmap.nigeria);
        mFlagCountryDown.setImageResource(R.mipmap.united_states);
        mCountryUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCountryList();
            }
        });
        mCountryDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCountryList2();
            }
        });
        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValueToString();
                if (isoUp != null && isoDown != null) {
                    try {
                        IsoUpRate = ratesObject.getDouble(isoUp);
                        IsoDownRate = ratesObject.getDouble(isoDown);

                        if (!mValueCountryUp.isEmpty() & mValueCountryDown.isEmpty()) {
                            mDoubValueCountryUp = Double.valueOf(mValueCountryUp);
                            Double ans1ConVert = mDoubValueCountryUp / IsoUpRate;
                            Double ans = ans1ConVert * IsoDownRate;
                            String finalAns = String.valueOf(ans);
                            mEdCountryDown.setText(finalAns);

                        } else if (!mValueCountryDown.isEmpty() & mValueCountryUp.isEmpty()) {
                            mDoubValueCountryDown = Double.valueOf(mValueCountryDown);
                            Double ans1ConVert = mDoubValueCountryDown / IsoDownRate;
                            Double ans = ans1ConVert * IsoUpRate;
                            String finalAns = String.valueOf(ans);
                            mEdCountryUp.setText(finalAns);
                        } else if (!mValueCountryUp.isEmpty() & !mValueCountryDown.isEmpty()) {

                            Toast.makeText(getApplicationContext(), "Clear screen", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    mCountryNameUp.setText("NGN");
                    mCountryNameDown.setText("USD");
                    isoUp = mCountryNameUp.getText().toString();
                    isoDown = mCountryNameDown.getText().toString();
                    try {
                        IsoUpRate = ratesObject.getDouble(isoUp);
                        IsoDownRate = ratesObject.getDouble(isoDown);

                        if (!mValueCountryUp.isEmpty() & mValueCountryDown.isEmpty()) {
                            mDoubValueCountryUp = Double.valueOf(mValueCountryUp);
                            Double ans1ConVert = mDoubValueCountryUp / IsoUpRate;
                            Double ans = ans1ConVert * IsoDownRate;
                            String finalAns = String.valueOf(ans);
                            mEdCountryDown.setText(finalAns);

                        } else if (!mValueCountryDown.isEmpty() & mValueCountryUp.isEmpty()) {
                            mDoubValueCountryDown = Double.valueOf(mValueCountryDown);
                            Double ans1ConVert = mDoubValueCountryDown / IsoDownRate;
                            Double ans = ans1ConVert * IsoUpRate;
                            String finalAns = String.valueOf(ans);
                            mEdCountryUp.setText(finalAns);
                        } else if (!mValueCountryUp.isEmpty() & !mValueCountryDown.isEmpty()) {

                            Toast.makeText(getApplicationContext(), "Clear screen", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), "Pick country", Toast.LENGTH_SHORT).show();
                }



            }


        });

        mCalculates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), testActivity.class);
                startActivity(in);
            }
        });
    }

    private void setValueToString() {
        mValueCountryUp = mEdCountryUp.getText().toString();
        mValueCountryDown = mEdCountryDown.getText().toString();

    }

    public void OpenCountryList() {
        Intent intent = new Intent(getApplicationContext(), CountryListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("flag1", "flag1Country");
        bundle.putInt("id", 5);
        intent.putExtras(bundle);
        startActivityForResult(intent, 2);

    }


    public void OpenCountryList2() {
        Intent intent = new Intent(getApplicationContext(), CountryListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("flag2", "flag2Country");
        bundle.putInt("id", 6);
        intent.putExtras(bundle);
        startActivityForResult(intent, 3);
    }


//    SlidingPaneLayout.PanelSlideListener panelListener = new SlidingPaneLayout.PanelSlideListener() {
//
//        @Override
//        public void onPanelClosed(View arg0) {
//            // TODO Auto-genxxerated method stub        getActionBar().setTitle(getString(R.string.app_name));
////            appImage.animate().rotation(0);
//        }
//
//        @Override
//        public void onPanelOpened(View arg0) {
//            // TODO Auto-generated method stub
////            getActionBar().setTitle("Menu Titles");
////            appImage.animate().rotation(90);
//        }
//
//        @Override
//        public void onPanelSlide(View arg0, float arg1) {
//            // TODO Auto-generated method stub
//
//        }
//
//    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mSlidingPanel.isOpen()) {
                    appImage.animate().rotation(0);
                    mSlidingPanel.closePane();
                    //                   getActionBar().setTitle(getString(R.string.app_name));
                } else {
                    appImage.animate().rotation(90);
                    mSlidingPanel.openPane();
//                    getActionBar().setTitle("Menu Titles");
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadCurrencyExchangeData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(API_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String decodedData = new String(responseBody);
                Log.d("result", "" + decodedData);

                try {
                    jsonObj = new JSONObject(decodedData);
                    ratesObject = jsonObj.getJSONObject("rates");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if data is null, if it is do nothing
     //   if(data == null) return;
        // check if the request code is same as what is passed here it is 2
        if (data != null){
            if (requestCode == 2) {
                //  country = data.getStringExtra("name");
                flag = data.getIntExtra("map", 1);
                iso = data.getStringExtra("iso");
                mCountryNameUp.setText(iso);
                mFlagCountryUp.setImageResource(flag);
                isoUp = mCountryNameUp.getText().toString();

            } else if (requestCode == 3) {
                //   country = data.getStringExtra("name");
                flag = data.getIntExtra("map", 1);
                iso = data.getStringExtra("iso");
                mCountryNameDown.setText(iso);
                mFlagCountryDown.setImageResource(flag);
                isoDown = mCountryNameDown.getText().toString();


            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
         loadCurrencyExchangeData();
    }
}
