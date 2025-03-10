package com.example.vidusqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vidusqlite.MainActivity;
import com.example.vidusqlite.NotesModel;
import com.example.vidusqlite.R;

import java.util.List;

public class NotesAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<NotesModel> noteList;

    public NotesAdapter(Context context, int layout, List<NotesModel> noteList) {
        this.context = (MainActivity) context;
        this.layout = layout;
        this.noteList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // tao viewHolder
    private class ViewHolder {
        TextView tvNote;
        ImageView imageViewEdit, imageViewDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // goi viewHolder
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.tvNote = (TextView) convertView.findViewById(R.id.textViewNameNote);
            viewHolder.imageViewDelete = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            viewHolder.imageViewEdit = (ImageView) convertView.findViewById(R.id.imageViewEdit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // lay gia tri
        NotesModel notes = noteList.get(position);
        viewHolder.tvNote.setText(notes.getNameNote());

        // bat su kien nut edit
        viewHolder.imageViewEdit.setOnClickListener(v -> {
            Toast.makeText(context, "Cập nhật " + notes.getNameNote(), Toast.LENGTH_SHORT).show();
            // Goi dialog trong MainActivity
            context.DialogCapNhatNotes(notes.getNameNote(), notes.getIdNote());
        });

        // băt sự kiện xóa notes
        viewHolder.imageViewDelete.setOnClickListener(v -> {
            context.DialogDelete(notes.getNameNote(), notes.getIdNote());
        });

        return convertView;
    }
}

