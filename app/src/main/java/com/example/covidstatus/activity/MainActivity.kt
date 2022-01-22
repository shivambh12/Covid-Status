package com.example.covidstatus.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covidstatus.R
import com.example.covidstatus.adapter.CovidStatusAdapter
import com.example.covidstatus.models.Regional
import com.example.covidstatus.viewmodel.CovidStatusViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),androidx.appcompat.widget.SearchView.OnQueryTextListener,SearchView.OnCloseListener {
    lateinit var madapter:CovidStatusAdapter
    lateinit var viewModel:CovidStatusViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        progressBar.visibility= View.VISIBLE
        viewModel = ViewModelProvider(this).get(CovidStatusViewModel::class.java)
        viewModel.getData()
        viewModel.data.observe(this, Observer {response->
            if(response.isSuccessful)
            {
                response.body()?.let {
                    Log.e("error1234","${it}")
                    madapter.differ.submitList(it.data.regional)
                    progressBar.visibility= View.GONE
                }
            }
            else
            {
                Log.e("error123",response.message())
                progressBar.visibility= View.GONE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.acitvity_menu,menu)
        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as SearchView
        searchView?.isSubmitButtonEnabled =true
        searchView?.setOnQueryTextListener(this)
        searchView?.setOnCloseListener(this)
        return true
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null&&query.isNotEmpty())
        {
            val list=madapter.differ.currentList
            val searchedList= mutableListOf<Regional>()
            for(item in list)
            {
                if(item.loc.equals(query,ignoreCase = true))
                {
                    searchedList.add(item)
                }
            }
            if(searchedList.isEmpty()){
                Toast.makeText(this,"No item found",Toast.LENGTH_SHORT).show()
            }else
            madapter.differ.submitList(searchedList.toList())
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query!=null&&query.isNotEmpty())
        {
            val list=madapter.differ.currentList
            val searchedList= mutableListOf<Regional>()
            for(item in list)
            {
                if(item.loc.contains(query,ignoreCase = true))
                {
                    searchedList.add(item)
                }
            }
                madapter.differ.submitList(searchedList.toList())
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.signout->{
                Firebase.auth.signOut()
                Toast.makeText(this,"Signed Out",Toast.LENGTH_SHORT).show()
                val intent= Intent(this, SignInActivity::class.java)
                startActivity(intent)
                return true
            }
                else->
                    return super.onOptionsItemSelected(item)

        }
    }
    private fun setupRecyclerView() {
        madapter= CovidStatusAdapter()
        reclycleview.apply {
            adapter = madapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        reclycleview.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onClose(): Boolean {
        progressBar.visibility= View.VISIBLE
        viewModel.getData()
        viewModel.data.observe(this, Observer {response->
            if(response.isSuccessful)
            {
                response.body()?.let {
                    Log.e("error1234","${it}")
                    madapter.differ.submitList(it.data.regional)
                    progressBar.visibility= View.GONE
                }
            }
            else
            {
                Log.e("error123",response.message())
                progressBar.visibility= View.GONE
            }
        })
        return false
    }
}