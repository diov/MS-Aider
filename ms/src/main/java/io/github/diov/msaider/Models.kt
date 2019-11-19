package io.github.diov.msaider

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * MSAider
 *
 * Created by Dio_V on 2019-11-16.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

@Serializable
data class RecruitResult(
    @SerialName("status") val status: Int,
    @SerialName("launch_url") val launchUrl: String,
    @SerialName("native_app_launch_url") val intentUrl: String,
    @SerialName("message") val message: String,
    @SerialName("expire_seconds") val expireSeconds: Int
)

@Suppress("ArrayInDataClass")
@Serializable
data class OrderBoard(
    @SerialName("filter_conditions") val filters: Filters,
    @SerialName("wanted_list") val orders: Array<Order>
)

@Serializable
data class Order(
    @SerialName("conditions") val conditions: Conditions,
    @SerialName("congestion") val congestion: Int,
    @SerialName("participation_number") val participation: Int,
    @SerialName("recruitment_number") val recruitment: Int,
    @SerialName("quest_info") val quest: Quest
)

@Suppress("ArrayInDataClass")
@Serializable
data class Filters(
    @SerialName("quest_categories") val categories: Array<String>,
    @SerialName("tags") val tags: Array<String>
)

@Suppress("ArrayInDataClass")
@Serializable
data class Conditions(
    @SerialName("tags") val tags: Array<String>
)

@Serializable
data class Quest(
    @SerialName("article_url") val wikiUrl: String,
    @SerialName("category") val category: String,
    @SerialName("icon_url") val icon: String,
    @SerialName("quest_name") val name: String,
    @SerialName("subquest_name") val level: String
)
