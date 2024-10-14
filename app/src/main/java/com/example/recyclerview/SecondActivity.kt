package com.example.recyclerview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivitySecondBinding

@Suppress("DEPRECATION")
class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private var listAdapter: CustomAdapter? = null
    private val dataBase = DBHelper(this)
    private var thingIn: Thing? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbarSecond)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        init()
        viewDataAdapter()
        binding.recycleViewRV.layoutManager = LinearLayoutManager(this)
        listAdapter = CustomAdapter(Thing.thingsDb)
        binding.recycleViewRV.adapter = listAdapter
        binding.recycleViewRV.setHasFixedSize(true)
        listAdapter?.setOnThingClickListener(object :
            CustomAdapter.OnThingClickListener {
            override fun onThingClick(thing: Thing, position: Int) {
                val intent = Intent(this@SecondActivity, DetailsActivity::class.java)
                intent.putExtra("thing", thing)
                launchSomeActivity.launch(intent)

            }

        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_second, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuSecond -> {
                finishAndRemoveTask()
                finishAffinity()
                finish()
                Toast.makeText(this, "Программа завершена", Toast.LENGTH_LONG).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun viewDataAdapter() {
//        Thing.thingsDb = dataBase.readThing(this)
        listAdapter = CustomAdapter(Thing.thingsDb)
        binding.recycleViewRV.adapter = listAdapter
        listAdapter?.notifyDataSetChanged()
    }

//    private fun init() {
//        dataBase.removeAll()
//        if (dataBase.readThing(this).isEmpty()) {
//            for ((id, i) in Thing.things.withIndex()) {
//                val thing = Thing(id, i.name, i.info, i.image)
//                dataBase.addThing(thing)
//            }
//            Thing.thingsDb = dataBase.readThing(this)
//        } else Thing.thingsDb = dataBase.readThing(this)
//    }

    private val launchSomeActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            thingIn = data?.getParcelableExtra("thingOut")
            Toast.makeText(this, "Объект добавлен ${thingIn.toString()}", Toast.LENGTH_LONG).show()
            if (thingIn != null) {
                dataBase.updateThing(thingIn!!)
                Thing.thingsDb = dataBase.readThing(this)
                viewDataAdapter()
            }
        } else {
//            Toast.makeText(this, "Объект не добавлен", Toast.LENGTH_LONG).show()
        }
    }
}