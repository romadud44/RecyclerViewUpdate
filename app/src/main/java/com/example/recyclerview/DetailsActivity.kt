package com.example.recyclerview

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recyclerview.databinding.ActivityDetailsBinding


@Suppress("DEPRECATION", "DEPRECATED_IDENTITY_EQUALS")
class DetailsActivity : AppCompatActivity() {
    private val GALLERY_REQUEST = 777
    private var photoUri: Uri? = null
    private var updateDialog: View? = null
    private var updateImage: ImageView? = null
    private var thingOut: Thing? = null


    private lateinit var binding: ActivityDetailsBinding

    @SuppressLint("CutPasteId", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbarDetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarDetails.setNavigationOnClickListener {
            onBackPressed()
//            intent.putExtra("thingOut", thingOut)
//            setResult(RESULT_OK, intent)
//            finish()

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutDetailsLL)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        updateDialog = this.layoutInflater.inflate(R.layout.update_dialog, null)
        updateImage = updateDialog?.findViewById(R.id.updateImageIV)


        var thing: Thing? = null
        if (intent.hasExtra("thing")) {
            thing = intent.getParcelableExtra("thing")
        }
        if (thing != null) {
            binding.detailsIdTV.text = thing.id.toString()
            binding.detailsNameTV.text = thing.name
            binding.detailsInfoTV.text = thing.info
            val path = "android.resource://com.example.recyclerview/"
            val uri = Uri.parse(path + thing.image)
            binding.detailsImageIV.setImageURI(uri)
        }
        binding.toolbarDetails.setTitle(thing?.name)
        binding.layoutDetailsLL.setOnLongClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setView(updateDialog)
            val editId = updateDialog?.findViewById<EditText>(R.id.updateidET)
            val editName = updateDialog?.findViewById<EditText>(R.id.updateNameET)
            val editInfo = updateDialog?.findViewById<EditText>(R.id.updateInfoET)
            val editImage = updateDialog?.findViewById<ImageView>(R.id.updateImageIV)
            editImage?.setOnClickListener {
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                this.startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
            }


            dialog.setTitle("Обновить запись")
            dialog.setMessage("Введите данные ниже:")
            dialog.setPositiveButton("Обновить") { _, _ ->
                binding.detailsIdTV.text = editId?.text.toString()
                binding.detailsNameTV.text = editName?.text.toString()
                binding.detailsInfoTV.text = editInfo?.text.toString()
                binding.detailsImageIV.setImageURI(photoUri)
                thingOut = Thing(
                    binding.detailsIdTV.text.toString().toInt(),
                    binding.detailsNameTV.text.toString(),
                    binding.detailsInfoTV.text.toString(),
                    photoUri.toString()
                )
//                Toast.makeText(applicationContext, "$thingOut", Toast.LENGTH_LONG).show()
                intent.putExtra("thingOut", thingOut)
                setResult(RESULT_OK, intent)
                finish()


            }
            dialog.setNegativeButton("Отмена") { _, _ ->

            }

            dialog.create().show()

            false
        }


    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuDetails -> {
                finishAndRemoveTask()
                finishAffinity()
                finish()
                Toast.makeText(this, "Программа завершена", Toast.LENGTH_LONG).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
                photoUri = data?.data
                updateImage?.setImageURI(photoUri)
            }
        }
    }
}