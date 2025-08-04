package com.justbaat.myapplicationKotlin

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.justbaat.ads.sdk.AdSdkManager.loadInterstitialAd
import com.justbaat.ads.sdk.AdSdkManager.showBanner
import com.justbaat.ads.sdk.AdSdkManager.showInterstitialAd
import com.justbaat.myapplicationKotlin.databinding.FragmentSecondBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadInterstitialAd(requireActivity(), "interstitial_placement")
        val activity: Activity? = activity
        if (activity != null) {
            val bannerContainer =
                view.findViewById<ViewGroup?>(R.id.banner_container_second)
            if (bannerContainer != null) {
                showBanner(activity, bannerContainer)
            } else {
                Log.e("SecondFragment", "Banner container is null!")
            }
        } else {
            Log.e("SecondFragment", "Activity is null!")
        }

        binding.buttonSecond.setOnClickListener {
            if (activity != null) {
                binding.buttonSecond.isEnabled = false

                viewLifecycleOwner.lifecycleScope.launch {
                    delay(5000)
                    binding.buttonSecond.isEnabled = true
                }

                showInterstitialAd(
                    activity,
                    "interstitial_placement",
                    false,
                    0,
                    {
                        if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                        }
                    },
                    { error: String? ->
                        Log.e("SecondFragment", "Interstitial ad failed: $error")
                        if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                        }
                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}