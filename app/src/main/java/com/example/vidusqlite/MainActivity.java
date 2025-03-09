package com.example.vidusqlite;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAddNotes){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }
    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_note_dialog);

        // anh xa trong dialog
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonAdd = dialog.findViewById(R.id.buttonThem);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);

        // bat su kien cho nut them va huy
        buttonAdd.setOnClickListener(v -> {
            String name = editText.getText().toString().trim();
            if (name.equals("")) {
                Toast.makeText(this, "Vui lòng nhập tên Notes", Toast.LENGTH_SHORT).show();
            } else {
                databaseHandler.queryData("INSERT INTO Notes VALUES(null, '" + name + "')");
                Toast.makeText(this, "Đã thêm Notes", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                databaseSQLite();
            }
        });

        buttonHuy.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    public void DialogCapNhatNotes(String name, int id) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_notes_dialog);

        // anh xa trong dialog
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonEdit= dialog.findViewById(R.id.buttonEdit);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);
        editText.setText(name);

        // bat su kien cho nut them va huy
        buttonEdit.setOnClickListener(v -> {
            String nameStr = editText.getText().toString().trim();
            databaseHandler.queryData("UPDATE Notes SET NameNotes ='" + name + "' WHERE Id = '" + "'");
            Toast.makeText(this, "Cập nhật Notes thành công", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            databaseSQLite();
        });

        buttonHuy.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    public void DialogDelete(String name, final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa Notes " + name + " này không?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            databaseHandler.queryData("DELETE FROM Notes WHERE Id = '" + id + "'");
            Toast.makeText(MainActivity.this, "Đã xóa Notes " + name + " thành công!", Toast.LENGTH_SHORT).show();
            databaseSQLite(); // goi ham load lai du lieu
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }


}