@file:Suppress("unused")

package de.binhacken.discord.utils

import de.binhacken.discord.Main

private fun log(prefix: String, msg: () -> Any?) = println("[$prefix] ${msg()}")

fun info(msg: () -> Any?) = log("INFO", msg)
fun info(msg: Any?) = info { msg }

fun warn(msg: () -> Any?) = log("WARN", msg)
fun warn(msg: Any?) = warn { msg }

fun err(msg: () -> Any?) = log("ERROR", msg)
fun err(msg: Any?) = err { msg }

fun debug(msg: () -> Any?) = if (Main.debugEnabled) log("DEBUG", msg) else Unit
fun debug(msg: Any?) = debug { msg }

fun trace(msg: () -> Any?) = if (Main.traceEnabled) log("TRACE", msg) else Unit
fun trace(msg: Any?) = trace { msg }

