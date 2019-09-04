package org.hcilab.projects.nlogx.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import org.hcilab.projects.nlogx.R;
import org.hcilab.projects.nlogx.misc.Const;
import org.hcilab.projects.nlogx.misc.DatabaseHelper;
import org.hcilab.projects.nlogx.misc.ExportTask;
import org.hcilab.projects.nlogx.service.NotificationHandler;


public class Navigator extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_Recents, R.id.nav_All_notification, R.id.nav_notfication_by_Apps,
                R.id.nav_Export_Logs, R.id.nav_Favourites, R.id.nav_How,R.id.nav_Settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigator, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                confirm();
                return true;
            case R.id.menu_export:
                export();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void confirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setTitle(R.string.dialog_delete_header);
        builder.setMessage(R.string.dialog_delete_text);
        builder.setNegativeButton(R.string.dialog_delete_no, (dialogInterface, i) -> {});
        builder.setPositiveButton(R.string.dialog_delete_yes, (dialogInterface, i) -> truncate());
        builder.show();
    }

    private void truncate() {
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(DatabaseHelper.SQL_DELETE_ENTRIES_POSTED);
            db.execSQL(DatabaseHelper.SQL_CREATE_ENTRIES_POSTED);
            db.execSQL(DatabaseHelper.SQL_DELETE_ENTRIES_REMOVED);
            db.execSQL(DatabaseHelper.SQL_CREATE_ENTRIES_REMOVED);
            Intent local = new Intent();
            local.setAction(NotificationHandler.BROADCAST);
            LocalBroadcastManager.getInstance(this).sendBroadcast(local);
        } catch (Exception e) {
            if(Const.DEBUG) e.printStackTrace();
        }
    }

    private void export() {
        if(!ExportTask.exporting) {
            ExportTask exportTask = new ExportTask(this, findViewById(android.R.id.content));
            exportTask.execute();
        }
    }
}
