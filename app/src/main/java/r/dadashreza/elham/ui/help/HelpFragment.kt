package r.dadashreza.elham.ui.help

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import r.dadashreza.elham.MainActivity
import r.dadashreza.elham.R
import r.dadashreza.elham.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {

    private var _binding: FragmentHelpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(HelpViewModel::class.java)

        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textHelp.movementMethod = ScrollingMovementMethod()
        (activity as MainActivity).setBg(R.drawable.help_bg)

        binding.imageView2.setOnClickListener {
            Toast.makeText(context, "RevEngine3r.iR", Toast.LENGTH_LONG).show()
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}