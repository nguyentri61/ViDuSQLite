package com.example.vidusqlite;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vidusqlite.adapter.NotesAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayList;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        InitDatabaseSQLite();

        createDatabaseSQLite();

        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        adapter = new NotesAdapter(this, R.layout.row_notes, arrayList);
        listView.setAdapter(adapter);

        databaseSQLite();
    }

    private void createDatabaseSQLite() {
        // them du lieu vao bang
        databaseHandler.queryData("INSERT INTO Notes VALUES(null, 'Vi du SQLite 1')");
        databaseHandler.queryData("INSERT INTO Notes VALUES(null, 'Vi du SQLite 2')");
    }

    private void InitDatabaseSQLite() {
        // khoi tao database
        databaseHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);
        // tao table notes
        databaseHandler.queryData("CREATE TABLE IF NOT EXISTS Notes (Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))");
    }

    private void databaseSQLite() {
        // lay du lieu
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");
        while (cursor.moveToNext()) {
            // them du lieu vao arraylist
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            arrayList.add(new NotesModel(id, name));
            //Toast.makeText(this, "name", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }
}