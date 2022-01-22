package com.example.covidstatus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.covidstatus.R
import com.example.covidstatus.models.Regional
import com.example.covidstatus.models.status
import kotlinx.android.synthetic.main.item_article_preview.view.*

class CovidStatusAdapter: RecyclerView.Adapter<CovidStatusAdapter.ArticleViewHolder>() {
    inner class ArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    private val differCallBack= object : DiffUtil.ItemCallback<Regional>() {
        override fun areItemsTheSame(oldItem: Regional, newItem: Regional): Boolean {
            return oldItem.loc==newItem.loc
        }

        override fun areContentsTheSame(oldItem: Regional, newItem: Regional): Boolean {
            return oldItem==newItem
        }
    }
    val differ= AsyncListDiffer(this,differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.item_article_preview,parent,false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val data=differ.currentList[position]
        holder.itemView.apply {
            statename.text=data.loc
            confirmedcases.text=data.confirmedCasesIndian.toString()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}