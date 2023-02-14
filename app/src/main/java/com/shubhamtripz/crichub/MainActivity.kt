package com.shubhamtripz.crichub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import androidx.lifecycle.lifecycleScope
import com.shubhamtripz.crichub.adapter.MatchAdapter
import com.shubhamtripz.crichub.api.ApiInterface
import com.shubhamtripz.crichub.api.ApiUtilities
import com.shubhamtripz.crichub.constant.Constant.API_KEY
import com.shubhamtripz.crichub.constant.Constant.SERIES_ID
import com.shubhamtripz.crichub.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val matchApi = ApiUtilities.getInstance().create(ApiInterface::class.java)


        lifecycleScope.launch(Dispatchers.IO) {

            val result = matchApi.getSeries(API_KEY, SERIES_ID)

            if (result.body() != null) {
                Log.d("SHUBH", "onCreate: ${result.body()!!.data.matchList}")

                withContext(Dispatchers.Main) {
                    binding.progressbar.visibility = GONE
                    binding.recyclerView.adapter =
                        MatchAdapter(this@MainActivity, result.body()!!.data.matchList)
                }
            }
        }
    }

}