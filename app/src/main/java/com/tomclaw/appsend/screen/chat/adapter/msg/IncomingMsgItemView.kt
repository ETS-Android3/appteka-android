package com.tomclaw.appsend.screen.chat.adapter.msg

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.appsend.R
import com.tomclaw.appsend.dto.UserIcon
import com.tomclaw.appsend.util.bind
import com.tomclaw.appsend.view.UserIconView
import com.tomclaw.appsend.view.UserIconViewImpl

interface IncomingMsgItemView : ItemView {

    fun setUserIcon(userIcon: UserIcon)

    fun setTime(time: String)

    fun setDate(date: String?)

    fun setText(text: String?)

    fun setOnClickListener(listener: (() -> Unit)?)

}

class IncomingMsgItemViewHolder(view: View) : BaseViewHolder(view), IncomingMsgItemView {

    private val dateView: TextView = view.findViewById(R.id.message_date)
    private val userIconView: UserIconView = UserIconViewImpl(view.findViewById(R.id.member_icon))
    private val textView: TextView = view.findViewById(R.id.inc_text)
    private val timeView: TextView = view.findViewById(R.id.inc_time)

    private var clickListener: (() -> Unit)? = null

    init {
        view.setOnClickListener { clickListener?.invoke() }
    }

    override fun setUserIcon(userIcon: UserIcon) {
        userIconView.bind(userIcon)
    }

    override fun setTime(time: String) {
        timeView.bind(time)
    }

    override fun setDate(date: String?) {
        dateView.bind(date)
    }

    override fun setText(text: String?) {
        textView.bind(text)
    }

    override fun setOnClickListener(listener: (() -> Unit)?) {
        this.clickListener = listener
    }

    override fun onUnbind() {
        this.clickListener = null
    }

}
