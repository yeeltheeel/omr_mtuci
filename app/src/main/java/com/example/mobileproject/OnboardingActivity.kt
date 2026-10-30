package com.example.mobileproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import kotlin.jvm.java
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val btnSkip = findViewById<Button>(R.id.btnSkip)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnStart = findViewById<Button>(R.id.btnStart)

        viewPager.adapter = OnboardingAdapter(this)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val last = (position == 2)  // 3 slides
                btnNext.visibility = if (last) View.GONE else View.VISIBLE
                btnStart.visibility = if (last) View.VISIBLE else View.GONE
            }
        })

        val goToLogin = {
            getSharedPreferences("app", MODE_PRIVATE).edit {
                putBoolean("onboarding_done", true)
            }
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }

        btnSkip.setOnClickListener { goToLogin() }
        btnStart.setOnClickListener { goToLogin() }
        btnNext.setOnClickListener { viewPager.currentItem++ }
    }
}

class OnboardingAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 3
    override fun createFragment(position: Int) = OnboardingFragment.newInstance(position)
}

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {
    companion object {
        fun newInstance(pos: Int) = OnboardingFragment().apply {
            arguments = Bundle().apply { putInt("pos", pos) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pos = requireArguments().getInt("pos")
        val titles = arrayOf("a", "b", "c")
        val descrs  = arrayOf("d1", "d2", "d3")
        val images = intArrayOf(R.drawable.frame_1, R.drawable.frame_2, R.drawable.frame_3)

        view.findViewById<TextView>(R.id.tvTitle).text = titles[pos]
        view.findViewById<TextView>(R.id.tvDesc).text  = descrs[pos]
        view.findViewById<ImageView>(R.id.ivImage).setImageResource(images[pos])
    }
}
