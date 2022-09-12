package com.gvelesiani.passworx.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.Nullable
import androidx.appcompat.widget.LinearLayoutCompat
import com.gvelesiani.passworx.databinding.OnboardingViewBinding


class OnboardingView(context: Context, @Nullable attrs: AttributeSet) :
    LinearLayoutCompat(context, attrs) {
    private var binding = OnboardingViewBinding.inflate(LayoutInflater.from(context), this, true)

    var viewState: ViewState = ViewState()
        set(value) {
            field = value
            updateViews()
        }

    @SuppressWarnings("NestedBlockDepth")
    private fun updateViews() {
        with(binding) {
            when (viewState.step) {
                0 -> {
                    reset()
                    dotFirstStep.isEnabled = false
                }
                1 -> {
                    reset()
                    dotSecondStep.isEnabled = false
                }
                else -> {
                    reset()
                    dotThirdStep.isEnabled = false
                }
            }
        }
    }

    private fun reset() {
        binding.dotFirstStep.isEnabled = true
        binding.dotSecondStep.isEnabled = true
        binding.dotThirdStep.isEnabled = true
    }

    inner class ViewState(
        step: Int = 0,
    ) {
        var step = step
            set(value) {
                field = value
                updateViews()
            }
    }
}