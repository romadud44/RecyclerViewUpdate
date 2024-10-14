package com.example.recyclerview
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recyclerview.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private val dataBase = DBHelper(this)
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.startBTN.setOnClickListener {
            init()
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        }

    private fun init() {
        dataBase.removeAll()
        if (dataBase.readThing(this).isEmpty()) {
            for ((id, i) in Thing.things.withIndex()) {
                val thing = Thing(id, i.name, i.info, i.image)
                dataBase.addThing(thing)
            }
            Thing.thingsDb = dataBase.readThing(this)
        } else Thing.thingsDb = dataBase.readThing(this)
    }

}
