package com.example.ynion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ynion.R
import com.example.ynion.Adapter.ActivityLogAdapter
import com.example.ynion.models.ActivityLogEntry
import com.example.ynion.models.DisplayItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID
import java.util.concurrent.TimeUnit



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LogsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LogsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var logAdapter: ActivityLogAdapter
    private val displayItems = mutableListOf<DisplayItem>()

    // Declare views that might be accessed outside onViewCreated if needed (e.g. for event listeners)
    private lateinit var etSearchLogs: EditText
    private lateinit var ivNotifications: ImageView
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LogsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LogsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Initialize views from the fragment's layout
////        toolbar = view.findViewById(R.id.toolbar_activity_logs)
////        etSearchLogs = view.findViewById(R.id.et_search_logs) // If you need to interact with it
////        ivNotifications = view.findViewById(R.id.iv_notifications) // If you need to interact with it
//
//        // Setup Toolbar
//        // If the Fragment is responsible for its own Toolbar (as per the layout)
//        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
//        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowTitleEnabled(false)
//        // To handle menu items in fragment's toolbar, you'd also use:
//        // setHasOptionsMenu(true)
//        // and override onCreateOptionsMenu, onOptionsItemSelected
//
//        recyclerView = view.findViewById(R.id.activityList)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext()) // Use requireContext()
//        logAdapter = ActivityLogAdapter(displayItems)
//        recyclerView.adapter = logAdapter
//
//        loadAndProcessLogData()
//
//        // Example: Add listener to notification icon if needed
////        ivNotifications.setOnClickListener {
////            // Handle notification icon click
////            // Toast.makeText(requireContext(), "Notifications clicked!", Toast.LENGTH_SHORT).show()
////        }
//    }
//
//    private fun loadAndProcessLogData() {
//        val rawLogs = getSampleLogEntries() // Replace with your actual data fetching
//
//        val groupedLogs = rawLogs.groupBy {
//            getCalendarDate(it.timestamp)
//        }
//
//        val sortedKeys = groupedLogs.keys.sortedDescending()
//
//        val tempList = mutableListOf<DisplayItem>()
//        val todayCal = getCalendarDate(Date())
//        val yesterdayCal = getCalendarDate(Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)))
//        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
//
//        for (dateKey in sortedKeys) {
//            val headerTitle = when (dateKey) {
//                todayCal -> "Today"
//                yesterdayCal -> "Yesterday"
//                else -> dateFormat.format(dateKey.time)
//            }
//            tempList.add(DisplayItem.Header(headerTitle, ))
//            // Make sure logs within the group are also sorted by timestamp descending
//            groupedLogs[dateKey]?.sortedByDescending { it.timestamp }?.forEach { logEntry ->
//                tempList.add(DisplayItem.Log(logEntry))
//            }
//        }
//        displayItems.clear()
//        displayItems.addAll(tempList)
//        // It's safer to update adapter on the UI thread, though for this initial load it's usually fine.
////        activity?.runOnUiThread { // Or viewLifecycleOwner.lifecycleScope.launch { ... }
////            logAdapter.updateData(displayItems)
////        }
//    }
//
//    private fun getCalendarDate(date: Date): Calendar {
//        return Calendar.getInstance().apply {
//            time = date
//            set(Calendar.HOUR_OF_DAY, 0)
//            set(Calendar.MINUTE, 0)
//            set(Calendar.SECOND, 0)
//            set(Calendar.MILLISECOND, 0)
//        }
//    }
//
//    private fun getRelativeTimeSpan(date: Date): String {
//        // Use a robust library for this in a real app (e.g., PrettyTime, or AndroidThreeTenBP with Duration)
//        // This is a simplified version.
//        val now = System.currentTimeMillis()
//        val time = date.time
//        val diff = now - time
//
//        val currentCalendar = Calendar.getInstance()
//        val dateCalendar = Calendar.getInstance()
//        dateCalendar.time = date
//
//        // Check if it's today
//        if (currentCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR) &&
//            currentCalendar.get(Calendar.DAY_OF_YEAR) == dateCalendar.get(Calendar.DAY_OF_YEAR)) {
//            return when {
//                diff < TimeUnit.MINUTES.toMillis(1) -> "Just now"
//                diff < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)} minutes ago"
//                else -> "${TimeUnit.MILLISECONDS.toHours(diff)} hours ago" // "X hours ago"
//            }
//        }
//
//        // Check if it was yesterday
//        currentCalendar.add(Calendar.DAY_OF_YEAR, -1)
//        if (currentCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR) &&
//            currentCalendar.get(Calendar.DAY_OF_YEAR) == dateCalendar.get(Calendar.DAY_OF_YEAR)) {
//            return "Yesterday, ${SimpleDateFormat("h:mm a", Locale.getDefault()).format(date)}"
//        }
//
//        // For other dates
//        return SimpleDateFormat("MMM dd, yyyy, h:mm a", Locale.getDefault()).format(date)
//    }
//
//
//    private fun getSampleLogEntries(): List<ActivityLogEntry> {
//        val list = mutableListOf<ActivityLogEntry>()
//        val cal = Calendar.getInstance()
//
//        // Today
//        val todayTime1 = Calendar.getInstance().apply { add(Calendar.HOUR, -2) }.time
//        list.add(
//            ActivityLogEntry(
////                UUID.randomUUID().toString(),
//                "\"Morning Report\"",
//                "Kent Carl Banzon",
//                todayTime1,
////                "Last edited ${getRelativeTimeSpan(todayTime1)}",
//                R.drawable.collab_icon
//            )
//        )
//        val todayTime2 = Calendar.getInstance().apply { add(Calendar.HOUR, -1) }.time
//        list.add(
//            ActivityLogEntry(
////                UUID.randomUUID().toString(),
//                "\"Feature Proposal\"",
//                "Alice Wonderland",
//                todayTime2,
////                "Viewed ${getRelativeTimeSpan(todayTime2)}",
//                R.drawable.collab_icon
//            )
//        )
//
//        // Yesterday
//        val yesterdayTime = Calendar.getInstance().apply {
//            add(Calendar.DAY_OF_YEAR, -1)
//            add(Calendar.HOUR, -5)
//        }.time
//        list.add(
//            ActivityLogEntry(
////                UUID.randomUUID().toString(),
//                "\"Budget Q2\"",
//                "Bob The Builder",
//                yesterdayTime,
////                "Created ${getRelativeTimeSpan(yesterdayTime)}",
//                R.drawable.collab_icon
//            )
//        )
//
//        // April 05, 2025 (as per your image, using current year for this example if it's past April 5th)
//        val specificDateCal = Calendar.getInstance()
//        // For a consistent "April 05, 2025" from your image, you'd set it directly:
//        // specificDateCal.set(2025, Calendar.APRIL, 5, 10, 30)
//        // For this example, let's use a date 3 days ago to have more variety
//        specificDateCal.add(Calendar.DAY_OF_YEAR, -3)
//        specificDateCal.set(Calendar.HOUR_OF_DAY, 14)
//        specificDateCal.set(Calendar.MINUTE, 15)
//        val specificDateTime = specificDateCal.time
//
//        list.add(
//            ActivityLogEntry(
////                UUID.randomUUID().toString(),
//                "\"Archived Project X\"",
//                "System",
//                specificDateTime,
////                "Action performed ${getRelativeTimeSpan(specificDateTime)}",
//                R.drawable.collab_icon
//            )
//        )
//        return list.sortedByDescending { it.timestamp } // Ensure raw list is sorted before grouping if needed, though grouping and then sorting keys also works
//    }
//
//    // If you add menu items to the toolbar (e.g., search action icon instead of EditText)
//    // override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//    //     super.onCreateOptionsMenu(menu, inflater)
//    //     inflater.inflate(R.menu.your_fragment_menu, menu)
//    //     // Find search item and setup SearchView if needed
//    // }
//    //
//    // override fun onOptionsItemSelected(item: MenuItem): Boolean {
//    //     return when (item.itemId) {
//    //         R.id.action_search -> {
//    //             // Handle search
//    //             true
//    //         }
//    //         else -> super.onOptionsItemSelected(item)
//    //     }
//    // }
//
//    companion object {
//        // Optional: Factory method to create a new instance of this fragment if you need to pass arguments
//        @JvmStatic
//        fun newInstance() = LogsFragment()
//        // fun newInstance(param1: String, param2: String) =
//        //     ActivityLogsFragment().apply {
//        //         arguments = Bundle().apply {
//        //             putString(ARG_PARAM1, param1)
//        //             putString(ARG_PARAM2, param2)
//        //         }
//        //     }
//    }

}