package com.ggami.refrigeratormanager.addItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggami.refrigeratormanager.databinding.FragmentAddItemSelectItemsBinding
import com.ggami.refrigeratormanager.databinding.FragmentAdditemSelectRefriBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddItemSelectItemsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddItemSelectItemsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var mBinding: FragmentAddItemSelectItemsBinding? = null
    private val binding get() = mBinding!!

    lateinit var adapter : AddItemSelectAdapter

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
        mBinding = FragmentAddItemSelectItemsBinding.inflate(inflater, container, false)
        parentActivity = requireContext() as AddItemActivity

        adapter = AddItemSelectAdapter(parentActivity)
        adapter.setOnItemClickListener(object: AddItemSelectAdapter.OnItemClickEventListener {
            override fun onItemClick(position: Int) {

            }
        })
        binding.addItemSelectList.adapter = adapter
        binding.addItemSelectList.layoutManager = LinearLayoutManager(parentActivity)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if(parentActivity.getNeedSelectInit()){
            adapter.selectInit()
        }
        adapter.setItemList(parentActivity.addItemList)
        adapter.notifyDataSetChanged()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddItemSelectItemsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddItemSelectItemsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}