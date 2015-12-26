package com.fernflower.orderbook.activitys;



//import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.fernflower.orderbook.R;
import com.fernflower.orderbook.fragments.CurrentOrdersFragment;
import com.fernflower.orderbook.fragments.CatalogFragment;
import com.fernflower.orderbook.fragments.ComplitedOrdersFragment;
import com.fernflower.orderbook.fragments.HelpFragment;
import com.fernflower.orderbook.fragments.SettingsFragment;
import com.fernflower.orderbook.fragments.StatisticFragment;
import com.fernflower.orderbook.fragments.TemplatesFragment;
import com.fernflower.orderbook.helpers.DialogHelper;
import com.fernflower.orderbook.helpers.StringHelper;

public class OrderNameListActivity extends ActionBarActivity {

    private DrawerLayout menuDrawerLayout;
    private ListView leftMenuList;
    private ActionBarDrawerToggle myDrawerToggle;

    private CharSequence myDrawerTitle;

    private Fragment fragmentScreen;
    private FragmentManager fragmentManager;

    private String[] menuItemsNames;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_name_list_activity);

        fragmentManager=getFragmentManager();

        this.getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();


        myDrawerTitle=getResources().getString(R.string.menu);
        menuItemsNames=getResources().getStringArray(R.array.main_menu_names_array);

        leftMenuList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item, menuItemsNames));




        myDrawerToggle = new ActionBarDrawerToggle(this, menuDrawerLayout, R.string.open,R.string.close){

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle("");
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(myDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };

        menuDrawerLayout.setDrawerListener(myDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }

        leftMenuList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void init(){
        menuDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        leftMenuList=(ListView)findViewById(R.id.left_drawer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if navigation drawer is opened, hide the action items
        boolean drawerOpen = menuDrawerLayout.isDrawerOpen(leftMenuList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(myDrawerToggle.onOptionsItemSelected(item)){
            return  true;
        }

        switch (item.getItemId()){
            case R.id.action_settings:
                displaySettingsView();//SettingsFragment
                return  true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        myDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        myDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void displayView(int position){
         fragmentScreen=null;
        switch (position){
            case 0:
                fragmentScreen=new CurrentOrdersFragment();
                break;
            case 1:
                fragmentScreen=new CatalogFragment();
                break;
            case 2:
                fragmentScreen=new ComplitedOrdersFragment();
                break;
            case  3:
                fragmentScreen=new TemplatesFragment();
                break;
            case 4:
                fragmentScreen=new StatisticFragment();
                break;
            case 5:
                fragmentScreen=new HelpFragment();
            default:
                break;
        }

        if(fragmentScreen!=null){
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentScreen).commit();

            leftMenuList.setItemChecked(position, true);
            leftMenuList.setSelection(position);
            setTitle(menuItemsNames[position]);
            menuDrawerLayout.closeDrawer(leftMenuList);
        }else{
            StringHelper.getInstance().showHint(this,"Fragment is null");
        }
    }

    private class DrawerItemClickListener implements  ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }

    private void displaySettingsView(){
        fragmentScreen=new SettingsFragment();
        if(fragmentScreen!=null) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentScreen).commit();
        }else{
            StringHelper.getInstance().showHint(this,"Settings Fragment is null");
        }
    }

    public void displayFragment(Fragment fragmentScreen){
        this.fragmentScreen=fragmentScreen;
        if(fragmentScreen!=null) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentScreen).commit();
        }else{
            StringHelper.getInstance().showHint(this,"Fragment is null");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


