package com.spbisya.navapp.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T> Fragment.viewLifecycleAware(initialise: () -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

        private var value: T? = null

        // This is called JUST before onDestroyView in a Fragment as a limitation of the lifecycle
        //  library. Do not try to access this property in onDestroyView, as it would
        //  implicitly call the initialise function again and provide a new value.
        override fun onDestroy(owner: LifecycleOwner) {
            value = null
        }

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            value ?: initialise().also {
                value = it
                this@viewLifecycleAware.viewLifecycleOwner.lifecycle.addObserver(this)
            }
    }

inline fun <reified T> Fragment.extra(key: String): Lazy<T?> {
    return lazy { arguments?.get(key) as? T }
}

inline fun <reified T> Fragment.extra(key: String, default: T): Lazy<T> {
    return lazy { arguments?.get(key) as? T ?: default }
}

fun Fragment.launch(block: suspend () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}