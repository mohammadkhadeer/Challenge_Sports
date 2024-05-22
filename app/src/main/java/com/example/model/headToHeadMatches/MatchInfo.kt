package com.example.model.headToHeadMatches

import com.google.gson.annotations.SerializedName

data class MatchInfo(
    @SerializedName("match_time") val matchTime: Long,
    @SerializedName("home_en_name") val homeEnName: String,
    @SerializedName("home_cn_name") val homeCnName: String,
    @SerializedName("home_league_ranking") val homeLeagueRanking: String,
    @SerializedName("home_team_score") val homeTeamScore: Int,
    @SerializedName("home_team_half_time_score") val homeTeamHalfTimeScore: Int,
    @SerializedName("home_team_red_cards") val homeTeamRedCards: Int,
    @SerializedName("home_team_yellow_cards") val homeTeamYellowCards: Int,
    @SerializedName("home_team_cornor") val homeTeamCorner: Int,
    @SerializedName("home_team_overtime_score") val homeTeamOvertimeScore: Int,
    @SerializedName("home_team_panalty_score") val homeTeamPenaltyScore: Int,
    @SerializedName("away_en_name") val awayEnName: String,
    @SerializedName("away_cn_name") val awayCnName: String,
    @SerializedName("away_league_ranking") val awayLeagueRanking: String,
    @SerializedName("away_team_score") val awayTeamScore: Int,
    @SerializedName("away_team_half_time_score") val awayTeamHalfTimeScore: Int,
    @SerializedName("away_team_red_cards") val awayTeamRedCards: Int,
    @SerializedName("away_team_yellow_cards") val awayTeamYellowCards: Int,
    @SerializedName("away_team_cornor") val awayTeamCorner: Int,
    @SerializedName("away_team_overtime_score") val awayTeamOvertimeScore: Int,
    @SerializedName("away_team_panalty_score") val awayTeamPenaltyScore: Int
)
