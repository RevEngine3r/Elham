package r.dadashreza.elham.ui.home

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import r.dadashreza.elham.Elham
import r.dadashreza.elham.MainActivity
import r.dadashreza.elham.R
import r.dadashreza.elham.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        val mainActivity = (activity as MainActivity)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome

        if (arguments != null) {
            textView.movementMethod = ScrollingMovementMethod()
            if (arguments?.getBoolean("isRand", false) == true) {
                textView.text = mainActivity.applicationContext?.let { Elham.getRandElham(it) }
            } else if (arguments?.getLong("fal", -1)!! > -1) {
                textView.text = mainActivity.applicationContext?.let {
                    Elham.getElham(
                        arguments?.getLong("fal")!!,
                        it
                    )
                }
            }
        } else {
            homeViewModel.text.observe(viewLifecycleOwner) {
                textView.text = it
            }
        }

        mainActivity.setBg(R.drawable.home_bg)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}