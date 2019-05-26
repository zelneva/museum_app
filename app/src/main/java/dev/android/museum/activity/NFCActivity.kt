package dev.android.museum.activity

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import dev.android.museum.R
import dev.android.museum.fragment.common.CommentListFragment
import dev.android.museum.fragment.common.ShowpieceDetailFragment
import java.nio.charset.Charset
import kotlin.experimental.and


class NFCActivity : AppCompatActivity(), ShowpieceDetailFragment.OnFragmentInteractionListener {

    private var nfcAdapter: NfcAdapter? = null
    private var nfcPendingIntent: PendingIntent? = null
    private var showpieceId: String = ""

    companion object {
        var str: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        nfcPendingIntent = PendingIntent.getActivity(this, 0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)

        if (intent != null && str == "") {
            processIntent(intent)
        } else if (intent != null && str != null) {
            if (createNFCMessage(str)) {
                str = ""
                Toast.makeText(this, "Запись прошла успешно", Toast.LENGTH_SHORT).show()
            }
        }

    }


    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, nfcPendingIntent, null, null);
    }


    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this);
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && str == "") {
            processIntent(intent)
        } else if (intent != null && str != "") {
            if (createNFCMessage(str)) {
                str = ""
                Toast.makeText(this, "Запись прошла успешно", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun processIntent(checkIntent: Intent) {
        if (checkIntent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            val rawMessages = checkIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)

            if (rawMessages != null && rawMessages.isNotEmpty()) {
                val ndefMsg = rawMessages[0] as NdefMessage
                if (ndefMsg.records != null && ndefMsg.records.isNotEmpty()) {
                    val ndefRecord = ndefMsg.records[0]
                    if (ndefRecord.toUri() != null) {
                    } else {
                        val payload = ndefRecord.payload
                        val textEncoding = "UTF-8"
                        val languageCodeLength = payload[0] and 51
                        showpieceId = String(payload, languageCodeLength + 1, payload.size - languageCodeLength - 1, Charset.defaultCharset())
                        openFragment(ShowpieceDetailFragment.newInstance(showpieceId))
                    }
                }
            }

        }
    }


    private fun createNFCMessage(string: String): Boolean {
        val pathPrefix = ""
        val nfcRecord = NdefRecord(NdefRecord.TNF_EXTERNAL_TYPE, pathPrefix.toByteArray(), ByteArray(0), string.toByteArray())
        val nfcMessage = NdefMessage(arrayOf(nfcRecord))
        intent?.let {
            val tag = it.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            return writeMessageToTag(string, tag)
        }
        return false
    }


    private fun writeMessageToTag(text: String, tag: Tag?): Boolean {
        val records = arrayOf<NdefRecord>(createRecord(text))
        val message = NdefMessage(records)
        // Get an instance of Ndef for the tag.
        val ndef = Ndef.get(tag) // Enable I/O
        ndef.connect() // Write the message
        ndef.writeNdefMessage(message) // Close the connection
        ndef.close()
        return true
    }

    private fun createRecord(text: String): NdefRecord {
        val lang = "en"
        val textBytes = text.toByteArray()
        val langBytes = lang.toByteArray(charset("US-ASCII"))
        val langLength = langBytes.size
        val textLength = textBytes.size
        val payload = ByteArray(1 + langLength + textLength)

        payload[0] = langLength.toByte()

        System.arraycopy(langBytes, 0, payload, 1, langLength)
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength)

        return NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, ByteArray(0), payload)
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nfc_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun openAuthorDetailFragment(authorId: String) {

    }

    override fun openCommentFragment(showpieceId: String) {
        openFragment(CommentListFragment.newInstance(showpieceId))
    }

}
