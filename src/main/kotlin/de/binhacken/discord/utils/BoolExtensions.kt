@file:Suppress("unused")

package de.binhacken.discord.utils

inline fun Boolean.ifTrue(action: () -> Unit) = if (this) action.invoke() else Unit
inline fun Boolean.ifFalse(action: () -> Unit) = if (!this) action.invoke() else Unit
