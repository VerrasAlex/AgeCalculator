package com.udemy.agecalculator

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
	
	private var tvSelectedDate: TextView? = null
	private var tvAgeInMinutes: TextView? = null
	
		@RequiresApi(Build.VERSION_CODES.N)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		tvSelectedDate = findViewById(R.id.tvSelectedDate)
		tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
		
		val btnDate: Button = findViewById(R.id.btnDate)
		
		btnDate.setOnClickListener{
			clickDateBtn()
		}
	}
	
	
	@RequiresApi(Build.VERSION_CODES.N)
	private fun clickDateBtn(){
		
		//Set Up Calendar
		val myCalendar = Calendar.getInstance()
		
		//Get Calendar Variables (year, month and day of the month)
		val year = myCalendar.get(Calendar.YEAR)
		val month = myCalendar.get(Calendar.MONTH)
		val day = myCalendar. get(Calendar.DAY_OF_MONTH)
		
		//Set Up Date Picker
		val dpd = DatePickerDialog(this,
			{ view, year, month, day ->
				//Toast.makeText(this, "$day/${month+1}/$year", Toast.LENGTH_SHORT).show()
				
				//month starts at 0 so we +1 it
				val selectedDate = "$day/${month+1}/$year"
				
				//Change the Text in the TextView to the date which was selected
				tvSelectedDate?.text = selectedDate
				
				
				//Creating an object of Simple Date Format
				val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
				
				//sdf allows us to create an actual date object
				//we are taking a String(selectedDate) and convert it to the format given by "sdf"
				val theDate = sdf.parse(selectedDate)
				
				// We use "let" to execute the code inside only if "theDate is NOT empty
				theDate?.let {
					
					// .time -or- .getTime() (both are correct and work) returns the time between "theDate"
					// and 01/01/1970 in MILLISECONDS. That is why we divide by 60000 to get the time in MINUTES.
					val selectedDateInMinutes = theDate.time/60000
					
					// System.currentTimeMillis() returns the time between NOW and 01/01/1970
					val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
					
					// We use "let" to execute the code inside only if "currentDate is NOT empty
					currentDate?.let {
						val currentDateInMinutes = currentDate.time/60000
						
						val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
						
						tvAgeInMinutes?.text = differenceInMinutes.toString()
					}
				}
				
			},
			year, month, day
			)
		
		// 86400000 are the milliseconds in a day. So, the maxDate we can choose from the Calendar is YESTERDAY.
		// if we comment the maxDate below, then dpd.show will display negative minutes when a future date is chosen
		dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
		
		dpd.show()//We need to show the calendar too. NOT ONLY set it up. Just like Toast
	}
}