package com.ggami.refrigeratormanager.addItem

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ggami.refrigeratormanager.*
import com.ggami.refrigeratormanager.databinding.ActivityAddItemBinding


class AddItemActivity : AppCompatActivity() {
    private var mBinding: ActivityAddItemBinding? = null
    val binding get() = mBinding!!
    private lateinit var listFragment : AddItemListFragment
    private lateinit var selectRefriFragment: AdditemSelectRefriFragment
    private lateinit var selectItemFragment : AddItemSelectItemsFragment

    var addItemList = mutableListOf<RefrigeratorData.ItemInfo>()

    var selectDoor: Int = 1
    var selectInit: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_item)
        mBinding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_arrow)

        binding.itemListPager.isUserInputEnabled = false

        listFragment = AddItemListFragment()
        selectRefriFragment = AdditemSelectRefriFragment()
        selectItemFragment = AddItemSelectItemsFragment()
        val pagerAdapter = FragmentPagerAdapter(this).apply {
            addFragments(listFragment)
            addFragments(selectRefriFragment)
            addFragments(selectItemFragment)
        }

        binding.itemListPager.adapter = pagerAdapter

        binding.completBtn.setOnClickListener {
            when(val current = binding.itemListPager.currentItem){
                0 -> {
                    for( i in 0 until listFragment.adapter.getItemList().size){
                        if(listFragment.adapter.getItemList()[i].name == ""){
                            val dlg = MessageDialog(this)
                            dlg.show("상품명을 모두 입력해주세요.")
                            dlg.disableNegativeBtn()
                            dlg.setOnOkClickListener(object: MessageDialog.OnOkClickListener {
                                override fun onOkClick() {
                                    return
                                }
                            })
                            return@setOnClickListener
                        }
                    }
                    addItemList = listFragment!!.adapter.getItemList()
                    binding.itemListPager.setCurrentItem(current+1, true)
                    setToolbarStatusAccordingToPage(binding.itemListPager.currentItem)
                }
                1 -> {
                    binding.itemListPager.setCurrentItem(current+1, true)
                    setToolbarStatusAccordingToPage(binding.itemListPager.currentItem)
                }
                2 -> {
                    val dlg = MessageDialog(this)
                    dlg.show("냉장고에 넣을까요??")
                    dlg.setOnOkClickListener(object: MessageDialog.OnOkClickListener {
                        override fun onOkClick() {
                            selectItemFragment.adapter.removeSelectedItems()
                            addItemList = selectItemFragment.adapter.getItemList()
                            listFragment.adapter.notifyDataSetChanged()
                            RefrigeratorData.addItemsRefrigeratorToDoor(selectDoor, selectItemFragment.adapter.getSelectedItemList())
                            RefrigeratorData.saveRefriInfo(this@AddItemActivity)
                            if(addItemList.size == 0) {
                                finish()
                            }else {
                                binding.itemListPager.setCurrentItem(current-1, true)
                                setToolbarStatusAccordingToPage(binding.itemListPager.currentItem)
                            }
                            return
                        }
                    })
                }
            }
        }

        //var layoutManager = LinearLayoutManager(this)
        /*adapter = AddItemAdapter()
        binding.addItemList.adapter = adapter
        binding.addItemList.layoutManager = LinearLayoutManager(this)

        binding.addItemBtn.setOnClickListener {
            /*var dialog = AddItemDialog()
            dialog.show(supportFragmentManager, "addItemDialog")*/
            var item = RefrigeratorData.ItemInfo()
            item.name = ""

            adapter.addItem(item)
            adapter.notifyItemInserted(adapter.itemCount)
            adapter.notifyDataSetChanged()

        }
        binding.completBtn.setOnClickListener {
            Toast.makeText(this, "ok ${adapter.itemCount}" , Toast.LENGTH_SHORT).show()
            for (i in (0 until adapter.itemCount)){
                Log.e("testMessage", "$i , ${adapter.getItemlist()[i]}")
            }
        }*/
    }

    override fun onResume() {
        super.onResume()
        setToolbarStatusAccordingToPage(binding.itemListPager.currentItem)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //menuInflater.inflate(R.menu.toolbar_okmenu, menu)

        //return true
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //finish()
                if (binding.itemListPager.currentItem == 0) {
                    val dlg = MessageDialog(this)
                    dlg.show("나가시겠습니까?\n주의 : 작성한 목록이 사라집니다.")
                    dlg.setOnOkClickListener(object: MessageDialog.OnOkClickListener {
                        override fun onOkClick() {
                            finish()
                        }
                    })
                } else {
                    var current = binding.itemListPager.currentItem
                    binding.itemListPager.setCurrentItem(current-1, true)
                    setToolbarStatusAccordingToPage(binding.itemListPager.currentItem)
                }
                return true
            }
            R.id.okbutton -> {
                //val intent = Intent()
                //intent.putExtra("refriId", refriId)
                //setResult(Activity.RESULT_OK, intent)
                //finish()
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }

    fun setToolbarStatusAccordingToPage(pageNum : Int){
        when(pageNum) {
            0-> {
                binding.toolbarTitle.text = "구매 목록 (1/3)"
                binding.completBtn.visibility = View.VISIBLE
            }
            1-> {
                binding.toolbarTitle.text = "위치 선택 (2/3)"
                binding.completBtn.visibility = View.GONE
            }
            2-> {
                binding.toolbarTitle.text = "목록 선택 (3/3)"
                binding.completBtn.visibility = View.VISIBLE
            }
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(ev?.action === MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if(v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if(!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())){
                    v.clearFocus()
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    class FragmentPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
        var fragmentList = ArrayList<Fragment>()
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }

        fun addFragments(fragment: Fragment) {
            fragmentList.add(fragment)
            notifyItemInserted(fragmentList.size - 1)
        }

    }

    fun setNeedSelectInit() {
        selectInit = true
    }

    fun getNeedSelectInit() :Boolean{
        return selectInit
    }
}