package com.harera.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.englizya.home_screen.databinding.FragmentHomeBinding
import com.harera.common.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        setupListeners()
//        setupObservers()
//        setupUI()
//        getOffers()
//        getAnnouncements()
//        getUser()
//    }
//
//    private fun getUser(forceOnline: Boolean = false) {
//        homeViewModel
//            .getUser(forceOnline)
//            .observe(viewLifecycleOwner) {
//                handleUserResult(it)
//            }
//    }
//
//    private fun getAnnouncements(forceOnline: Boolean = false) {
//        homeViewModel
//            .getAnnouncements(forceOnline)
//            .observe(viewLifecycleOwner) {
//                handleAnnouncementsResult(it)
//            }
//    }
//
//    private fun getOffers(forceOnline: Boolean = false) {
//        homeViewModel
//            .getOffers(forceOnline)
//            .observe(viewLifecycleOwner) {
//                handleOffersResult(it)
//            }
//    }
//
//    private fun setupUI() {
//        offerSliderAdapter = OfferSliderAdapter(
//            emptyList(),
//            onItemClicked = {
//                navigateToOfferDetails(it)
//            }
//        )
//        binding.imageSlider.setSliderAdapter(offerSliderAdapter)
//        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM)
//        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
//        binding.imageSlider.startAutoCycle()
//        announcementAdapter = AnnouncementAdapter(
//            emptyList(),
//            onItemClicked = {
//                navigateToAnnouncementDetails(it)
//            }
//        )
//        binding.announcementRecyclerView.adapter = announcementAdapter
//    }
//
//    private fun navigateToOfferDetails(offer: Offer) {
//        findNavController().navigate(
//            NavigationUtils.getUriNavigation(
//                Domain.ENGLIZYA_PAY,
//                Destination.OFFER_DETAILS,
//                offer.offerId.toString()
//            )
//        )
//    }
//
//    private fun navigateToAnnouncementDetails(announcement: Announcement) {
//        findNavController().navigate(
//            NavigationUtils.getUriNavigation(
//                Domain.ENGLIZYA_PAY,
//                Destination.ANNOUNCEMENT_DETAILS,
//                announcement.announcementId
//            )
//        )
//    }
//
//    private fun setupObservers() {
//        connectionLiveData.observe(viewLifecycleOwner) {
//            showInternetSnackBar(binding.root, it)
//        }
//    }
//
//    private fun handleOffersResult(resource: Resource<List<Offer>>) {
//        when (resource) {
//            is Resource.Success -> {
////                handleLoading(false)
//                updateUI(resource.data!!)
//            }
//            is Resource.Error -> {
////                handleLoading(false)
//                handleFailure(resource.error)
//
//            }
//            is Resource.Loading -> {
////                handleLoading(true)
//            }
//        }
//    }
//
//    private fun updateUI(offerList: List<Offer>) {
//        offerSliderAdapter.setOffers(offerList)
//    }
//
//    private fun handleUserResult(resource: Resource<User>) {
//        when (resource) {
//            is Resource.Success -> {
//                handleLoading(false)
//                updateUI(resource.data!!)
//            }
//            is Resource.Error -> {
//                handleLoading(false)
//                handleFailure(resource.error)
//            }
//            is Resource.Loading -> {
//                handleLoading(true)
//            }
//        }
//    }
//
//    private fun updateUI(user: User) {
//        binding.userNameTV.text = user.name
//        if(user.imageUrl != null){
//            Picasso.get().load(user.imageUrl).into(binding.imageView)
//
//        }
//    }
//
//    private fun handleAnnouncementsResult(result: Resource<List<Announcement>>) {
//        when (result) {
//            is Resource.Success -> {
////                handleLoading(false)
//                updateAnnouncementsUI(result.data!!)
//            }
//
//            is Resource.Error -> {
////                handleLoading(false)
//                handleFailure(result.error)
//            }
//
//            is Resource.Loading -> {
////                handleLoading(true)
//            }
//        }
//    }
//
//    private fun updateAnnouncementsUI(data: List<Announcement>) {
//        announcementAdapter.setAnnouncements(data)
//    }
//
//    private fun setupListeners() {
//        binding.navigationMenu.setOnClickListener {
//            findNavController().navigate(
//                NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.PROFILE, false)
//            )
//        }
//
//        binding.shortTransportationService.setOnClickListener {
//            progressToInternalSearchActivity()
//        }
//
//        binding.longTransportationService.setOnClickListener {
//            progressToBookingActivity()
//        }
//
//        binding.offerSeeMore.setOnClickListener {
//            progressToOffers()
//        }
//
//        binding.announcementSeeMore.setOnClickListener {
//            progressToAnnouncements()
//        }
//
//        binding.homeSwipeLayout.setOnRefreshListener {
//            binding.homeSwipeLayout.isRefreshing = false
//            refreshData()
//        }
//    }
//
//    private fun refreshData() {
//        getOffers(true)
//        getAnnouncements(true)
//        getUser(true)
//    }
//
//    private fun progressToAnnouncements() {
//        findNavController().navigate(
//            NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.ANNOUNCEMENT, false)
//        )
//    }
//
//    private fun progressToOffers() {
//        findNavController().navigate(
//            NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.OFFERS, false)
//        )
//    }
//
//    private fun progressToBookingActivity() {
//        startActivity(
//            Intent(
//                context,
//                Class.forName("com.englizya.navigation.booking.BookingActivity")
//            )
//        )
//    }
//
//    private fun progressToInternalSearchActivity() {
//        findNavController().navigate(
//            NavigationUtils.getUriNavigation(
//                Domain.ENGLIZYA_PAY,
//                Destination.INTERNAL_SEARCH,
//                false
//            )
//        )
//
//    }
//
//    override fun onDestroyView() {
//        binding.homeSwipeLayout.removeAllViews()
//        super.onDestroyView()
//    }
}