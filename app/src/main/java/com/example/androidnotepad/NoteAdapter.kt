package com.example.androidnotepad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidnotepad.db.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    var dataSet: List<Note>? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvId: TextView = itemView.findViewById(R.id.text_id)
        var tvTitle: TextView = itemView.findViewById(R.id.text_note)
        fun bind(note: Note) {
            tvId.text = "" + note.id
            tvTitle.text = note.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataSet?.get(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return dataSet?.size ?: 0
    }
}