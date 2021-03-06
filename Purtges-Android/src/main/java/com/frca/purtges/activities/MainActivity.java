package com.frca.purtges.activities;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.frca.purtges.R;
import com.frca.purtges.activities.AppActivity;
import com.frca.purtges.adapters.DrawerAdapter;
import com.frca.purtges.fragments.FragmentLog;
import com.frca.purtges.fragments.FragmentMainTeam;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppActivity {

    private ListView mDrawerList;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*startActivity(new Intent(MainActivity.this, EndpointService.class));
        finish();
        if (true)
            return;*/

        setContentView(R.layout.activity_main);

        DrawerAdapter navigationAdapter = getNavigationAdapter();
        mDrawerList.setAdapter(navigationAdapter);

        mTitle = mDrawerTitle = getTitle();

        final ActionBar bar = getActionBar();

        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                FragmentMainTeam fragment = new FragmentMainTeam();

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content, fragment)
                        .commit();

                mDrawerList.setItemChecked(position, true);
                TextView header = (TextView) view.findViewById(R.id.text_header);
                mTitle = header.getText();

                if (mTitle.equals(getString(R.string.register))) {
                    //startActivity(new Intent(MainActivity.this, EndpointService.class));
                    //GCMIntentService.register(MainActivity.this);
                } else if (mTitle.equals(getString(R.string.checker))) {
                    // Intent special = new Intent(MainActivity.this, EndpointService.class);
                    //special.putExtra(EndpointService.SPECIAL, EndpointService.SPECIAL_ID);
                    //startActivity(special);
                } else
                    bar.setTitle(mTitle);

                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                bar.setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                bar.setTitle(R.string.navigation);
                supportInvalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private DrawerAdapter getNavigationAdapter() {
        if (mDrawerList == null)
            mDrawerList = (ListView) findViewById(R.id.left_drawer);

        DrawerAdapter adapter;
        if (mDrawerList.getAdapter() != null && mDrawerList.getAdapter() instanceof DrawerAdapter) {
            adapter = (DrawerAdapter) mDrawerList.getAdapter();
        } else {
            List<DrawerAdapter.DrawerItem> items = new ArrayList<DrawerAdapter.DrawerItem>();

            // temp
            items.add(new DrawerAdapter.DrawerItem(getString(R.string.team1), R.drawable.game_icon_dota, getString(R.string.team1_members)));
            items.add(new DrawerAdapter.DrawerItem(getString(R.string.team2), R.drawable.game_icon_dota, getString(R.string.team2_members)));
            items.add(new DrawerAdapter.DrawerItem(getString(R.string.team3), R.drawable.game_icon_dota, getString(R.string.team3_members)));

            // divider
            items.add(new DrawerAdapter.DrawerItem(null, 0));

            // Static values
            items.add(new DrawerAdapter.DrawerItem(getString(R.string.team_add), android.R.drawable.ic_menu_add));
            items.add(new DrawerAdapter.DrawerItem(getString(R.string.teams_manage), android.R.drawable.ic_menu_manage));
            items.add(new DrawerAdapter.DrawerItem(getString(R.string.register), android.R.drawable.ic_menu_save));
            items.add(new DrawerAdapter.DrawerItem(getString(R.string.checker), android.R.drawable.ic_menu_manage));

            DrawerAdapter.DrawerItem[] arrItems = items.toArray(new DrawerAdapter.DrawerItem[items.size()]);
            adapter = new DrawerAdapter(this, R.layout.item_drawer, arrItems);
        }

        return adapter;
    }


    @Override
    public void appendText(String text) {
        FragmentMainTeam fragment = (FragmentMainTeam)getSupportFragmentManager().findFragmentById(R.id.content);
        FragmentLog fragmentLog = (FragmentLog)fragment.getSectionsPagerAdapter().getItem(0);
        fragmentLog.appendText(text);
    }
}
