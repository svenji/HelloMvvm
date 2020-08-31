package com.sven.mvvm.interactions

import com.sven.mvvm.view.InteractionRequest


class CopyTextToClipboardInteractionRequest private constructor(val text: String, val label: String) : InteractionRequest