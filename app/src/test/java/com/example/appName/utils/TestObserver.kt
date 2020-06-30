package com.example.appName.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import junit.framework.AssertionFailedError

class TestObserver<T> : Observer<T> {
    private val values = mutableListOf<T>()

    override fun onChanged(newValue: T) {
        values.add(newValue)
    }

    fun assertValues(vararg expectedValues: T) {
        if (expectedValues.size != values.size) {
            throw AssertionFailedError("\nExpected ${expectedValues.size} values, got ${values.size}. \n\n${getExpectedAndCapturedValuesMessage(expectedValues)}")
        }

        expectedValues.forEachIndexed { i, expectedValue ->
            val actualValue = values[i]
            if (actualValue != expectedValue) {
                throw AssertionFailedError("\nValues at $i differ. \n\nExpected value: \n${expectedValue.toString()} \n\nCaptured value: \n${actualValue.toString()}")
            }
        }
    }

    private fun getExpectedAndCapturedValuesMessage(expectedValues: Array<out T>) =
            "Expected values: \n${getStringForValues(expectedValues.toList())} \nCaptured values: \n${getStringForValues(values)}"

    private fun getStringForValues(values: List<T>) =
            StringBuilder()
                    .apply {
                        values.forEach {
                            append(it.toString())
                            append("\n")
                        }
                    }.toString()

}

fun <T> LiveData<T>.test(): TestObserver<T> =
        TestObserver<T>().also {
            this.observeForever(it)
        }
