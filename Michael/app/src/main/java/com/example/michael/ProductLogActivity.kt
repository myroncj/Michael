package com.example.michael

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_product_log.*

class ProductLogActivity : AppCompatActivity() {

    private final val TAG: String = "MICHAEL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_log)

        FirebaseApp.initializeApp(applicationContext)

        val db = FirebaseFirestore.getInstance()

        val bundle: Bundle = intent.extras!!
        val codeValue = bundle.get("codeValue")

        codeValueText.setText(codeValue.toString())

        submitData.setOnClickListener(View.OnClickListener {

            val qty: String  = quantityValue!!.text.toString()

            val data = hashMapOf(
                "code" to codeValue,
                "quantity" to qty,
                "time" to System.currentTimeMillis()
            )

            db.collection("store")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Log.e(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error adding document", e)
                }
        })

    }
}
