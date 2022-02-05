package r.dadashreza.elham.ui.fal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import r.dadashreza.elham.MainActivity
import r.dadashreza.elham.R
import r.dadashreza.elham.databinding.FragmentFalBinding

class FalFragment : Fragment() {

    private var _binding: FragmentFalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this)[FalViewModel::class.java]

        _binding = FragmentFalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btn: Button = binding.button
        val txt: EditText = binding.editTextNumber

        btn.setOnClickListener {
            if (txt.text.isNotEmpty()) {
                val bundle = Bundle()
                bundle.putLong("fal", txt.text.toString().toLong())
                navController.navigate(R.id.nav_home, bundle)
            }
        }

        (activity as MainActivity).setBg(R.drawable.fal_bg)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}