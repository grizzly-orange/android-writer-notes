package com.grizzlyorange.writernotes.ui.customviews.valuedviews

import android.content.Context
import com.google.android.material.chip.Chip

class ValuedChip <T>(
    public val value: T? = null,
    context: Context,
    style: Int
) : Chip(context, null, style)