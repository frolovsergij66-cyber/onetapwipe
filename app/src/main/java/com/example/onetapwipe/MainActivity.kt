package com.example.onetapwipe

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dpm: DevicePolicyManager
    private lateinit var admin: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        admin = ComponentName(this, WipeAdminReceiver::class.java)

        val button = Button(this).apply {
            text = "СТЕРЕТЬ ВСЁ"
            textSize = 22f
            setOnClickListener { onWipeClicked() }
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(48, 48, 48, 48)
            addView(button)
        }

        setContentView(layout)
    }

    private fun onWipeClicked() {
        if (!dpm.isAdminActive(admin)) {
            // Первый запуск: запрашиваем права администратора устройства.
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin)
                putExtra(
                    DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "Нужно для сброса телефона к заводским настройкам."
                )
            }
            startActivity(intent)
            Toast.makeText(
                this,
                "Включи права администратора и нажми кнопку ещё раз.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        AlertDialog.Builder(this)
            .setTitle("Точно стереть всё?")
            .setMessage("Телефон сбросится к заводским настройкам. Отменить нельзя.")
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Да, стереть") { _, _ ->
                dpm.wipeData(0)
            }
            .show()
    }
}
