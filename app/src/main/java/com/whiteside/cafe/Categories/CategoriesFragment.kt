package com.whiteside.cafe.Categories

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.whiteside.cafe.R

class CategoriesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.categories_rec_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = CategoriesRecyclerViewAdapter(context)
        recyclerView.adapter = adapter
        val inflater1 = TransitionInflater.from(context)
        exitTransition = inflater1.inflateTransition(R.transition.fragment_in)
        enterTransition = inflater1.inflateTransition(R.transition.fragment_out)
        return view
    }
}