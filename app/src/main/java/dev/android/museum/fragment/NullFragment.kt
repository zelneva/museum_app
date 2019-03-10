package dev.android.museum.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.android.museum.R

class NullFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.null_fragment, container, false)

    companion object {
        fun newInstance(): NullFragment = NullFragment()
    }
}