package com.example.view.mainActivity.homeFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.presnter.OnDetailListener
import com.example.view.mainActivity.homeAdapter.newsAdapter.LoadMoreCommunicator
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsInnerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsInnerFragment : Fragment() {
    private lateinit var recommendedRV: RecyclerView
    private lateinit var horizontal_rv: RecyclerView
    private lateinit var seeAllRecommended: View
    private lateinit var trendingRv: RecyclerView
    private lateinit var seeAllTrending: View
    private var loadingBar:View?=null
    private var onDetailListener: OnDetailListener?=null
    var loadListener: LoadMoreCommunicator?=null

    private lateinit var nested_scroll_view_news_inner_fragment: NestedScrollView

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_inner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         seeAllTrending=view.findViewById(R.id.see_all_top)
         trendingRv=view.findViewById(R.id.trending_recycler_view)
         seeAllRecommended=view.findViewById(R.id.see_all_bottom)
         recommendedRV=view.findViewById(R.id.recommended_recycler)
        horizontal_rv=view.findViewById(R.id.horizontal_rv)
        loadingBar=view.findViewById(R.id.loading_more_bar)
        nested_scroll_view_news_inner_fragment=view.findViewById(R.id.nested_scroll_view_news_inner_fragment)

//        val viewModel=ViewModelProvider(requireActivity().viewModelStore
//            , ViewModelFactory(ApiImpl(RetroInstance.apiService))
//        ).get(MainViewModel::class.java)


//        val viewModel2=ViewModelProvider(requireActivity().viewModelStore,ViewModelFactory(ApiHelperImpl(RetroInstance.apiService))).get(MainViewModel::class.java)

//        viewModel.makeNewsCallHorizontal("1",SharedPreference.getInstance().getStringValueFromPreference(SharedPreference.LOCALE_KEY,SharedPreference.ENGLISH,requireContext()))
//        viewModel2.makeNewsCall("2",SharedPreference.getInstance().getStringValueFromPreference(SharedPreference.LOCALE_KEY,SharedPreference.ENGLISH,requireContext()))
//
//        val data=viewModel.newsLiveData.value
//        val data2=viewModel2.newsLiveData2.value

//        if (data!=null){
//            if (data.status==Status.SUCCESS){
////               populateRecyclerViews(data)
//                populateRecyclerViewsHorizontal(data)
//            }else{
//                viewModel.newsLiveData.observe(requireActivity()){
//                    if (it.status==Status.SUCCESS){
//                        //populateRecyclerViews(it)
//                        populateRecyclerViewsHorizontal(it)
//                    }
//                }
//            }
//        }else{
//            viewModel.newsLiveData.observe(requireActivity()){
//                if (it.status==Status.SUCCESS){
//                    //populateRecyclerViews(it)
//                    populateRecyclerViewsHorizontal(it)
//                }
//            }
//        }


//        nested_scroll_view_news_inner_fragment.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
//                Log.i("TAG", "BOTTOM SCROLL")
//
//                loadListener?.loadMore()
//
//            }
//        })
//
//        if (data2!=null){
//            if (data2.status==Status.SUCCESS){
//               populateRecyclerViews(data2)
//            }else{
//                viewModel.newsLiveData2.observe(requireActivity()){
//                    if (it.status==Status.SUCCESS){
//                        populateRecyclerViews(it)
//                    }
//                }
//            }
//        }else{
//            viewModel.newsLiveData2.observe(requireActivity()){
//                if (it.status==Status.SUCCESS){
//                    populateRecyclerViews(it)
//                }
//            }
//        }

    }


    fun setOnDetailListener(onDetailListener: OnDetailListener) {
        this.onDetailListener=onDetailListener
    }

//    private fun populateRecyclerViews(data: Resource<NewsBase>?) {
//        val vm=SpewViewModel.giveMeViewModel(requireActivity())
//        val newsList=data?.data?.list
//
//        loadListener=object : LoadMoreCommunicator{
//            override fun loadMore() {
//                vm.makeNewsCall(object : OnPostDetailResponse<NewsBase>{
//                    override fun onSuccess(responseBody: NewsBase) {
//                        (recommendedRV?.adapter as NewsAdapter).updateList(responseBody.list)
//                        loadingBar?.visibility=View.GONE
//                    }
//
//                    override fun onFailure(message: String) {
//                        loadingBar?.visibility=View.GONE
//                    }
//
//                    override fun onLoading(message: String) {
//                        loadingBar?.visibility=View.VISIBLE
//                    }
//
//                },SharedPreference.getInstance().getStringValueFromPreference(SharedPreference.LOCALE_KEY,SharedPreference.ENGLISH,requireContext()))
//            }
//        }
//
//        recommendedRV.setNestedScrollingEnabled(false);
//
//        recommendedRV.adapter=NewsAdapter(requireContext(), newsList!! as ArrayList<List>,loadListener!!,
//            object : RecyclerViewOnclick{
//                override fun onClick(position: Int) {
//                    val list=ArrayList<String>()
//                    list.add(newsList.get(position)?.id.toString())
//                    onDetailListener?.onDetail(list)
//                }
//            })
//
//        recommendedRV?.layoutManager =
//            GridLayoutManager(context, 2)
//        //recommendedRV.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
//
//    }
//
//    private fun populateRecyclerViewsHorizontal(data: Resource<NewsBase>?) {
//        val newsList=data?.data?.list
//        val vm=SpewViewModel.giveMeViewModel(requireActivity())
//
//        horizontal_rv.setNestedScrollingEnabled(false);
//
//        horizontal_rv.adapter=NewsAdapter_Horizontal(requireContext(), newsList!! as ArrayList<List>,
//            object : RecyclerViewOnclick{
//                override fun onClick(position: Int) {
//                    val list=ArrayList<String>()
//                    list.add(newsList.get(position)?.id.toString())
//                    onDetailListener?.onDetail(list)
//                }
//            })
//
//        horizontal_rv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
//
//    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsInnerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}