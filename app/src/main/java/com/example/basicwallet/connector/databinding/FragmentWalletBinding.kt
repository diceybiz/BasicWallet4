package com.example.basicwallet.databinding

import androidx.databinding.BindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable

public object FragmentWalletBinding {
    @NonNull
    public fun inflate(@NonNull inflater: LayoutInflater, @Nullable parent: ViewGroup?, @BooleanRes attachToParent: Boolean): FragmentWalletBinding {
        return inflate(inflater, parent, attachToParent, DataBindingUtil.getDefaultComponent())
    }

    @NonNull
    public fun inflate(@NonNull inflater: LayoutInflater, @Nullable parent: ViewGroup?, @BooleanRes attachToParent: Boolean, @Nullable component: BindingComponent?): FragmentWalletBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, parent, attachToParent, component)
    }

    @NonNull
    public fun inflate(@NonNull inflater: LayoutInflater): FragmentWalletBinding {
        return inflate(inflater, null, false)
    }

    public static FragmentWalletBinding bind(@NonNull view: View): FragmentWalletBinding {
        return DataBindingUtil.bind(view)!!
    }
}