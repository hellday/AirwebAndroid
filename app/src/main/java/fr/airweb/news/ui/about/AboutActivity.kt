package fr.airweb.news.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import fr.airweb.news.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

            // On Click Listeners
            findPreference<Preference>("address")?.setOnPreferenceClickListener {
                goToAddressGmap()
                return@setOnPreferenceClickListener true
            }

            findPreference<Preference>("mail")?.setOnPreferenceClickListener {
                sendEmail()
                return@setOnPreferenceClickListener true
            }

            findPreference<Preference>("phone")?.setOnPreferenceClickListener {
                callPhone()
                return@setOnPreferenceClickListener true
            }


        }

        private fun goToAddressGmap(){
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://maps.google.co.in/maps?q=" + getString(R.string.contact_address))
            startActivity(intent)
        }

        private fun sendEmail(){
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                data = Uri.parse("mailto:")
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.contact_mail)))
            }

            try {
                startActivity(Intent.createChooser(intent, "Choix de l'app"))
            } catch (e: Exception) {
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }
        }

        private fun callPhone() {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + getString(R.string.contact_phone))
            startActivity(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}