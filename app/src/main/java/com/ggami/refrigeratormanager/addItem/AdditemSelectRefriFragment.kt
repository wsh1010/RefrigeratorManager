package com.ggami.refrigeratormanager.addItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.ggami.refrigeratormanager.MessageDialog
import com.ggami.refrigeratormanager.RefrigeratorData
import com.ggami.refrigeratormanager.databinding.FragmentAdditemSelectRefriBinding
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdditemSelectRefriFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdditemSelectRefriFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var mBinding: FragmentAdditemSelectRefriBinding? = null
    private val binding get() = mBinding!!

    private lateinit var parentActivity : AddItemActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentAdditemSelectRefriBinding.inflate(inflater, container, false)
        parentActivity = requireContext() as AddItemActivity


        setRefrigeratorDoor(RefrigeratorData.refriInfo.id)

        setRefrigeratorDoorClick(binding.saveRefriTopLeft, 1)
        setRefrigeratorDoorClick(binding.saveRefriTopRight, 2)
        setRefrigeratorDoorClick(binding.saveRefriBottomLeft, 3)
        setRefrigeratorDoorClick(binding.saveRefriBottomRight, 4)


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.remainingNum.text = "남은 개수 : ${parentActivity.addItemList.size}"
    }

    private fun setRefrigeratorDoorClick (view :TextView, num :Int) {
        view.setOnClickListener {
            parentActivity.binding.itemListPager.setCurrentItem(2, true)
            parentActivity.binding.toolbarTitle.text = "목록 선택 (3/3)"
            parentActivity.selectDoor = num
            parentActivity.setToolbarStatusAccordingToPage(2)
            parentActivity.setNeedSelectInit()
        }
    }

    private fun setRefrigeratorDoor(num :Int) {
        when (num) {
            1 -> {
                binding.saveRefriBottom.visibility = View.GONE
                binding.saveRefriTopRight.visibility = View.GONE
            }
            2 -> {
                binding.saveRefriBottom.visibility = View.GONE
            }
            3 -> {
                binding.saveRefriTopRight.visibility = View.GONE
                binding.saveRefriBottomRight.visibility = View.GONE
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdditemSelectRefriFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdditemSelectRefriFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}