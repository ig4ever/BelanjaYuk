package com.ig4ever.belanjayukver001;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.ig4ever.belanjayukver001.db.TaskContact;
import com.ig4ever.belanjayukver001.db.TaskDBHelper;

public class MainActivity extends ListActivity {
    private ListAdapter listAdapter;
    private TaskDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();
                addtask();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void updateUI() {
        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContact.TABLE,
                new String[]{TaskContact.Columns._ID, TaskContact.Columns.TASK},
                null, null, null, null, null);

        listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.task_view,
                cursor,
                new String[]{TaskContact.Columns.TASK},
                new int[]{R.id.list},
                0
        );
        this.setListAdapter(listAdapter);
        //ListView listView = (ListView) findViewById(R.id.list);
        //listView.setAdapter(listAdapter);
    }

    public void addtask () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a task");
        builder.setMessage("What do you want to do?");
        final EditText inputField = new EditText(this);
        builder.setView(inputField);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String task = inputField.getText().toString();

                helper = new TaskDBHelper(MainActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.clear();
                values.put(TaskContact.Columns.TASK, task);

                db.insertWithOnConflict(TaskContact.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                updateUI();
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

    public void onDeleteButtonClick(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.list);
        String task = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                TaskContact.TABLE, TaskContact.Columns.TASK, task);


        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }

    public void update (View view) {
        View u = (View) view.getParent();
        TextView taskUpdateView = (TextView) u.findViewById(R.id.list);
        final int id = u.getId();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Data");
        final EditText inputField = new EditText(this);
        builder.setView(inputField);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String update = inputField.getText().toString();

                helper = new TaskDBHelper(MainActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                //db.execSQL(sql);
                ContentValues values = new ContentValues();

                values.clear();
                values.put(TaskContact.Columns._ID, id);
                values.put(TaskContact.Columns.TASK, update);
                db.replace(TaskContact.TABLE, null, values);
                updateUI();
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }
}
