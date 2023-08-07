package nferno1.cfreddit.presentation.screens

import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class CommonAbstractFragment : Fragment() {

    fun showToast(msg: String?) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}