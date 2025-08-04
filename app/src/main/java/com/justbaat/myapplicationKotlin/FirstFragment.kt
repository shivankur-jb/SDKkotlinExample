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
import com.justbaat.ads.sdk.AdSdkManager
import com.justbaat.ads.sdk.AdSdkManager.loadRewarded
import com.justbaat.ads.sdk.AdSdkManager.registerSdkReadyCallback
import com.justbaat.ads.sdk.AdSdkManager.showBanner
import com.justbaat.ads.sdk.AdSdkManager.showRewardedAd
import com.justbaat.ads.sdk.models.RewardedReward
import com.justbaat.myapplicationKotlin.databinding.FragmentFirstBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: Activity? = activity
        loadRewarded(requireActivity(), "rewarded_placement")
        registerSdkReadyCallback {
            if (activity != null) {
                val bannerContainer =
                    view.findViewById<ViewGroup?>(R.id.banner_container_first)
                if (bannerContainer != null) {
                    showBanner(activity, bannerContainer)
                } else {
                    Log.e("FirstFragment", "Banner container is null!")
                }
            } else {
                Log.e("FirstFragment", "Activity is null!")
            }
            null
        }

        registerSdkReadyCallback {
            if (activity != null) {
                val nativeAdContainer =
                    view.findViewById<ViewGroup?>(R.id.native_ad_container)

                AdSdkManager.showNativeAd(
                    activity,
                    nativeAdContainer,
                    null,
                    {
                        Log.d("FirstFragment", "Native ad loaded")
                        null
                    },
                    { error: String? ->
                        Log.e("FirstFragment", "Native ad failed to load: $error")
                        null
                    }
                )
            }
            null
        }

        binding.buttonFirst.setOnClickListener {
            if (activity != null) {
                binding.buttonFirst.isEnabled = false
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(5000)
                    binding.buttonFirst.isEnabled = true
                }
                showRewardedAd(
                    activity,
                    "rewarded_placement",
                    true,
                    1,
                    { reward: RewardedReward? ->
                        Log.d("FirstFragment", "User earned reward: $reward")
                        if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                        }
                    },
                    {
                        Log.d("FirstFragment", "Ad was dismissed")
                        if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                        }
                    },
                    { error: String? ->
                        Log.e("FirstFragment", "Ad failed to show: $error")
                        if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
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