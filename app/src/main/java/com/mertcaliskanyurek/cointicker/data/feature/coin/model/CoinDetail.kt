package com.mertcaliskanyurek.cointicker.data.feature.coin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_details")
data class CoinDetail(
    @PrimaryKey val id: String,
    val symbol: String,
    val name: String,
    val block_time_in_minutes: String,
    val hashing_algorithm: String,
    val description: Description,
    val links: Links,
    val image: Images,
    val genesis_date: String,
    val market_cap_rank: Int,
    val developer_score: Double,
    val community_score: Double,
    val liquidity_score: Double,
    val community_data: CommunityData,
    val developer_data: DeveloperData,
    val last_updated: String
)

data class Description(
    val en: String
)

data class CommunityData(
    val twitter_followers: Int,
    val reddit_average_posts_48h: Double,
    val reddit_average_comments_48h: Double,
    val reddit_subscribers: Int,
    val reddit_accounts_active_48h: Int
)

data class DeveloperData(
    val forks: Int,
    val stars: Int,
    val subscribers: Int,
    val total_issues: Int,
    val closed_issues: Int,
    val pull_requests_merged: Int,
    val pull_request_contributors: Int,
)

data class Links(
    val homepage: List<String>,
    val blockchain_site: List<String>,
    val official_forum_url: List<String>,
    val chat_url: List<String>,
    val announcement_url: List<String>,
    val subreddit_url: String,
    val twitter_screen_name: String
)

data class Images(
    val thumb: String,
    val small: String,
    val large: String
)